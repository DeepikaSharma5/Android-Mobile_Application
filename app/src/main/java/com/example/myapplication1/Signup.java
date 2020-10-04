package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    EditText name,email,password,phone;
    Button registerBtn,goToLogin;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    CheckBox isAdminBox,isUserBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        name=findViewById(R.id.registerName);
        email=findViewById(R.id.registerEmail);
        password=findViewById(R.id.registerPassword);
        phone=findViewById(R.id.registerPhone);
        registerBtn=findViewById(R.id.registerBtn);
        goToLogin=findViewById(R.id.goToLogin);

        isAdminBox=findViewById(R.id.isAdmin);
        isUserBox=findViewById(R.id.isUser);

        //check boxes
        isUserBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isAdminBox.setChecked(false);
                }
            }
        });

        isAdminBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isUserBox.setChecked(false);
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkField(name);
                checkField(email);
                checkField(password);
                checkField(phone);

                //checkbox validation
                if(!(isAdminBox.isChecked() || isUserBox.isChecked())){
                   Toast.makeText(Signup.this,"Select the Account Type..",Toast.LENGTH_SHORT).show();
                   return;
                }

                if(valid){
                    //start the user registration
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Signup.this,"Account Created",Toast.LENGTH_SHORT).show();
                            DocumentReference df =fstore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo=new HashMap<>();
                            userInfo.put("name",name.getText().toString());
                            userInfo.put("email",email.getText().toString());
                            userInfo.put("phone",phone.getText().toString());
                            //specify if the user is admin
                            if(isAdminBox.isChecked()){
                                userInfo.put("isAdmin","1");
                            }
                            if(isUserBox.isChecked()){
                                userInfo.put("isUser","1");
                            }
                            df.set(userInfo);
                           if(isAdminBox.isChecked()){
                               startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
                               finish();
                           }

                            if(isUserBox.isChecked()){
                                startActivity(new Intent(getApplicationContext(),Homeactivity.class));
                                finish();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Signup.this,"Failed to create Account",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }

    public boolean checkField(EditText textField) {
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error!!");
            valid=false;
        }else{
            valid=true;
        }

        return valid;
    }
}