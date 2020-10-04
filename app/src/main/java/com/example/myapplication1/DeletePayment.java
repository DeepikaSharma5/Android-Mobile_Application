package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class DeletePayment extends AppCompatActivity {
    Button bttn2,deletepayment,buttonsearch,btview;
    EditText text1,text2,text3,text4,text5,text6,text7,textpay;
    TextView t1;
    DetailsOfPayment DPay;
    DatabaseReference dbref;
    int searchID;
    int idToBeRemoved;

    private void clearControls() {
        text1.setText("");
        text2.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");
        text6.setText("");
        text7.setText("");
        textpay.setText("");
        t1.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_payment);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        text6 = findViewById(R.id.text6);
        text7 = findViewById(R.id.text7);
        t1 = findViewById(R.id.tot1);
        textpay = findViewById(R.id.textpay);

        bttn2 = findViewById(R.id.cancelpaymentdel);
        deletepayment = findViewById(R.id.deletepayment);
        buttonsearch = findViewById(R.id.buttonserch);
        btview = findViewById(R.id.viewpayment);

        DPay = new DetailsOfPayment();


        dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfPayment");


        btview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext(); //The context to use. Usually your Application or Activity object
                CharSequence message = "Payment details page";//Display string
                int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(DeletePayment.this,viewpaymentdetails.class);
                startActivity(intent);
            }
        });

        bttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence message = "Deleting payment canceled";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(DeletePayment.this,ViewPaymentShipping.class);
                startActivity(intent);
            }
        });
    }
    protected void onResume(){
        super.onResume();

        buttonsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfPayment");
                searchID = Integer.parseInt(textpay.getText().toString().trim());
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference dref = readRef.child("DetailsOfPayment");
                Query query = dref.orderByChild("card4").equalTo(searchID);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                text1.setText((ds.child("card1").getValue().toString()));
                                text2.setText((ds.child("card2").getValue().toString()));
                                text3.setText((ds.child("card3").getValue().toString()));
                                text4.setText((ds.child("card4").getValue().toString()));
                                text5.setText((ds.child("name").getValue().toString()));
                                text6.setText((ds.child("month").getValue().toString()));
                                text7.setText((ds.child("year").getValue().toString()));
                                t1.setText((ds.child("amount").getValue().toString()));

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

        deletepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfPayment");
                idToBeRemoved = Integer.parseInt( textpay.getText().toString().trim());
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference dref = readRef.child("DetailsOfPayment");
                Query query = dref.orderByChild("card4").equalTo(idToBeRemoved);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            ds.getRef().removeValue();
                            Toast.makeText(getApplicationContext(), "Payment Detail Deleted Successfully", Toast.LENGTH_SHORT).show();
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