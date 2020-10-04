package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PurchaseConfirmation extends AppCompatActivity {

    Button bt1,bt2,bt3,bt4;
    EditText discount1;
    TextView famount,amount;
    String total,ftotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_confirmation);

            bt1 = findViewById(R.id.cpayment);
            bt2 = findViewById(R.id.cancel4);
            bt3 = findViewById(R.id.promo);
            bt4 = findViewById(R.id.checkprice);

            discount1 = findViewById(R.id.discount1);
            famount = findViewById(R.id.famount);
            amount = findViewById(R.id.amount);

            Bundle b = getIntent().getExtras();
            assert b != null;
            total = b.getString("Value");
            amount.setText(String.valueOf(total));


         bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(TextUtils.isEmpty(famount.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Please select 'GET YOYR FINAL AMOUNT' button",Toast.LENGTH_LONG).show();
                    }else{
                        Context context = getApplicationContext();
                        CharSequence message = "Proceeding to Shipping details";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, message, duration);
                        toast.show();

                        ftotal = famount.getText().toString();
                        Intent intent = new Intent(PurchaseConfirmation.this,ShippingDetails.class);
                        intent.putExtra("finalTotal1",ftotal);
                        startActivity(intent);
                        finish();
                       
                    }


                }
            });


            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = getApplicationContext();
                    CharSequence message = "Payment Canceled, Moving to Items";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, message, duration);
                    toast.show();

                    Intent intent = new Intent(PurchaseConfirmation.this,Homeactivity.class);
                    startActivity(intent);
                }
            });

        bt4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    if(discount1.getText().toString().equals("cr12A")){
                        double no2 = Double.valueOf(amount.getText().toString());
                        double m = 80.0/100.0;
                        double no4 = no2*m;
                        famount.setText(String.valueOf(no4));
                    }
                    else {
                        double Net = Double.valueOf(amount.getText().toString());
                        famount.setText(String.valueOf(Net));
                    }
            }
                
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence message = "Discount code";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();
                Intent intent = new Intent(PurchaseConfirmation.this,viewcodedetails.class);
                startActivity(intent);



            }
        });

    }

}
