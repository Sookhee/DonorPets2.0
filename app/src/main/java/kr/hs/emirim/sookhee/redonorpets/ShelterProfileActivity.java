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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kr.hs.emirim.sookhee.redonorpets.adapter.DonationAdapter;
import kr.hs.emirim.sookhee.redonorpets.adapter.ShelterDonationAdapter;
import kr.hs.emirim.sookhee.redonorpets.adapter.StoryAdapter;
import kr.hs.emirim.sookhee.redonorpets.model.DonationObjectData;
import kr.hs.emirim.sookhee.redonorpets.model.StoryData;

public class ShelterProfileActivity extends AppCompatActivity {
    public static Context mContext;

    TextView tvShelterName;
    TextView tvPhone;
    TextView tvStoryCount;
    TextView tvDonorCount;
    TextView tvLikeCount;
    ImageView ivShelterImg;
    Button btnDonation;

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    StoryAdapter adapter;

    RecyclerView donationRecyclerView;
    ShelterDonationAdapter donationAdapter;


    // Firebase
    private FirebaseDatabase FirebaseDatabase;
    private DatabaseReference shelterDatabaseReference;
    private DatabaseReference storyDatabaseReference = FirebaseDatabase.getInstance().getReference().child("story");
    Query storyQuery;

    //
    private String shelterPosition;
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

        FirebaseDatabase =FirebaseDatabase.getInstance();
        shelterDatabaseReference = FirebaseDatabase.getReference("shelter").child(shelterPosition);
        shelterDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String img = dataSnapshot.child("profileImg").getValue(String.class);
                tvShelterName = findViewById(R.id.shelterNameTextView);
                tvShelterName.setText(dataSnapshot.child("name").getValue(String.class));
                tvPhone = findViewById(R.id.shelterPhoneTextView);
                tvPhone.setText(dataSnapshot.child("phone").getValue(String.class));
                ivShelterImg = findViewById(R.id.shelterProfileImageView);
                Picasso.get().load(img).into(ivShelterImg);
                tvStoryCount = findViewById(R.id.storyCountTextView);
                tvStoryCount.setText((dataSnapshot.child("storyCount").getValue(int.class).toString()));
                tvDonorCount = findViewById(R.id.donorCountTextView);
                tvDonorCount.setText((dataSnapshot.child("donorCount").getValue(int.class).toString()));
                tvLikeCount = findViewById(R.id.likeCountTextView);
                tvLikeCount.setText((dataSnapshot.child("likeCount").getValue(int.class).toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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


        btnDonation = findViewById(R.id.donationButton);
        btnDonation.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent;
                intent = new Intent(getApplicationContext(), DonationActivity.class);
                intent.putExtra("shelterPosition", shelterPosition);
                intent.putExtra("donationObject", donationObject);
                intent.putExtra("realDonationObjectList", realDonationObjectList);
                intent.putExtra("realDonationList", realDonationList);
                startActivity(intent);
            }
        });
    }

    private void setDonationObject(){

        Log.e("CHECK", "들어옴!");
        donationRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        donationAdapter = new ShelterDonationAdapter(this, donationObject);
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
}