package kr.hs.emirim.sookhee.redonorpets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoryDetailActivity extends AppCompatActivity {

    TextView tvTitle;
    TextView tvDate;
    TextView tvShelterName;
    ImageView ivShelterImg;
    LinearLayout lStoryContent;
    View view;

    // Firebase
    private FirebaseDatabase FirebaseDatabase;
    private DatabaseReference storyDatabaseReference;
    private DatabaseReference shelterDatabaseReference;

    private String storyPosition;
    private String shelterPosition;
    private int text = 0, img = 0;
    final ArrayList<String> storyImgList = new ArrayList<>();
    final ArrayList<String> storyTextList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        view = getLayoutInflater().from(this).inflate(R.layout.activity_story_detail, null);
        Intent intent = getIntent();
        storyPosition = intent.getExtras().getString("storyPosition");
        shelterPosition = intent.getExtras().getString("shelterPosition");

        FirebaseDatabase =FirebaseDatabase.getInstance();

        lStoryContent = (LinearLayout)findViewById(R.id.storyContentLayout);
        storyDatabaseReference = FirebaseDatabase.getReference("story").child(storyPosition);
        storyDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvTitle = findViewById(R.id.titleTextView);
                tvTitle.setText(dataSnapshot.child("title").getValue(String.class));
                tvDate = findViewById(R.id.dateTextView);
                tvDate.setText(dataSnapshot.child("date").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        shelterDatabaseReference = FirebaseDatabase.getReference("shelter").child(shelterPosition);
        shelterDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String img = dataSnapshot.child("profileImg").getValue(String.class);
                tvShelterName = findViewById(R.id.shelterNameTextView);
                tvShelterName.setText(dataSnapshot.child("name").getValue(String.class));
                ivShelterImg = findViewById(R.id.shelterProfileImageView);
                Picasso.get().load(img).into(ivShelterImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onBackClick(View v){
        super.onBackPressed();
    }

    public void onShelterProfileClick(View v){
        Intent intent;
        intent = new Intent(v.getContext(), ShelterProfileActivity.class);
        intent.putExtra("shelterPosition", shelterPosition);
        startActivity(intent);
    }

    }
}
