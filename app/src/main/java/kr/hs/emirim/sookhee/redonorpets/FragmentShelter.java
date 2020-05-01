package kr.hs.emirim.sookhee.redonorpets;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import kr.hs.emirim.sookhee.redonorpets.adapter.ShelterLargeAdapter;
import kr.hs.emirim.sookhee.redonorpets.model.ShelterData;

public class FragmentShelter extends Fragment {
    View shelterView;
    Context context = getContext();
    TextView tvCheck1, tvCheck2, tvCheck3;
    View vCheck1, vCheck2, vCheck3;

    RecyclerView recyclerView;
    GridLayoutManager mLayoutManager;
    ShelterLargeAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference().child("shelter");
    Query shelterQuery = mRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_shelter, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        this.shelterView = view;

        recyclerView = (RecyclerView)shelterView.findViewById(R.id.shelterRecyclerView);

        resetShelterRecyclerView(shelterView);

        vCheck1 = shelterView.findViewById(R.id.shelterCheckView1);
        vCheck2 = shelterView.findViewById(R.id.shelterCheckView2);
        vCheck3 = shelterView.findViewById(R.id.shelterCheckView3);

        tvCheck1 = (TextView)shelterView.findViewById(R.id.shelterCheckTextView1);
        tvCheck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vCheck1.setVisibility(View.VISIBLE);
                vCheck2.setVisibility(View.INVISIBLE);
                vCheck3.setVisibility(View.INVISIBLE);
                tvCheck1.setTextColor(Color.parseColor("#137ef5"));
                tvCheck2.setTextColor(Color.parseColor("#b4b4b4"));
                tvCheck3.setTextColor(Color.parseColor("#b4b4b4"));
                shelterQuery = mRef;
                resetShelterRecyclerView(shelterView);
            }
        });

        tvCheck2 = (TextView)shelterView.findViewById(R.id.shelterCheckTextView2);
        tvCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vCheck1.setVisibility(View.INVISIBLE);
                vCheck2.setVisibility(View.VISIBLE);
                vCheck3.setVisibility(View.INVISIBLE);
                tvCheck1.setTextColor(Color.parseColor("#b4b4b4"));
                tvCheck2.setTextColor(Color.parseColor("#137ef5"));
                tvCheck3.setTextColor(Color.parseColor("#b4b4b4"));
                shelterQuery = mRef.orderByChild("region").equalTo("수도권");
                resetShelterRecyclerView(shelterView);

            }
        });

        tvCheck3 = (TextView)shelterView.findViewById(R.id.shelterCheckTextView3);
        tvCheck3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vCheck1.setVisibility(View.INVISIBLE);
                vCheck2.setVisibility(View.INVISIBLE);
                vCheck3.setVisibility(View.VISIBLE);
                tvCheck1.setTextColor(Color.parseColor("#b4b4b4"));
                tvCheck2.setTextColor(Color.parseColor("#b4b4b4"));
                tvCheck3.setTextColor(Color.parseColor("#137ef5"));
                shelterQuery = mRef.orderByChild("region").equalTo("강원도");
                resetShelterRecyclerView(shelterView);

            }
        });
    }

    public void resetShelterRecyclerView(View view) {
        adapter = new ShelterLargeAdapter(getActivity());
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        shelterQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                ShelterData shelter = dataSnapshot.getValue(ShelterData.class);

                adapter.addDataAndUpdate(key, shelter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                ShelterData shelter = dataSnapshot.getValue(ShelterData.class);

                adapter.addDataAndUpdate(key, shelter);
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
    }
}
