package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class detailsfragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String name;
    String brand;
    String price;
    String category;
    String image;

    public detailsfragment() {
    }

    public detailsfragment(String name, String brand, String price, String category, String image) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.category = category;
        this.image = image;

    }

    public static detailsfragment newInstance(String param1, String param2) {
        detailsfragment fragment = new detailsfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detailsfragment, container, false);

        ImageView imageholder= view.findViewById(R.id.imageholder);
        final TextView nameholder = view.findViewById(R.id.nameholder);
        TextView brandholder = view.findViewById(R.id.brandholder);
        TextView categotyholder = view.findViewById(R.id.categoryholder);
        TextView priceholder = view.findViewById(R.id.priceholder);

        /*          sid         */
        Button addtocartbtn = view.findViewById((R.id.addtocartbtn));


        nameholder.setText(name);
        brandholder.setText(brand);
        categotyholder.setText(category);
        priceholder.setText(price);
        Glide.with(getContext()).load(image).into(imageholder);


        /*          sid         */
        addtocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
            }
        });

        return view;
    }

    /*          sid         */
    public void addingToCartList(){
        String productRandom, saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForTime.getTime());

        productRandom = saveCurrentDate + saveCurrentTime;

        Object quantity = 1;
        Object tot_price = 0;


        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productRandom);
        cartMap.put("pname", name);
        cartMap.put("pbrand", brand);
        cartMap.put("pcategory", category);
        cartMap.put("pro_price", price);
        cartMap.put("pimage", image);
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", quantity);
        cartMap.put("total price", tot_price);

        cartListRef.child("Cart products").child(productRandom).updateChildren(cartMap)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(getActivity(),"Added to cart", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),Homeactivity.class);
                    startActivity(intent);


                }

            }
        });

    }

    public void OnBackPressed()
    {
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main,new Productfrag()).addToBackStack(null).commit();
    }
}