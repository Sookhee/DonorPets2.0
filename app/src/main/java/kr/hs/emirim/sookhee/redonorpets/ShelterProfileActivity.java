package kr.hs.emirim.sookhee.redonorpets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kr.hs.emirim.sookhee.redonorpets.adapter.ShelterDonationAdapter;
import kr.hs.emirim.sookhee.redonorpets.adapter.StoryAdapter;
import kr.hs.emirim.sookhee.redonorpets.model.DonationObjectData;
import kr.hs.emirim.sookhee.redonorpets.model.StoryData;
import kr.hs.emirim.sookhee.redonorpets.ui.home.StoryFeedAdapter;

public class ShelterProfileActivity extends AppCompatActivity {
    public static Context mContext;

    TextView tvShelterName;
    TextView tvPhone;
    TextView tvStoryCount;
    TextView tvDonorCount;
    TextView tvLikeCount;
    ImageView ivShelterImg;
    Button btnDonation;
    Button btnShelterLike;

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    StoryAdapter adapter;

    RecyclerView donationRecyclerView;
    LinearLayoutManager donationLayoutManager;
    ShelterDonationAdapter donationAdapter;


    // Firebase
    private FirebaseDatabase FirebaseDatabase;
    private DatabaseReference shelterDatabaseReference;
    private DatabaseReference storyDatabaseReference = FirebaseDatabase.getInstance().getReference().child("story");
    private DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user").child("-MAFju1Ieq4ZfwhhdhsR");
    Query storyQuery;

