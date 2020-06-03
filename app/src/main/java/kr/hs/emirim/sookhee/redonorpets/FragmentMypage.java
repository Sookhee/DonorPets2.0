package kr.hs.emirim.sookhee.redonorpets;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.hs.emirim.sookhee.redonorpets.adapter.BadgeAdapter;
import kr.hs.emirim.sookhee.redonorpets.adapter.ShelterAdapter;
import kr.hs.emirim.sookhee.redonorpets.model.BadgeData;
import kr.hs.emirim.sookhee.redonorpets.model.ShelterData;

public class FragmentMypage extends Fragment {
    View mypageView;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ShelterAdapter adapter;
    AppCompatImageView setting;

    CircleImageView ivProfile;
    TextView tvName;
    TextView tvPoint;
    Button btnSetting;
    ProgressBar pbProgressLevel;
    TextView tvProgressLevelName;
    ImageView ivProgressIcon;


    private int userLevel;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userDatabaseReference = database.getReference().child("user").child("0");

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mypage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mypageView = view;

        pbProgressLevel = (ProgressBar)mypageView.findViewById(R.id.userPointProgressBar);
        tvProgressLevelName = (TextView) mypageView.findViewById(R.id.userLevelTextView);
        ivProgressIcon = (ImageView)mypageView.findViewById(R.id.userLevelImageView);

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
                userLevel = dataSnapshot.child("point").getValue(int.class);
                setUserLevelProgress();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSetting = (Button)mypageView.findViewById(R.id.settingButton);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
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

        // 관심있는 보호소
        adapter = new ShelterAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setUserLevelProgress(){
        if(userLevel >= 0 && userLevel < 100){
            pbProgressLevel.setProgress(userLevel);
            ivProgressIcon.setImageResource(R.drawable.donor_level_01);
            tvProgressLevelName.setText("오리지널 도너츠");
        }
        else if(userLevel >= 100 && userLevel < 200){
            pbProgressLevel.setProgress(userLevel-100);
            ivProgressIcon.setImageResource(R.drawable.donor_level_02);
            tvProgressLevelName.setText("딸기시럽 도너츠");
        }
        else if(userLevel >= 200 && userLevel < 300){
            pbProgressLevel.setProgress(userLevel-200);
            ivProgressIcon.setImageResource(R.drawable.donor_level_03);
            tvProgressLevelName.setText("레인보우 도너츠");
        }
        else if(userLevel >= 300 && userLevel < 400){
            pbProgressLevel.setProgress(userLevel-300);
            ivProgressIcon.setImageResource(R.drawable.donor_level_04);
            tvProgressLevelName.setText("킹 도너츠");
        }
        else{
            pbProgressLevel.setProgress(userLevel-400);
            ivProgressIcon.setImageResource(R.drawable.donor_level_05);
            tvProgressLevelName.setText("레인보우 킹 도너츠");
        }
    }
}