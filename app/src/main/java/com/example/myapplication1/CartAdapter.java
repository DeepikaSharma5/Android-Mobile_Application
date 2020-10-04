package com.example.myapplication1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myapplication1.Model.Cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CartAdapter extends FirebaseRecyclerAdapter<Cart,CartAdapter.CartViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *  @param options
     */

    public CartAdapter(@NonNull FirebaseRecyclerOptions<Cart> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull final CartViewHolder holder, final int position, final Cart model) {

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String, Object> cartMap = new HashMap<>();


        cartMap.put("total price", (Integer.parseInt(model.getPrice()) * model.getQuantity()));
        cartListRef.child("Cart products").child(model.getPid()).updateChildren(cartMap);

        holder.pname.setText(model.getName());
        holder.pbrand.setText(model.getBrand());
        holder.pro_price.setText(model.getPrice());
        Glide.with(holder.pimage.getContext()).load(model.getImage()).into(holder.pimage);

        holder.btn_quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                cartMap.put("quantity", newValue);

                cartListRef.child("Cart products").child(model.getPid()).updateChildren(cartMap);
            }
        });

        holder.btn_quantity.setNumber(String.valueOf(model.quantity));

        holder.psize.setText(model.getSize());

        holder.smallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.psize = "Small";
                cartMap.put("size", "Small");
                cartListRef.child("Cart products").child(model.getPid()).updateChildren(cartMap);
            }
        });

        holder.medbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.psize = "Medium";
                cartMap.put("size", "Medium");
                cartListRef.child("Cart products").child(model.getPid()).updateChildren(cartMap);
            }
        });

        holder.larbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.psize = "Large";
                cartMap.put("size", "Large");
                cartListRef.child("Cart products").child(model.getPid()).updateChildren(cartMap);
            }
        });

        holder.xlarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.psize = "Extra Large";
                cartMap.put("size", "Extra Large");
                cartListRef.child("Cart products").child(model.getPid()).updateChildren(cartMap);
            }
        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartListRef.child("Cart products").child(model.getPid()).removeValue();
            }
        });


    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
        return new CartViewHolder(view);
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView pimage,delete_btn,backarrow;
        TextView pname,pbrand,pro_price,psize;
        ElegantNumberButton btn_quantity;
        Button smallbtn, medbtn, larbtn, xlarbtn;

        public CartViewHolder(View itemView){
            super(itemView);

            pimage = itemView.findViewById(R.id.img_product);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            pname = itemView.findViewById(R.id.txt_product_name);
            pbrand = itemView.findViewById(R.id.txt_product_brand);
            pro_price = itemView.findViewById(R.id.txt_product_price);
            btn_quantity = itemView.findViewById(R.id.btn_quantity);
            medbtn = itemView.findViewById(R.id.medbtn);
            smallbtn = itemView.findViewById(R.id.smallbtn);
            larbtn = itemView.findViewById(R.id.larbtn);
            xlarbtn = itemView.findViewById(R.id.xlarbtn);
            psize = itemView.findViewById(R.id.txt_string_size_view);


        }
    }
}
