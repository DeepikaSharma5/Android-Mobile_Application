package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.WindowManager;

public class CartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportFragmentManager().beginTransaction().replace(R.id.cart1,new CartFrag()).commit();



        /* SID
        *
        * fragment_recfragment.xml
        * Homefrag.java
        * Homeadapter.java
        * detailsfragament.java
        * fragment_homefrag.xml
        * fragment_detailsfragment.xml
        *
        * build.gradle
        * android manifest
        */



    }
}
