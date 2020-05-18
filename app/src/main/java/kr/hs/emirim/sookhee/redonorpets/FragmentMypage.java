package kr.hs.emirim.sookhee.redonorpets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kr.hs.emirim.sookhee.redonorpets.adapter.ShelterAdapter;
import kr.hs.emirim.sookhee.redonorpets.adapter.StoryAdapter;
import kr.hs.emirim.sookhee.redonorpets.model.ShelterData;
import kr.hs.emirim.sookhee.redonorpets.model.StoryData;

public class FragmentMypage extends Fragment {
    View mypageView;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ShelterAdapter adapter;
    AppCompatImageView setting;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference().child("shelter");

    //appcompatimageview는 어케 연결하는거야.... 찾다가 지쳤어 미안해 ㅜㅜ
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setting = (AppCompatImageView)view.findViewById(R.id.settingButton);
//        setting.setOnClickListener(myListener);
//
//    }
//
//    View.OnClickListener myListener = new View.OnClickListener()
//    {
//        @Override
//        public void onClick(View v) {
//
//        }
//    };

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mypage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mypageView = view;

        recyclerView = (RecyclerView)mypageView.findViewById(R.id.mypageShelterRecyclerView);

        resetShelterRecyclerView(mypageView);
    }

    public void resetShelterRecyclerView(View view){

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                ShelterData shelterData = dataSnapshot.getValue(ShelterData.class);

                adapter.addDataAndUpdate(key, shelterData);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                ShelterData shelterData = dataSnapshot.getValue(ShelterData.class);

                adapter.addDataAndUpdate(key, shelterData);
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

        adapter = new ShelterAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}