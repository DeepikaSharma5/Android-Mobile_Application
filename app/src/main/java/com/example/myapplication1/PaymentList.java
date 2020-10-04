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

public class PaymentList extends ArrayAdapter<DetailsOfPayment> {
    private Activity context;
    private List<DetailsOfPayment> paymentList;

    public PaymentList(Activity context, List<DetailsOfPayment> paymentList){
        super(context,R.layout.listpayment,paymentList);
        this.context = context;
        this.paymentList = paymentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.listpayment,null,true);
        TextView textView1 = (TextView) listViewItem.findViewById(R.id.textView1);
        TextView textView2 = (TextView) listViewItem.findViewById(R.id.textView2);
        TextView textView3 = (TextView) listViewItem.findViewById(R.id.textView3);
        TextView textView4 = (TextView) listViewItem.findViewById(R.id.textView4);
        TextView textView5 = (TextView) listViewItem.findViewById(R.id.textView5);
        TextView textView6 = (TextView) listViewItem.findViewById(R.id.textView6);
        TextView textView7 = (TextView) listViewItem.findViewById(R.id.textView7);
        TextView textView14 = (TextView) listViewItem.findViewById(R.id.textView14);

        textView1.setTextColor(Color.BLACK);
        textView2.setTextColor(Color.BLACK);
        textView3.setTextColor(Color.BLACK);
        textView4.setTextColor(Color.BLACK);
        textView5.setTextColor(Color.BLACK);
        textView6.setTextColor(Color.BLACK);
        textView7.setTextColor(Color.BLACK);
        textView14.setTextColor(Color.BLACK);


        DetailsOfPayment paymentdetail = paymentList.get(position);

        textView1.setText(paymentdetail.getName());
        textView2.setText(toString().valueOf(paymentdetail.getCard1()));
        textView3.setText(toString().valueOf(paymentdetail.getCard2()));
        textView4.setText(toString().valueOf(paymentdetail.getCard3()));
        textView5.setText(toString().valueOf(paymentdetail.getCard4()));
        textView6.setText(toString().valueOf(paymentdetail.getMonth()));
        textView7.setText(toString().valueOf(paymentdetail.getYear()));
        textView14.setText(toString().valueOf(paymentdetail.getAmount()));


        return listViewItem;
    }
}