package kr.hs.emirim.sookhee.redonorpets;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
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
    TextView tvChatSubmit;
    EditText etChat;
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

        storyDatabaseReference.child("text").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String text = postSnapshot.getValue(String.class);
                    storyTextList.add(text);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        storyDatabaseReference.child("img").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String img = postSnapshot.getValue(String.class);
                    storyImgList.add(img);
                }
                setStoryContentLayout(0, 0);
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

        etChat = findViewById(R.id.storyDetailChatEditText);
        tvChatSubmit = findViewById(R.id.storyDetailChatSubmitButton);
        tvChatSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = etChat.getText().toString();
                Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_SHORT).show();
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

    public void setStoryContentLayout(int text, int img){
        lStoryContent.removeAllViews();
        do{
            Log.e( "들어옴", "" + this.storyTextList.size() + " " + this.storyImgList.size());
            if(text < storyTextList.size()){
                TextView tv = new TextView(this);
                tv.setText(storyTextList.get(text));
                tv.setLineSpacing(0, 1.1f);
                tv.setPadding(0, (int)convertDpToPixel(15), 0, 0);
                tv.setTypeface(ResourcesCompat.getFont(this, R.font.notosanscjkkr_regular));
                tv.setIncludeFontPadding(false);
                tv.setTextColor(Color.BLACK);
                lStoryContent.addView(tv);
            }
            if(img < storyImgList.size()){
                ImageView iv = new ImageView(this);
                Picasso.get().load(storyImgList.get(img)).into(iv);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setPadding(0, (int)convertDpToPixel(15), 0, 0);
                lStoryContent.addView(iv);
            }
            text++;
            img++;
        }while(text < storyTextList.size() || img < storyImgList.size());
    }

    public float convertDpToPixel(float dp){
        Resources resources = this.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

}
