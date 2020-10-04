package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ViewPaymentShipping extends AppCompatActivity {

    Button buttonA, buttonB, buttonC, buttonD,buttonE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payment_shipping);

        buttonA = findViewById(R.id.viewshipping);
        buttonB = findViewById(R.id.viewpayment);
        buttonC = findViewById(R.id.Deleteshipping);
        buttonD = findViewById(R.id.Deletepayment);
        buttonE = findViewById(R.id.spcancel);

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //view list of shipping details
                Context context = getApplicationContext(); //The context to use. Usually your Application or Activity object
                CharSequence message = "shipping details page";//Display string
                int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(ViewPaymentShipping.this,viewshippingdetails.class);
                startActivity(intent);
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //View list of payment details
                Context context = getApplicationContext(); //The context to use. Usually your Application or Activity object
                CharSequence message = "Payment details page";//Display string
                int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(ViewPaymentShipping.this,viewpaymentdetails.class);
                startActivity(intent);
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Delete payment details page
                Context context = getApplicationContext();
                CharSequence message = "Delete shipping details page";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(ViewPaymentShipping.this,DeleteShipping.class);
                startActivity(intent);

            }
        });
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Delete payment details page
                Context context = getApplicationContext();
                CharSequence message = "Delete payment details page";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(ViewPaymentShipping.this,DeletePayment.class);
                startActivity(intent);

            }
        });

        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // cancel and go to admin dashboard
                Context context = getApplicationContext();
                CharSequence message = "Admin Dashboard";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                Intent intent = new Intent(ViewPaymentShipping.this,AdminDashboard.class);
                startActivity(intent);

            }
        });



    }
}