    //
    private String shelterPosition, shelterName, shelterPhone, shelterImg;
    private Boolean isLike = false;
    private int shelterLikeCount, shelterStoryCount;
    private ArrayList<DonationObjectData> donationObject = new ArrayList<>();
    private ArrayList<DonationObjectData> realDonationObjectList = new ArrayList<>();
    private ArrayList<String> realDonationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_profile);

        mContext = this;
        Intent intent = getIntent();
        shelterPosition = intent.getExtras().getString("shelterPosition");

        //보호소 프로필 정보 세팅
        FirebaseDatabase =FirebaseDatabase.getInstance();
        shelterDatabaseReference = FirebaseDatabase.getReference("shelter").child(shelterPosition);
        shelterDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shelterName = dataSnapshot.child("name").getValue(String.class);
                tvShelterName = findViewById(R.id.shelterNameTextView);
                tvShelterName.setText(shelterName);

                shelterPhone = dataSnapshot.child("phone").getValue(String.class);
                tvPhone = findViewById(R.id.shelterPhoneTextView);
                tvPhone.setText(shelterPhone);

                shelterImg = dataSnapshot.child("profileImg").getValue(String.class);
                ivShelterImg = findViewById(R.id.shelterProfileImageView);
                Picasso.get().load(shelterImg).into(ivShelterImg);

                shelterStoryCount = dataSnapshot.child("storyCount").getValue(int.class);
                tvStoryCount = findViewById(R.id.storyCountTextView);
                tvStoryCount.setText(Integer.toString(shelterStoryCount));

                tvDonorCount = findViewById(R.id.donorCountTextView);
                tvDonorCount.setText((dataSnapshot.child("donorCount").getValue(int.class).toString()));

                shelterLikeCount = Integer.parseInt(dataSnapshot.child("likeCount").getValue(int.class).toString());
                tvLikeCount = findViewById(R.id.likeCountTextView);
                tvLikeCount.setText((dataSnapshot.child("likeCount").getValue(int.class).toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //보호소 스토리 recyclerView 세팅
        recyclerView = (RecyclerView)findViewById(R.id.shelterProfileRecyclerView);
        storyQuery = storyDatabaseReference.orderByChild("shelterPosition").equalTo(shelterPosition).limitToLast(2);
        storyQuery.addChildEventListener(new ChildEventListener() {
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

        //보호소 기부 물품 받아오기
        donationRecyclerView = (RecyclerView)findViewById(R.id.shelterDonationObjectRecyclerView);
        FirebaseDatabase.getReference("shelter").child(shelterPosition).child("donationObject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DonationObjectData object = snapshot.getValue(DonationObjectData.class);
                    donationObject.add(object);
                }
                setDonationObject();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new StoryAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        //보호소 기부 버튼 눌렀을 때
        btnDonation = findViewById(R.id.donationButton);
        btnDonation.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                if(realDonationList.size() > 0){
                    Intent intent;
                    intent = new Intent(getApplicationContext(), DonationActivity.class);
                    intent.putExtra("shelterPosition", shelterPosition);
                    intent.putExtra("donationObject", donationObject);
                    intent.putExtra("realDonationObjectList", realDonationObjectList);
                    intent.putExtra("realDonationList", realDonationList);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "기부 물품을 먼저 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //보호소 좋아요 버튼 기능 구현
        userDatabaseReference.child("likeShelter").child(shelterPosition).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                boolean isLike = (Boolean)dataSnapshot.getValue();
                if(isLike == true){
                    btnShelterLike.setBackgroundResource(R.drawable.icon_like_blue);
                    setIsLike(true);
                }
                else{
                    btnShelterLike.setBackgroundResource(R.drawable.icon_like_white);
                    setIsLike(false);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                boolean isLike = (boolean)dataSnapshot.getValue();
                if(isLike == true){
                    btnShelterLike.setBackgroundResource(R.drawable.icon_like_blue);
                    setIsLike(true);
                }
                else{
                    btnShelterLike.setBackgroundResource(R.drawable.icon_like_white);
                    setIsLike(false);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnShelterLike = (Button)findViewById(R.id.shelterLikeButton);
        btnShelterLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLike){ //true -> false

                    //관심있는 보호소 false
                    Map<String, Object> isLikeMap = new HashMap<String, Object>();
                    isLikeMap.put(shelterPosition+"/isLike", false);
                    userDatabaseReference.child("likeShelter").updateChildren(isLikeMap);

                    //이 보호소를 좋아하는 사람들 count +1
                    Map<String, Object> shelterCountMap = new HashMap<String, Object>();
                    shelterCountMap.put("likeCount", shelterLikeCount-1);
                    shelterDatabaseReference.updateChildren(shelterCountMap);

                    //관심있는 보호소 리스트에서 삭제
                    userDatabaseReference.child("likeShelterList").child(shelterPosition).removeValue();

                }else{ //false -> true
                    //관심있는 보호소 true
                    Map<String, Object> isLikeMap = new HashMap<String, Object>();
                    isLikeMap.put(shelterPosition+"/isLike", true);
                    userDatabaseReference.child("likeShelter").updateChildren(isLikeMap);

                    //이 보호소를 좋아하는 사람들 count +1
                    Map<String, Object> shelterCountMap = new HashMap<String, Object>();
                    shelterCountMap.put("likeCount", shelterLikeCount+1);
                    shelterDatabaseReference.updateChildren(shelterCountMap);

                    //관심있는 보호소 리스트에 추가
                    Map<String, Object> likeShelterListMap = new HashMap<String, Object>();
                    likeShelterListMap.put("name", shelterName);
                    likeShelterListMap.put("phone", shelterPhone);
                    likeShelterListMap.put("profileImg", shelterImg);
                    likeShelterListMap.put("shelterPosition", shelterPosition);
                    likeShelterListMap.put("storyCount", shelterStoryCount);
                    userDatabaseReference.child("likeShelterList").child(shelterPosition).updateChildren(likeShelterListMap);

                }
            }
        });
    }

    //보호소 기부 물품 recyclerView 세팅
    private void setDonationObject(){
        donationAdapter = new ShelterDonationAdapter(this, donationObject);
        donationLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        donationRecyclerView.setLayoutManager(donationLayoutManager);
        donationLayoutManager.setReverseLayout(true);
        donationLayoutManager.setStackFromEnd(true);
        donationRecyclerView.setAdapter(donationAdapter);
    }

    public void addRealDonationObjectList(String object, int point, int tag){
        realDonationObjectList.add(new DonationObjectData(object, point,true));
        realDonationList.add(object);
        donationObject.get(tag).setIsDonation(true);
    }

    public void removeRealDonationObjectList(String object, int tag){
        int index = realDonationList.indexOf(object);
        realDonationObjectList.remove(index);
        realDonationList.remove(index);
        donationObject.get(tag).setIsDonation(false);
    }

    public void onBackClick(View v){
        super.onBackPressed();
    }

    public void setIsLike(boolean isLike){
        this.isLike = isLike;
    }
}