package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProduct extends AppCompatActivity {

    private String CategoryName,Proname,Proprice,Probrand,Procolor,savecurrentdate,savecurrenttime;
    private Button AddProduct;
    private ImageView imgupload;
    private EditText ProductName,Brand,Color,Price;
    private static final int gallerypick=1;
    private Uri ImageUri;
    private String productRandom, downloadimageurl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

        AddProduct = (Button) findViewById(R.id.AddProductButton);
        imgupload = (ImageView) findViewById(R.id.imgupload);
        ProductName = (EditText) findViewById(R.id.ProductName);
        Brand = (EditText) findViewById(R.id.Brand);
        Color = (EditText) findViewById(R.id.Color);
        Price = (EditText) findViewById(R.id.Price);


        imgupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ValidateProductData();
            }
        });

        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();
    }

    private void OpenGallery() {

        Intent PicIntent = new Intent();
        PicIntent.setAction(Intent.ACTION_GET_CONTENT);
        PicIntent.setType("image/*");
        startActivityForResult(PicIntent,gallerypick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==gallerypick &&  resultCode==RESULT_OK && data!=null){
            ImageUri = data.getData();
            imgupload.setImageURI(ImageUri);
        }

    }

    private void ValidateProductData(){
        Proname = ProductName.getText().toString();
        Procolor = Color.getText().toString();
        Proprice = Price.getText().toString();
        Probrand = Brand.getText().toString();


        if (ImageUri==null){
            Toast.makeText(this, "Image is Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Proname)){
            Toast.makeText(this, "Cloth name is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Proprice)){
            Toast.makeText(this, "Price is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Probrand)){
            Toast.makeText(this, "Brand name is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Procolor)){
            Toast.makeText(this, "Color is required", Toast.LENGTH_SHORT).show();
        }
        else{
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate = currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime = currenttime.format(calendar.getTime());

        productRandom = savecurrentdate + savecurrenttime;

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandom + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddProduct.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddProduct.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadimageurl= filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            downloadimageurl = task.getResult().toString();

                            Toast.makeText(AddProduct.this, "getting product image url successfully", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase() {

        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("pid",productRandom);
        productMap.put("date",savecurrentdate);
        productMap.put("time",savecurrenttime);
        productMap.put("color",Procolor);
        productMap.put("brand",Probrand);
        productMap.put("price",Proprice);
        productMap.put("name",Proname);
        productMap.put("category",CategoryName);
        productMap.put("image",downloadimageurl);

        ProductRef.child(productRandom).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Intent intent = new Intent(com.example.myapplication1.AddProduct.this,AddCategory.class);
                            startActivity(intent);

                            Toast.makeText(AddProduct.this, "Product is add successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(AddProduct.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}