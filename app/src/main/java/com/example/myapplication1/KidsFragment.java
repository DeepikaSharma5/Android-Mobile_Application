package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication1.Model.Product;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class KidsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView kidrecview;
    KidsAdapter adapter;
    ImageView menuicon;
    DrawerLayout drawerLayout;
    NavigationView nav;

    public KidsFragment() {
    }


    public static KidsFragment newInstance(String param1, String param2) {
        KidsFragment fragment = new KidsFragment();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_kids, container, false);
        kidrecview = (RecyclerView)view.findViewById(R.id.kidrecview);
        kidrecview.setLayoutManager(new GridLayoutManager(getContext(),2));
        menuicon = (ImageView)view.findViewById(R.id.menuicon);
        nav = (NavigationView)view.findViewById(R.id.navmenu);
        final DrawerLayout drawerLayout = view.findViewById(R.id.drawer);


        menuicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.menuCart:
                        startActivity(new Intent(getActivity(),AddCategory.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menuMen:
                        startActivity(new Intent(getActivity(),MenActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menuWomen:
                        startActivity(new Intent(getActivity(),WomenActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menuKids:
                        startActivity(new Intent(getActivity(),KidsActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                   /* case R.id.menuProfile:
                        startActivity(new Intent(getActivity(),ProfileUser.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;*/
                    case R.id.menuLogout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getActivity(),Login.class));
                        getActivity().finish();




                }
                return true;
            }
        });

        FirebaseRecyclerOptions<Product> options=
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("category").equalTo("Kids"),Product.class)
                        .build();

        adapter = new KidsAdapter(options);
        kidrecview.setAdapter(adapter);
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