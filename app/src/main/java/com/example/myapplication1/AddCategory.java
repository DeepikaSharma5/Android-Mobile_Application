package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddCategory extends AppCompatActivity {

    private CardView CardMen,CardWomen,CardKids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        CardMen = (CardView) findViewById(R.id.CardMen);
        CardWomen = (CardView) findViewById(R.id.CardWomen);
        CardKids = (CardView) findViewById(R.id.CardKids);


        CardMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategory.this,AddProduct.class);
                intent.putExtra("category", "Men");
                startActivity(intent);
            }
        });

        CardWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategory.this,AddProduct.class);
                intent.putExtra("category", "Women");
                startActivity(intent);
            }
        });

        CardKids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategory.this,AddProduct.class);
                intent.putExtra("category", "Kids");
                startActivity(intent);
            }
        });
    }
}