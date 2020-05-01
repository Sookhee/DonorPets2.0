package kr.hs.emirim.sookhee.redonorpets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import kr.hs.emirim.sookhee.redonorpets.adapter.StoryAdapter;

public class ShelterProfileActivity extends AppCompatActivity {
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

        setDonationObject();

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
        donationObject.add(new DonationObjectData("사료", 20));
        donationObject.add(new DonationObjectData("간식", 10));
        donationObject.add(new DonationObjectData("수건", 5));
        donationObject.add(new DonationObjectData("휴지", 5));
        donationObject.add(new DonationObjectData("이불", 30));


        realDonationObjectList.add(new DonationObjectData("사료", 20,true));
        realDonationObjectList.add(new DonationObjectData("수건", 5, true));
        realDonationObjectList.add(new DonationObjectData("휴지", 5, true));

        realDonationList.add("사료");
        realDonationList.add("수건");
        realDonationList.add("휴지");

        donationObject.get(0).setIsDonation(true);
        donationObject.get(2).setIsDonation(true);
        donationObject.get(3).setIsDonation(true);
    }

    public void onBackClick(View v){
        super.onBackPressed();
    }
}