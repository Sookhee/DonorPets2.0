package kr.hs.emirim.sookhee.redonorpets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentHome extends Fragment {
    View v;
    Context context = getContext();
    TextView tvCheck1, tvCheck2, tvCheck3;
    View vCheck1, vCheck2, vCheck3;

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    StoryAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference().child("story");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        this.v = view;

        recyclerView = (RecyclerView)v.findViewById(R.id.homeStoryRecyclerView);
        adapter = new StoryAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                StoryData story = dataSnapshot.getValue(StoryData.class);

                adapter.addDataAndUpdate(key, story);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                StoryData story = dataSnapshot.getValue(StoryData.class);

                adapter.addDataAndUpdate(key, story);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                adapter.deleteDataAndUpdate(key);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        vCheck1 = v.findViewById(R.id.mainCheckView1);
        vCheck2 = v.findViewById(R.id.mainCheckView2);
        vCheck3 = v.findViewById(R.id.mainCheckView3);

        tvCheck1 = (TextView)v.findViewById(R.id.mainCheckTextView1);
        tvCheck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vCheck1.setVisibility(View.VISIBLE);
                vCheck2.setVisibility(View.INVISIBLE);
                vCheck3.setVisibility(View.INVISIBLE);
                tvCheck1.setTextColor(Color.parseColor("#000000"));
                tvCheck2.setTextColor(Color.parseColor("#b4b4b4"));
                tvCheck3.setTextColor(Color.parseColor("#b4b4b4"));
            }
        });

        tvCheck2 = (TextView)v.findViewById(R.id.mainCheckTextView2);
        tvCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vCheck1.setVisibility(View.INVISIBLE);
                vCheck2.setVisibility(View.VISIBLE);
                vCheck3.setVisibility(View.INVISIBLE);
                tvCheck1.setTextColor(Color.parseColor("#b4b4b4"));
                tvCheck2.setTextColor(Color.parseColor("#000000"));
                tvCheck3.setTextColor(Color.parseColor("#b4b4b4"));
            }
        });

        tvCheck3 = (TextView)v.findViewById(R.id.mainCheckTextView3);
        tvCheck3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vCheck1.setVisibility(View.INVISIBLE);
                vCheck2.setVisibility(View.INVISIBLE);
                vCheck3.setVisibility(View.VISIBLE);
                tvCheck1.setTextColor(Color.parseColor("#b4b4b4"));
                tvCheck2.setTextColor(Color.parseColor("#b4b4b4"));
                tvCheck3.setTextColor(Color.parseColor("#000000"));
            }
        });
    }
}
