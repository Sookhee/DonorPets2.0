package kr.hs.emirim.sookhee.redonorpets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.hs.emirim.sookhee.redonorpets.adapter.ShelterAdapter;
import kr.hs.emirim.sookhee.redonorpets.model.ShelterData;

public class FragmentMypage extends Fragment {
    View mypageView;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ShelterAdapter adapter;

    CircleImageView ivProfile;
    TextView tvName;
    TextView tvPoint;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userDatabaseReference = database.getReference().child("user").child("0");


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mypage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mypageView = view;

        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profileImg = dataSnapshot.child("profileImg").getValue(String.class).toString();
                ivProfile = mypageView.findViewById(R.id.userProfileImageView);
                Picasso.get().load(profileImg).into(ivProfile);
                tvName = mypageView.findViewById(R.id.userNameTextView);
                tvName.setText(dataSnapshot.child("id").getValue(String.class));
                tvPoint = mypageView.findViewById(R.id.userPointTextView);
                tvPoint.setText(dataSnapshot.child("point").getValue(int.class) + "p");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = (RecyclerView)mypageView.findViewById(R.id.mypageShelterRecyclerView);

        resetShelterRecyclerView(mypageView);
    }

    public void resetShelterRecyclerView(View view){

        userDatabaseReference.child("likeShelterList").addChildEventListener(new ChildEventListener() {
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