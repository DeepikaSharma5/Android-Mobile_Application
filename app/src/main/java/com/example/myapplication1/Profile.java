package com.example.myapplication1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {


    private EditText name,email,password,address,contactNo;
    private Button  updateProfile;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        address=findViewById(R.id.address);
        contactNo=findViewById(R.id.phone);
        updateProfile=findViewById(R.id.updateProfile);
        progressBar=findViewById(R.id.progressBar);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();

        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);
                name.setText("Name: "+ userProfile.getUsername());
                email.setText("Email: "+ userProfile.getUserEmail());
                password.setText("Password: "+ userProfile.getUserPassword());
                address.setText("Address: "+ userProfile.getUserAddres());
                contactNo.setText("ContactNo: "+ userProfile.getUserContactNo());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Profile.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}