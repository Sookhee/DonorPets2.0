package kr.hs.emirim.sookhee.redonorpets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.hs.emirim.sookhee.redonorpets.adapter.CommentAdapter;
import kr.hs.emirim.sookhee.redonorpets.model.CommentData;
import kr.hs.emirim.sookhee.redonorpets.model.StoryData;

public class StoryDetailActivity extends AppCompatActivity {

    TextView tvTitle;
    TextView tvDate;
    TextView tvShelterName;
    TextView tvLikeCount;
    TextView tvCommentSubmit;
    EditText etComment;
    Button btnStoryLike;
    ImageView ivShelterImg;
    CircleImageView ivUserProfile;
    LinearLayout lStoryContent;
    View view;

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    CommentAdapter adapter;

    // Firebase
    private FirebaseDatabase FirebaseDatabase;
    private DatabaseReference storyDatabaseReference;
    private DatabaseReference shelterDatabaseReference;
    private DatabaseReference userDatabaseReference;

    private String storyPosition;
    private String shelterPosition;
    private boolean isLike = false;
    private String userEmail = "";
    private int text = 0, img = 0;
    private int storyLikeCount, commentCount;
    final ArrayList<String> storyImgList = new ArrayList<>();
    final ArrayList<String> storyTextList = new ArrayList<>();
    private String userName = "temporary", userProfile, putUserIsLike = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        userEmail = pref.getString("userEmail", "");

        view = getLayoutInflater().from(this).inflate(R.layout.activity_story_detail, null);
        Intent intent = getIntent();
        storyPosition = intent.getExtras().getString("storyPosition");
        shelterPosition = intent.getExtras().getString("shelterPosition");

        btnStoryLike = (Button) findViewById(R.id.storyLkeButton);
        tvLikeCount = findViewById(R.id.storyLikeCountTextView);
        recyclerView = (RecyclerView)findViewById(R.id.storyDetailCommentLayout);
        ivUserProfile = findViewById(R.id.storyDetailCommentUserProfileImageView);

        FirebaseDatabase =FirebaseDatabase.getInstance();

        lStoryContent = (LinearLayout)findViewById(R.id.storyContentLayout);

        //사용자 정보 불러오기
        userDatabaseReference = FirebaseDatabase.getReference("user");
        Query userQuery = userDatabaseReference.orderByChild("email").equalTo(userEmail);
        userQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                userName = dataSnapshot.child("id").getValue(String.class);
                userProfile = dataSnapshot.child("profileImg").getValue(String.class);
                Picasso.get().load(userProfile).into(ivUserProfile);
                putUserIsLike = (userName + "/isLike");
                Toast.makeText(getApplicationContext(), putUserIsLike, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
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

        //스토리 기본 정보 불러오기
        storyDatabaseReference = FirebaseDatabase.getReference("story").child(storyPosition);
        storyDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvTitle = findViewById(R.id.titleTextView);
                tvTitle.setText(dataSnapshot.child("title").getValue(String.class));
                tvDate = findViewById(R.id.dateTextView);
                tvDate.setText(dataSnapshot.child("date").getValue(String.class));
                commentCount = dataSnapshot.child("commentCount").getValue(int.class);
                storyLikeCount = dataSnapshot.child("likeCount").getValue(int.class);
                tvLikeCount.setText(storyLikeCount + "명이 응원합니다!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 스토리 컨텐츠(text) 불러오기
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


        // 스토리 컨텐츠(img) 불러오기
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

        // 보호소 프로필 불러오기
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

        //스토리 좋아요 버튼 기능 구현
        Query storyLikeQuery = storyDatabaseReference.child("liker").child(userName);
        storyDatabaseReference.child("liker").child("ggkgk").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                boolean isLike = (Boolean)dataSnapshot.getValue();
                if(isLike == true){
                    btnStoryLike.setBackgroundResource(R.drawable.icon_like_blue);
                    setIsLike(true);
                }
                else{
                    btnStoryLike.setBackgroundResource(R.drawable.icon_like_gray);
                    setIsLike(false);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                boolean isLike = (boolean)dataSnapshot.getValue();
                if(isLike == true){
                    btnStoryLike.setBackgroundResource(R.drawable.icon_like_blue);
                    setIsLike(true);
                }
                else{
                    btnStoryLike.setBackgroundResource(R.drawable.icon_like_gray);
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

        btnStoryLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLike){
                    //true -> false
                    Map<String, Object> isLikeMap = new HashMap<String, Object>();
                    isLikeMap.put(putUserIsLike, false);

                    Map<String, Object> storyCountMap = new HashMap<String, Object>();
                    storyCountMap.put("likeCount", storyLikeCount-1);

                    storyDatabaseReference.child("liker").updateChildren(isLikeMap);
                    storyDatabaseReference.updateChildren(storyCountMap);

                }else{
                    //false -> true
                    Map<String, Object> isLikeMap = new HashMap<String, Object>();
                    isLikeMap.put(putUserIsLike, true);

                    Map<String, Object> storyCountMap = new HashMap<String, Object>();
                    storyCountMap.put("likeCount", storyLikeCount+1);

                    storyDatabaseReference.child("liker").updateChildren(isLikeMap);
                    storyDatabaseReference.updateChildren(storyCountMap);
                }
            }
        });


        // 스토리 댓글 기능 구현
        storyDatabaseReference.child("comment").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                CommentData commentData = dataSnapshot.getValue(CommentData.class);

                adapter.addDataAndUpdate(key, commentData);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                CommentData commentData = dataSnapshot.getValue(CommentData.class);

                adapter.addDataAndUpdate(key, commentData);
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

        adapter = new CommentAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        // 댓글 작성
        etComment = findViewById(R.id.storyDetailCommentEditText);
        tvCommentSubmit = findViewById(R.id.storyDetailCommentSubmitButton);
        tvCommentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etComment.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    etComment.setText("");
                    CommentData commentData = new CommentData();
                    commentData.setName(userName);
                    commentData.setContent(message);
                    commentData.setImg(userProfile);
                    storyDatabaseReference.child("comment").push().setValue(commentData);

                    Map<String, Object> commentCountMap = new HashMap<String, Object>();
                    commentCountMap.put("commentCount", commentCount+1);

                    storyDatabaseReference.updateChildren(commentCountMap);
                }
            }
        });

    }

    public void onBackClick(View v){
        super.onBackPressed();
    }

    public void setIsLike(boolean isLike){
        this.isLike = isLike;
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
