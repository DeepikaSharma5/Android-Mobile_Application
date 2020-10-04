package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedrecFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedrecFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recycview;
    Feedbackadapter myadapter;
    Button addfeed;

    public FeedrecFragment() {
        // Required empty public constructor
    }


    public static FeedrecFragment newInstance(String param1, String param2) {
        FeedrecFragment fragment = new FeedrecFragment();
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
        View view= inflater.inflate(R.layout.fragment_feedrec, container, false);

        recycview = (RecyclerView)view.findViewById(R.id.recycview);
        recycview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<ModelFeedback> options=
                new FirebaseRecyclerOptions.Builder<ModelFeedback>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Feedback"),ModelFeedback.class)
                        .build();
        myadapter = new Feedbackadapter(options);
        recycview.setAdapter(myadapter);

        addfeed = (Button)view.findViewById(R.id.addfeed);

        addfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Feedback.class));
            }
        });

        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        myadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myadapter.stopListening();
    }

   
}