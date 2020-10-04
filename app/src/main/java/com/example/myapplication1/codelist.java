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

public class codelist extends ArrayAdapter<DetailsOfCode> {
    private Activity context;
    private List<DetailsOfCode> codeList;

    public codelist(Activity context, List<DetailsOfCode> codeList){
        super(context,R.layout.listcode,codeList);
        this.context = context;
        this.codeList = codeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.listcode,null,true);
        TextView textView1 = (TextView) listViewItem.findViewById(R.id.textView1);
        TextView textView2 = (TextView) listViewItem.findViewById(R.id.textView2);
        TextView textView = (TextView)listViewItem.findViewById(R.id.textView);
        TextView textView8 = (TextView)listViewItem.findViewById(R.id.textView8);




        textView1.setTextColor(Color.BLACK);
        textView2.setTextColor(Color.BLACK);
        textView.setTextColor(Color.BLACK);
        textView8.setTextColor(Color.BLACK);



        textView1.setTextSize(20);
        textView2.setTextSize(20);
        textView.setTextSize(20);
        textView8.setTextSize(20);


        DetailsOfCode codedetail = codeList.get(position);

        textView1.setText(codedetail.getCode());
        textView2.setText(codedetail.getDescription());


        return listViewItem;
    }
}