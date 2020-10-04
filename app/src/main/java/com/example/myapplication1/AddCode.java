package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddCode extends AppCompatActivity {

    Button btn5, btn6, btn7, btn8,btn9,btn10;
    EditText description, code,search;
    DetailsOfCode DCode;
    DatabaseReference dbref;
    String searchID,idToBeRemoved;
    long maxid = 0;

    private void clearControls() {
        description.setText("");
        code.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_code);

        description = findViewById(R.id.descriptiontext);
        code = findViewById(R.id.discodetext);
        search = findViewById(R.id.searchid);


        btn5 = findViewById(R.id.button_edit);
        btn6 = findViewById(R.id.add);
        btn7 = findViewById(R.id.viewcode);
        btn8 = findViewById(R.id.button_cancel);
        btn9 = findViewById(R.id.deletecode);
        btn10 = findViewById(R.id.searchcodeid);

        DCode = new DetailsOfCode();

    }

    protected void onResume() {
        super.onResume();

        btn6.setOnClickListener(new View.OnClickListener() { //create
            @Override
            public void onClick(View view) { //Create code details
                dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfCode");
                try {
                    if (TextUtils.isEmpty(description.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Description", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(code.getText().toString()) || code.length() > 5)
                        Toast.makeText(getApplicationContext(), "Please enter a Code", Toast.LENGTH_SHORT).show();
                    else {
                        DCode.setDescription(description.getText().toString().trim());
                        DCode.setCode(code.getText().toString().trim());

                        dbref.child(String.valueOf(maxid+1)).setValue(DCode);

                        Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Data, check again", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn10.setOnClickListener(new View.OnClickListener() { //search
            @Override
            public void onClick(View view) { //Search by ID (search by code of discount)
                dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfCode");
                searchID = search.getText().toString().trim();
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference dref = readRef.child("DetailsOfCode");
                Query query = dref.orderByChild("code").equalTo(searchID);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                description.setText((ds.child("description").getValue().toString()));
                                code.setText((ds.child("code").getValue().toString()));
                                Toast.makeText(getApplicationContext(),"Details of your Code",Toast.LENGTH_SHORT).show();
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

        btn5.setOnClickListener(new View.OnClickListener() { //update
            @Override
            public void onClick(View view) { //Update the code details
                dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfCode");
                try {
                    if (TextUtils.isEmpty(description.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Description", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(code.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Code", Toast.LENGTH_SHORT).show();
                    else {
                        DCode.setDescription(description.getText().toString().trim());
                        DCode.setCode(code.getText().toString().trim());
                        dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfCode");
                        dbref.child(String.valueOf(maxid+1)).setValue(DCode);

                        Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Data, check again", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn9.setOnClickListener(new View.OnClickListener() { //Delete
            @Override
            public void onClick(View view) { // Delete code
                dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfCode");
                idToBeRemoved = search.getText().toString().trim();
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference codeRef = rootRef.child("DetailsOfCode");
                Query query = codeRef.orderByChild("code").equalTo(idToBeRemoved);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            ds.getRef().removeValue();
                            Toast.makeText(getApplicationContext(), "Code Deleted Successfully", Toast.LENGTH_SHORT).show();
                            clearControls();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG", databaseError.getMessage());
                    }
                });
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //View code
                Context context = getApplicationContext(); //The context to use. Usually your Application or Activity object
                CharSequence message = "Code details page";//Display string
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(AddCode.this,viewcodedetails.class);
                startActivity(intent);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Cancel
                Context context = getApplicationContext(); //The context to use. Usually your Application or Activity object
                CharSequence message = "Canceled";//Display string
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(AddCode.this,AdminDashboard.class);
                startActivity(intent);
            }
        });


    }

}