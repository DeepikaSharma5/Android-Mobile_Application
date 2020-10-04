package com.example.myapplication1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {

    EditText username,feedback;
    Button btnadd;
    ModelFeedback feed;
    DatabaseReference dbref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        username = findViewById(R.id.username);
        feedback = findViewById(R.id.addfeedback);

        btnadd = findViewById(R.id.btnadd);

        feed = new ModelFeedback();


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbref = FirebaseDatabase.getInstance().getReference().child("Feedback");


                    if(TextUtils.isEmpty(username.getText().toString()))
                        Toast.makeText(Feedback.this, "Username required", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(feedback.getText().toString()))
                        Toast.makeText(Feedback.this, "Feedback is required", Toast.LENGTH_SHORT).show();
                    else {
                        feed.setUsername(username.getText().toString().trim());
                        feed.setFeedback(feedback.getText().toString().trim());

                        dbref.push().setValue(feed);

                        Toast.makeText(Feedback.this, "Feedback added successfully", Toast.LENGTH_SHORT).show();
                        clearcontrols();
                    }




            }
        });






    }







    private void clearcontrols(){
        username.setText("");
        feedback.setText("");
    }


}