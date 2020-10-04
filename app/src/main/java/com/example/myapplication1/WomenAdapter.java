package com.example.myapplication1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication1.Model.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class WomenAdapter extends FirebaseRecyclerAdapter<Product,WomenAdapter.homeviewholder> {



    public WomenAdapter(@NonNull FirebaseRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull homeviewholder holder, int position, @NonNull final Product model) {
        holder.productname.setText(model.getName());
        holder.brand.setText(model.getBrand());
        holder.price.setText(model.getPrice());
        Glide.with(holder.productImage.getContext()).load(model.getImage()).into(holder.productImage);

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.womenactivity,new detailsfragment(model.getName(),model.getBrand(),model.getPrice(),model.getCategory(),model.getImage())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public homeviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_homefrag,parent,false);
        return new homeviewholder(view);
    }

    public class homeviewholder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productname,brand,price;
        public homeviewholder(View itemView){
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productname = itemView.findViewById(R.id.productname);
            brand = itemView.findViewById(R.id.brand);
            price = itemView.findViewById(R.id.price);
        }
    }

}

