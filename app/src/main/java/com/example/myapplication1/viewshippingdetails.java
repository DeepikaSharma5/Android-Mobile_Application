package com.example.myapplication1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewshippingdetails extends AppCompatActivity {

    DatabaseReference dbref;
    int searchID;
    long maxid=0;
    ListView listviewshipping;
    List<DetailsOfShipping> shippingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewshippingdetails);


        listviewshipping = (ListView)findViewById(R.id.listviewshipping);
        shippingList = new ArrayList<>();
        dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfShipping");

        listviewshipping.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final int which_item = i;

                new AlertDialog.Builder(viewshippingdetails.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are You Sure?").setMessage("Do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                shippingList.remove(which_item);
                            }

                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shippingList.clear();
                for(DataSnapshot shippingsnapshot : dataSnapshot.getChildren()){
                    DetailsOfShipping shippingdetail = shippingsnapshot.getValue(DetailsOfShipping.class);
                    shippingList.add(shippingdetail);
                }

                ShippingList adapter = new ShippingList(viewshippingdetails.this,shippingList) ;

                listviewshipping.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}