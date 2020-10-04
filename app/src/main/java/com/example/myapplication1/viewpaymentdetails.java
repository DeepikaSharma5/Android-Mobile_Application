package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewpaymentdetails extends AppCompatActivity {

    DatabaseReference dbref;
    int searchID;
    long maxid=0;
    ListView listviewpayment;
    List<DetailsOfPayment> paymentlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpaymentdetails);


        listviewpayment = (ListView)findViewById(R.id.listviewpayment);
        paymentlist = new ArrayList<>();
        dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfPayment");


    }

    @Override
    protected void onStart() {
        super.onStart();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                paymentlist.clear();
                for(DataSnapshot paymentsnapshot : dataSnapshot.getChildren()){
                    DetailsOfPayment paymentdetail = paymentsnapshot.getValue(DetailsOfPayment.class);
                    paymentlist.add(paymentdetail);
                }

                PaymentList adapter = new PaymentList(viewpaymentdetails.this,paymentlist) ;
                listviewpayment.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}