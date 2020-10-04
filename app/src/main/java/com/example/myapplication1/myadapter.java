package com.example.myapplication1;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication1.Model.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class myadapter extends FirebaseRecyclerAdapter<Product,myadapter.myviewholder>
{


    public myadapter(@NonNull FirebaseRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Product model) {
        holder.productname.setText(model.getName());
        holder.brand.setText(model.getBrand());
        holder.price.setText(model.getPrice());
        Glide.with(holder.productImage.getContext()).load(model.getImage()).into(holder.productImage);

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main,new detailsfragment(model.getName(),model.getBrand(),model.getPrice(),model.getCategory(),model.getImage())).addToBackStack(null).commit();
            }
        });

        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.productImage.getContext())
                        .setContentHolder(new ViewHolder(R.layout.editbox))
                        .setExpanded(true,1800)
                        .create();

                View myview = dialogPlus.getHolderView();
                final EditText image = myview.findViewById(R.id.url);
                final EditText name = myview.findViewById(R.id.ProductName);
                final EditText brand = myview.findViewById(R.id.Brand);
                final EditText price = myview.findViewById(R.id.Price);
                final EditText color = myview.findViewById(R.id.Color);
                Button update = myview.findViewById(R.id.AddProductButton);

                image.setText(model.getImage());
                name.setText(model.getName());
                brand.setText(model.getBrand());
                price.setText(model.getPrice());
                color.setText(model.getColor());

                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("image",image.getText().toString());
                        map.put("color",color.getText().toString());
                        map.put("brand",brand.getText().toString());
                        map.put("name",name.getText().toString());
                        map.put("price",price.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Products")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });




            }
        });

        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.productImage.getContext());
                builder.setTitle("Delete Product");
                builder.setMessage("Want to delete this product?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Products")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_lists,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productname,brand,price;
        Button editbtn,delbtn;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productname = itemView.findViewById(R.id.productname);
            brand = itemView.findViewById(R.id.brand);
            price = itemView.findViewById(R.id.price);



            editbtn = itemView.findViewById(R.id.editbtn);
            delbtn = itemView.findViewById(R.id.delbtn);
        }
    }
}
