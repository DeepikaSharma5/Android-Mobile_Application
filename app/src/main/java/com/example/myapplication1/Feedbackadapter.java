package com.example.myapplication1;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class Feedbackadapter extends FirebaseRecyclerAdapter<ModelFeedback,Feedbackadapter.myfeedviewholder> {



    public Feedbackadapter(@NonNull FirebaseRecyclerOptions<ModelFeedback> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myfeedviewholder holder, final int position, @NonNull final ModelFeedback model) {
        holder.usernameholder.setText(model.getUsername());
        holder.feedbackholder.setText(model.getFeedback());





         holder.editbut.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 final DialogPlus dialogPlus = DialogPlus.newDialog(holder.usernameholder.getContext())
                         .setContentHolder(new ViewHolder(R.layout.feedbackedit))
                         .setExpanded(true,1700)
                         .create();

                 View feedview = dialogPlus.getHolderView();
                 final EditText uname = feedview.findViewById(R.id.uname);
                 final EditText ufeedback = feedview.findViewById(R.id.ufeedback);
                 Button update = feedview.findViewById(R.id.update);

                 uname.setText(model.getUsername());
                 ufeedback.setText(model.getFeedback());

                 dialogPlus.show();

                 update.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Map<String,Object> map = new HashMap<>();
                         map.put("username",uname.getText().toString());
                         map.put("feedback",ufeedback.getText().toString());

                         FirebaseDatabase.getInstance().getReference().child("Feedback")
                                 .child(getRef(position).getKey()).updateChildren(map)
                                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                     }
                                 })
                                 .addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                     }
                                 });
                     }
                 });


             }
         });


        holder.Delbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.usernameholder.getContext());
                builder.setTitle("Delete");
                builder.setMessage("Delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Feedback")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });



    }

    @NonNull
    @Override
    public myfeedviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_list,parent,false);
        return new myfeedviewholder(view);
    }

    public class myfeedviewholder extends RecyclerView.ViewHolder {


        TextView usernameholder,feedbackholder;
        Button Delbut,editbut;

        public myfeedviewholder(@NonNull View itemView) {
            super(itemView);

            usernameholder = itemView.findViewById(R.id.usernameholder);
            feedbackholder = itemView.findViewById(R.id.feedbackholder);

            Delbut = itemView.findViewById(R.id.Delbut);
            editbut = itemView.findViewById(R.id.Editbut);

        }
    }
}
