package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Payment extends AppCompatActivity {

    Button b1,b2,b3,proceed;
    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    TextView t1,t2,t3,t4,t5,t6;
    DatabaseReference dbref;
    DetailsOfPayment DPay;
    long maxid = 0;
    public static final String CHANNEL_ID = "Purchase confirmation pg";
    String finaltotalamt;

    private void clearControls() {
        e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("");
        e5.setText("");
        e6.setText("");
        e7.setText("");
        e8.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        e1 = (EditText) findViewById(R.id.text1); // code1
        e2 = (EditText) findViewById(R.id.text2); // code2
        e3 = (EditText) findViewById(R.id.text3); // code3
        e4 = (EditText) findViewById(R.id.text4); // code4
        e5 = (EditText) findViewById(R.id.text5); // name
        e6 = (EditText) findViewById(R.id.text6); // Month
        e7 = (EditText) findViewById(R.id.text7); // year
        e8 = (EditText) findViewById(R.id.text8); // cvv


        t1 = (TextView)findViewById(R.id.code);
        t2 = (TextView)findViewById(R.id.cardholder);
        t3 = (TextView)findViewById(R.id.month);
        t4 = (TextView)findViewById(R.id.year);
        t5 = (TextView)findViewById(R.id.cvv);
        t6 = (TextView)findViewById(R.id.finalamt1);

        Bundle b = getIntent().getExtras();
        assert b != null;
        finaltotalamt = b.getString("finaltotal");
        t6.setText(String.valueOf(finaltotalamt));

        DPay = new DetailsOfPayment();

        proceed = (Button) findViewById(R.id.view);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_down);
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl1);
                relativeLayout.setAnimation(animation);
                String code1, code2, code3, code4;

                code1 = e1.getText().toString();
                code2 = e2.getText().toString();
                code3 = e3.getText().toString();
                code4 = e4.getText().toString();

                t1.setText(code1 + "\t\t" + code2 + "\t\t" + code3 + "\t\t" + code4);

                String nam,mont,yea,cv;

                nam = e5.getText().toString();
                t2.setText(nam);
                mont= e6.getText().toString();
                t3.setText(mont);
                yea = e7.getText().toString();
                t4.setText(yea);
                cv = e8.getText().toString();
                t5.setText(cv);
            }
        });

        b1 = findViewById(R.id.proceed1);
        b2 = findViewById(R.id.cancel1);
        b3 = findViewById(R.id.save);

        dbref = FirebaseDatabase.getInstance().getReference().child("DetailsOfPayment");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Cancel button
                Context context = getApplicationContext(); //The context to use. Usually your Application or Activity object
                CharSequence message = "Payment Canceled, Moving to Items";//Display string
                int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(Payment.this,MainActivity.class);
                startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Edit Shippiing Button
                Context context = getApplicationContext(); //The context to use. Usually your Application or Activity object
                CharSequence message = "Edit Shipping Details";//Display string
                int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(Payment.this,ShippingDetails.class);
                startActivity(intent);

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // notification for purchase confirmation
            CharSequence name = "Purchase confirmation";
            String description = "Purchase confirmation";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }

    }

    public void addNotification(){


        Intent intent = new Intent(this, home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("You have successfully made your purchase!")
                .setContentText("Your item(s) will be shipped in a day")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }

    protected void onResume(){
        super.onResume();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(TextUtils.isEmpty(e1.getText().toString()) || e1.length() < 4)
                        Toast.makeText(getApplicationContext(),"Please enter valid Card Number",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(e2.getText().toString()) || e2.length() < 4)
                        Toast.makeText(getApplicationContext(),"Please enter valid Card Number",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(e3.getText().toString()) || e3.length() < 4)
                        Toast.makeText(getApplicationContext(),"Please enter valid Card Number",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(e4.getText().toString()) || e4.length() < 4)
                        Toast.makeText(getApplicationContext(),"Please enter valid Card Number",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(e5.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter your Name",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(e6.getText().toString()) || Integer.parseInt(e6.getText().toString()) > 12)
                        Toast.makeText(getApplicationContext(),"Please enter valid expire month",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(e7.getText().toString()) || Integer.parseInt(e7.getText().toString()) <20)
                        Toast.makeText(getApplicationContext(),"Please enter valid expire year",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(e8.getText().toString()) || e8.length()<3)
                        Toast.makeText(getApplicationContext(),"Please enter valid Card CVV",Toast.LENGTH_SHORT).show();
                    else{
                        DPay.setCard1(Integer.parseInt(e1.getText().toString().trim()));
                        DPay.setCard2(Integer.parseInt(e2.getText().toString().trim()));
                        DPay.setCard3(Integer.parseInt(e3.getText().toString().trim()));
                        DPay.setCard4(Integer.parseInt(e4.getText().toString().trim()));
                        DPay.setName(e5.getText().toString().trim());
                        DPay.setMonth(Integer.parseInt(e6.getText().toString().trim()));
                        DPay.setYear(Integer.parseInt(e7.getText().toString().trim()));
                        DPay.setAmount(Double.parseDouble(t6.getText().toString().trim()));

                        dbref.child(String.valueOf(maxid+1)).setValue(DPay); // add to database with auto increment id

                        Toast.makeText(getApplicationContext(),"Paid Successfully",Toast.LENGTH_LONG).show();
                        clearControls();
                        addNotification();
                        Intent intent = new Intent(Payment.this,Homeactivity.class);
                        startActivity(intent);
                    }
                }
                catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Data, check again",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}