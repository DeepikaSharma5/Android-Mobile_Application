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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DeleteShipping extends AppCompatActivity {
    EditText text1,text2,text3,text9,text5,text6,text7,text8,text;
    Button bttn1,button1,btndel,viewship;
    DetailsOfShipping DShip;
    DatabaseReference dbref;
    int searchID;
    int idToBeRemoved;


    private void clearControls(){
        text1.setText("");
        text2.setText("");
        text3.setText("");
        text9.setText("");
        text5.setText("");
        text6.setText("");
        text7.setText("");
        text8.setText("");
        text.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_shipping);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text9 = findViewById(R.id.text9);
        text5 = findViewById(R.id.text5);
        text6 = findViewById(R.id.text6);
        text7 = findViewById(R.id.text7);
        text8 = findViewById(R.id.text8);
        text = findViewById(R.id.text);

        button1 = findViewById(R.id.button1);
        bttn1 = findViewById(R.id.shipcancel);
        btndel = findViewById(R.id.deleteship);
        viewship = findViewById(R.id.viewshipping);

        DShip = new DetailsOfShipping();


        dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfShipping");


        viewship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext(); //The context to use. Usually your Application or Activity object
                CharSequence message = "shipping details page";//Display string
                int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(DeleteShipping.this,viewshippingdetails.class);
                startActivity(intent);
            }
        });

        bttn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence message = "Deleting shipping details canceled";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(DeleteShipping.this,ViewPaymentShipping.class);
                startActivity(intent);
            }
        });


    }

    protected void onResume(){
        super.onResume();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchID = Integer.parseInt(text.getText().toString().trim());
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference dref = readRef.child("DetailsOfShipping");
                Query query = dref.orderByChild("phoneNo").equalTo(searchID);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                text1.setText((ds.child("firstName").getValue().toString()));
                                text2.setText((ds.child("lastName").getValue().toString()));
                                text3.setText((ds.child("addressLine1").getValue().toString()));
                                text9.setText((ds.child("addressLine2").getValue().toString()));
                                text5.setText((ds.child("city").getValue().toString()));
                                text6.setText((ds.child("postalCode").getValue().toString()));
                                text7.setText((ds.child("phoneNo").getValue().toString()));
                                text8.setText((ds.child("email").getValue().toString()));

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

        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfShipping");
                idToBeRemoved = Integer.parseInt(text.getText().toString().trim());
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference dref = readRef.child("DetailsOfShipping");
                Query query = dref.orderByChild("phoneNo").equalTo(idToBeRemoved);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            ds.getRef().removeValue();
                            Toast.makeText(getApplicationContext(), "Shipping Detail Deleted Successfully", Toast.LENGTH_SHORT).show();
                            clearControls();
                        }
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