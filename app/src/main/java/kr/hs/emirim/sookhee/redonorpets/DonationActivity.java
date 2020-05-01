package kr.hs.emirim.sookhee.redonorpets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kr.hs.emirim.sookhee.redonorpets.adapter.DonationAdapter;
import kr.hs.emirim.sookhee.redonorpets.model.DonationObjectData;

public class DonationActivity extends AppCompatActivity {
    public static Context mContext;
    TextView tvShelterName;
    TextView tvPhone;
    TextView tvTotalPoint;
    ImageView ivShelterImg;
    View vCheck1, vCheck2, vCheck3, vCheck4;
    ImageView ivCheck1, ivCheck2, ivCheck3, ivCheck4;
    Button btnSubmit;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    // Firebase
    private com.google.firebase.database.FirebaseDatabase FirebaseDatabase;
    private DatabaseReference shelterDatabaseReference;
    private DatabaseReference donationDatabaseReference;

    //
    private String shelterPosition;
    private int totalPoint = 0;
    private ArrayList<DonationObjectData> donationObject = new ArrayList<>();
    private ArrayList<DonationObjectData> realDonationObjectList = new ArrayList<>();
    private ArrayList<String> realDonationList = new ArrayList<>();
    private boolean [] checkTerms = new boolean[]{false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        this.setTheme(R.style.MeterialComponents);
        mContext = this;

        Intent intent = getIntent();
        shelterPosition = intent.getExtras().getString("shelterPosition");
        donationObject = (ArrayList<DonationObjectData>)intent.getSerializableExtra("donationObject");
        realDonationObjectList = (ArrayList<DonationObjectData>)intent.getSerializableExtra("realDonationObjectList");
        realDonationList = intent.getExtras().getStringArrayList("realDonationList");
        DisplayMetrics dm = this.getResources().getDisplayMetrics();

        tvTotalPoint = (TextView)findViewById(R.id.donationPointTextView);
        tvTotalPoint.setText(Integer.toString(totalPoint));

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 기부물품 선택 Chip 생성
        ChipGroup chipGroup = findViewById(R.id.donationObjectGroup);
        for(int i = 0; i < donationObject.size(); i++){
            final Chip chip = new Chip(this); // Must contain context in parameter
            chip.setTag(i);
            chip.setChipDrawable(ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Action));
            chip.setText(donationObject.get(i).getObject());
            chip.setCheckable(true);
            chip.setCheckedIconVisible(false);
            chip.setChipIconSize(dm.density*15);
            chip.setHeight((int)dm.density*70);
            chip.setChipStartPadding(dm.density*9);
            if(donationObject.get(i).getIsDonation()){
                chip.setChipIcon(getResources().getDrawable(R.drawable.icon_check_true));
                chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.deep_sky_blue)));
                setTotalPoint(donationObject.get(i).getPoint(), true);
            }else{
                chip.setChipIcon(getResources().getDrawable(R.drawable.icon_check_false));
                chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.greyish)));
            }
            chip.setTextColor(getResources().getColor(R.color.white));
            chip.setTypeface(ResourcesCompat.getFont(this, R.font.notosanscjkkr_medium));
            chip.setTextStartPadding(dm.density*14);
            chip.setTextEndPadding(dm.density*25);
            chip.setElevation(dm.density*4);
            chip.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int tag = (int)v.getTag();
                    if(!donationObject.get(tag).getIsDonation()){
                        donationObject.get(tag).setIsDonation(true);
                        realDonationList.add(donationObject.get(tag).getObject());
                        realDonationObjectList.add(new DonationObjectData(donationObject.get(tag).getObject(), donationObject.get(tag).getPoint(), true));
                        chip.setChipIcon(getResources().getDrawable(R.drawable.icon_check_true));
                        chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(v.getContext(), R.color.deep_sky_blue)));
                        setTotalPoint(donationObject.get(tag).getPoint(), true);
                        resetList();
                    }else{
                        setTotalPoint((realDonationObjectList.get(realDonationList.indexOf(donationObject.get(tag).getObject())).getCount() * donationObject.get(tag).getPoint()), false);
                        donationObject.get(tag).setIsDonation(false);
                        donationObject.get(tag).setCount(1);
                        realDonationObjectList.remove(realDonationList.indexOf(donationObject.get(tag).getObject()));
                        realDonationList.remove(donationObject.get(tag).getObject());
                        chip.setChipIcon(getResources().getDrawable(R.drawable.icon_check_false));
                        chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(v.getContext(), R.color.greyish)));
                        resetList();
                    }
                }
            });
            chipGroup.addView(chip);
        }

        recyclerView = (RecyclerView)findViewById(R.id.donationObjectCountRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new DonationAdapter(this, realDonationObjectList);
        recyclerView.setAdapter(mAdapter);

        // 약관 동의
        ivCheck1=(ImageView)findViewById(R.id.checkImg1);
        ivCheck2=(ImageView)findViewById(R.id.checkImg2);
        ivCheck3=(ImageView)findViewById(R.id.checkImg3);
        ivCheck4=(ImageView)findViewById(R.id.checkImg4);

        vCheck1=(View)findViewById(R.id.check1);
        vCheck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkTerms[0] == false){
                    ivCheck1.setVisibility(View.VISIBLE);
                    checkTerms[0] = true;

                    ivCheck2.setVisibility(View.VISIBLE);
                    checkTerms[1] = true;

                    ivCheck3.setVisibility(View.VISIBLE);
                    checkTerms[2] = true;

                    ivCheck4.setVisibility(View.VISIBLE);
                    checkTerms[3] = true;
                }
                else{
                    ivCheck1.setVisibility(View.INVISIBLE);
                    checkTerms[0] = false;

                    ivCheck2.setVisibility(View.INVISIBLE);
                    checkTerms[1] = false;

                    ivCheck3.setVisibility(View.INVISIBLE);
                    checkTerms[2] = false;

                    ivCheck4.setVisibility(View.INVISIBLE);
                    checkTerms[3] = false;
                }
            }
        });

        vCheck2=(View)findViewById(R.id.check2);
        vCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkTerms[1] == false){
                    ivCheck2.setVisibility(View.VISIBLE);
                    checkTerms[1] = true;
                }
                else{
                    ivCheck2.setVisibility(View.INVISIBLE);
                    checkTerms[1] = false;
                }
            }
        });

        vCheck3=(View)findViewById(R.id.check3);
        vCheck3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkTerms[2] == false){
                    ivCheck3.setVisibility(View.VISIBLE);
                    checkTerms[2] = true;
                }
                else{
                    ivCheck3.setVisibility(View.INVISIBLE);
                    checkTerms[2] = false;
                }
            }
        });

        vCheck4=(View)findViewById(R.id.check4);
        vCheck4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkTerms[3] == false){
                    ivCheck4.setVisibility(View.VISIBLE);
                    checkTerms[3] = true;
                }
                else{
                    ivCheck4.setVisibility(View.INVISIBLE);
                    checkTerms[3] = false;
                }
            }
        });

        // 기부 폼 제출
        btnSubmit = (Button)findViewById(R.id.submitButton);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkTerms[1] == true && checkTerms[2] && totalPoint > 0){
                    Toast.makeText(getApplicationContext(), "제출", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(totalPoint == 0){
                    Toast.makeText(getApplicationContext(), "기부할 항목을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "필수 항목을 모두 동의해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onBackClick(View v){
        super.onBackPressed();
    }

    protected void resetList(){
        mAdapter = new DonationAdapter(this, realDonationObjectList);
        recyclerView.setAdapter(mAdapter);
    }

    public void setTotalPoint(int point, boolean isCheck){
        if(isCheck){
            totalPoint += point;
            tvTotalPoint.setText(Integer.toString(totalPoint));
        }
        else{
            totalPoint -= point;
            tvTotalPoint.setText(Integer.toString(totalPoint));
        }
    }
}
