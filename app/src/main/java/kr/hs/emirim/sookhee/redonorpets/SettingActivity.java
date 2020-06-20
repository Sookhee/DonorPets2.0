package kr.hs.emirim.sookhee.redonorpets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void onBackClick(View v){
        super.onBackPressed();
    }

    public void onClickLogout(View view){
        Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show();

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isLogin", false);
        editor.putString("userEmail", "");
        editor.commit();

        Intent intent;
        intent = new Intent(view.getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
