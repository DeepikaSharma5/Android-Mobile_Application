package com.example.myapplication1;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ShippingList extends ArrayAdapter<DetailsOfShipping> {
    private Activity context;
    private List<DetailsOfShipping>shippingList;

    public ShippingList(Activity context,List<DetailsOfShipping>shippingList){
        super(context,R.layout.listshipping,shippingList);
        this.context = context;
        this.shippingList = shippingList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.listshipping,null,true);
        TextView textView1 = (TextView) listViewItem.findViewById(R.id.textView1);
        TextView textView2 = (TextView) listViewItem.findViewById(R.id.textView2);
        TextView textView3 = (TextView) listViewItem.findViewById(R.id.textView3);
        TextView textView4 = (TextView) listViewItem.findViewById(R.id.textView4);
        TextView textView5 = (TextView) listViewItem.findViewById(R.id.textView5);
        TextView textView6 = (TextView) listViewItem.findViewById(R.id.textView6);
        TextView textView7 = (TextView) listViewItem.findViewById(R.id.textView7);
        TextView textView8 = (TextView) listViewItem.findViewById(R.id.textView9);

        textView1.setTextColor(Color.BLACK);
        textView2.setTextColor(Color.BLACK);
        textView3.setTextColor(Color.BLACK);
        textView4.setTextColor(Color.BLACK);
        textView5.setTextColor(Color.BLACK);
        textView6.setTextColor(Color.BLACK);
        textView7.setTextColor(Color.BLACK);
        textView8.setTextColor(Color.BLACK);


        DetailsOfShipping shippingdetail = shippingList.get(position);

        textView1.setText(shippingdetail.getFirstName());
        textView2.setText(shippingdetail.getLastName());
        textView3.setText(shippingdetail.getAddressLine1());
        textView4.setText(shippingdetail.getAddressLine2());
        textView5.setText(shippingdetail.getCity());
        textView6.setText(toString().valueOf(shippingdetail.getPostalCode()));
        textView7.setText(toString().valueOf(shippingdetail.getPhoneNo()));
        textView8.setText(shippingdetail.getEmail());

        return listViewItem;
    }

}
