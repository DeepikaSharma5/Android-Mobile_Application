package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication1.Model.Product;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Homefrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView homerecview;
    Homeadapter adapter;
    Button Logout;
    ImageView menuicon, carticon;
    DrawerLayout drawerLayout;
    NavigationView nav;




    public Homefrag() {

    }


    public static Homefrag newInstance(String param1, String param2) {
        Homefrag fragment = new Homefrag();
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
                             Bundle savedInstanceState)  {

        View view= inflater.inflate(R.layout.fragment_recfragment, container, false);
        homerecview = (RecyclerView)view.findViewById(R.id.homerecview);
        homerecview.setLayoutManager(new GridLayoutManager(getContext(),2));



        Logout = (Button)view.findViewById(R.id.logoutBtn);
        menuicon = (ImageView)view.findViewById(R.id.menuicon);

        carticon = (ImageView)view.findViewById(R.id.carticon);
        nav = (NavigationView)view.findViewById(R.id.navmenu);
        final DrawerLayout drawerLayout = view.findViewById(R.id.drawer);



        carticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),CartActivity.class));

            }
        });






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

                    case R.id.menuProfile:
                        startActivity(new Intent(getActivity(),ProfileUser.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menuLogout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getActivity(),Login.class));
                        getActivity().finish();




                }
                return true;
            }
        });





        FirebaseRecyclerOptions<Product>options=
                new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"),Product.class)
                .build();

        adapter = new Homeadapter(options);
        homerecview.setAdapter(adapter);



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