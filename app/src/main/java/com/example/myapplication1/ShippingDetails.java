package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShippingDetails extends AppCompatActivity {

    EditText txtfName,txtlName,txtadd1,txtadd2,txtcity,txtpcode,txtpno,txtemail,txtsearch;
    Button btn1,btn2,btn3;
    DetailsOfShipping DShip;
    TextView t1;
    DatabaseReference dbref;
    int searchID;
    long maxid = 0;
    String finaltot,finaltotal;


    private void clearControls(){
        txtfName.setText("");
        txtlName.setText("");
        txtadd1.setText("");
        txtadd2.setText("");
        txtcity.setText("");
        txtpcode.setText("");
        txtpno.setText("");
        txtemail.setText("");
        txtsearch.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details);

        txtfName = findViewById(R.id.text1);
        txtlName = findViewById(R.id.text2);
        txtadd1 = findViewById(R.id.text3);
        txtadd2 = findViewById(R.id.text9);
        txtcity = findViewById(R.id.text5);
        txtpcode = findViewById(R.id.text6);
        txtpno = findViewById(R.id.text7);
        txtemail = findViewById(R.id.text8);
        txtsearch = findViewById(R.id.text);
        t1 = findViewById(R.id.data);

        Bundle b = getIntent().getExtras();
        assert b != null;
        finaltot = b.getString("finalTotal1");
        t1.setText(String.valueOf(finaltot));

        btn1 = findViewById(R.id.proceed);
        btn2 = findViewById(R.id.cancel);
        btn3 = findViewById(R.id.button1);

        DShip = new DetailsOfShipping();

        //auto increment id session
        dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfShipping");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void onResume(){
        super.onResume();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //add data to database and validation
                try{
                    if(TextUtils.isEmpty(txtfName.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter your First Name",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtlName.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter your Last Name",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtadd1.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter your Address",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtadd2.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter your Address",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtcity.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter your City",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtpcode.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter your Postal Code",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtpno.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter your Phone Number",Toast.LENGTH_SHORT).show();
                    else{
                        DShip.setFirstName(txtfName.getText().toString().trim());
                        DShip.setLastName(txtlName.getText().toString().trim());
                        DShip.setAddressLine1(txtadd1.getText().toString().trim());
                        DShip.setAddressLine2(txtadd2.getText().toString().trim());
                        DShip.setCity(txtcity.getText().toString().trim());
                        DShip.setPostalCode(Integer.parseInt(txtpcode.getText().toString().trim()));
                        DShip.setPhoneNo(Integer.parseInt(txtpno.getText().toString().trim()));
                        DShip.setEmail(txtemail.getText().toString().trim());

                        dbref.child(String.valueOf(maxid+1)).setValue(DShip); // add to database with auto increment id


                        Toast.makeText(getApplicationContext(),"Data Saved Successfully, Proseeding to Payment",Toast.LENGTH_SHORT).show();
                        clearControls();

                        finaltotal = t1.getText().toString();
                        Intent intent = new Intent(ShippingDetails.this,Payment.class);
                        intent.putExtra("finaltotal",finaltotal);
                        startActivity(intent);
                        finish();

                    }
                }
                catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Data, check again",Toast.LENGTH_SHORT).show();
                }



            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence message = "Shipping Canceled, Moving to Items";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(ShippingDetails.this,Homeactivity.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchID = Integer.parseInt(txtsearch.getText().toString().trim());
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference dref = readRef.child("DetailsOfShipping");
                Query query = dref.orderByChild("phoneNo").equalTo(searchID);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                txtfName.setText((ds.child("firstName").getValue().toString()));
                                txtlName.setText((ds.child("lastName").getValue().toString()));
                                txtadd1.setText((ds.child("addressLine1").getValue().toString()));
                                txtadd2.setText((ds.child("addressLine2").getValue().toString()));
                                txtcity.setText((ds.child("city").getValue().toString()));
                                txtpcode.setText((ds.child("postalCode").getValue().toString()));
                                txtpno.setText((ds.child("phoneNo").getValue().toString()));
                                txtemail.setText((ds.child("email").getValue().toString()));

                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Source to Display",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG",databaseError.getMessage());
                    }
                });
            }
        });
    }
}