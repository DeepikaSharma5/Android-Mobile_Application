package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {

    NavigationView nav;
    ImageView menuicon;
    ImageView product,discount,feedback,shipping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        menuicon = findViewById(R.id.menuicon);
        nav = findViewById(R.id.adnavmenu);
        product = findViewById(R.id.product);
        discount = findViewById(R.id.discount);
        feedback = findViewById(R.id.feedback);
        shipping = findViewById(R.id.shipping);
        final DrawerLayout drawerLayout = findViewById(R.id.drawer);

        menuicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });

        shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewPaymentShipping.class));

            }
        });

        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddCode.class));

            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FeedbackMainActivity.class));

            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {

                    case R.id.menuLogout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();

                    case R.id.menuProfile:
                        startActivity(new Intent(getApplicationContext(),ProfileUser.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menuProduct:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menuFeedback:
                        startActivity(new Intent(getApplicationContext(),FeedbackMainActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menuShipping:
                        startActivity(new Intent(getApplicationContext(),ViewPaymentShipping.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;




                }
                return true;
            }
        });


    }
}