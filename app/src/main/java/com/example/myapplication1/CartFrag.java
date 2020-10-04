package com.example.myapplication1;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication1.Model.Cart;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class CartFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView cart_list;
    RecyclerView.LayoutManager layoutManager;
    CartAdapter adapter;
    TextView title,totalamount;
    Button nxt_process_btn;
    ImageView img_prod;

    int sum=0;
    String total;


    public CartFrag() {

    }


    public static CartFrag newInstance(String param1, String param2) {
        CartFrag fragment = new CartFrag();
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

        View view= inflater.inflate(R.layout.fragment_cart, container, false);



        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List").child("Cart products");

        title = (TextView)view.findViewById(R.id.txt_title);
        cart_list = (RecyclerView)view.findViewById(R.id.cart_list);
        nxt_process_btn = (Button)view.findViewById(R.id.nxt_btn);
        img_prod = (ImageView)view.findViewById(R.id.img_product);

        totalamount = (TextView)view.findViewById(R.id.txt_amount);

        cart_list.setHasFixedSize(true);
        cart_list.setLayoutManager(new LinearLayoutManager(getContext()));




        cartListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sum=0;
                for(DataSnapshot ds : snapshot.getChildren()){
                    Map<String,Object> map = (Map<String, Object>) ds.getValue();
                    Object tot_price = map.get("total price");
                    int pValue = Integer.parseInt(String.valueOf(tot_price));
                    sum += pValue;


                    totalamount.setText(String.valueOf(sum));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        nxt_process_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = totalamount.getText().toString();
                Intent intent = new Intent(getActivity(),PurchaseConfirmation.class);
                intent.putExtra("Value",total);
                startActivity(intent);
                getActivity().finish();


            }
        });

        FirebaseRecyclerOptions<Cart>options=
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart List").child("Cart products"),Cart.class)
                        .build();

        adapter = new CartAdapter(options);
        cart_list.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}