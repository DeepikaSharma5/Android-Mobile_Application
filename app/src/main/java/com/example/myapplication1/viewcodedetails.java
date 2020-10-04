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

public class viewcodedetails extends AppCompatActivity {

    DatabaseReference dbref;
    int searchID;
    long maxid=0;
    ListView listviewcode;
    List<DetailsOfCode> codeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcodedetails);


        listviewcode = (ListView)findViewById(R.id.listviewcode);
        codeList = new ArrayList<>();
        dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfCode");


    }

    @Override
    protected void onStart() {
        super.onStart();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                codeList.clear();
                for(DataSnapshot codesnapshot : dataSnapshot.getChildren()){
                    DetailsOfCode codedetail = codesnapshot.getValue(DetailsOfCode.class);
                    codeList.add(codedetail);
                }

                codelist adapter = new codelist(viewcodedetails.this,codeList);
                listviewcode.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}