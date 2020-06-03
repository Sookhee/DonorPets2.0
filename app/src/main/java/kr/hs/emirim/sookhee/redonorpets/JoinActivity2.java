package kr.hs.emirim.sookhee.redonorpets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class JoinActivity2 extends AppCompatActivity {
    Intent intent;
    EditText etJoinEmail;
    EditText etJoinPasswd;

    String nickname, area, email, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

        etJoinEmail = (EditText)findViewById(R.id.joinEmailEditText);
        etJoinPasswd = (EditText)findViewById(R.id.joinPasswdEditText);

        intent = getIntent();
        nickname = intent.getStringExtra("nickname");
        area = intent.getStringExtra("area");

        String str = "이메일과 비밀번호를\n설정해주세요!";
        TextView guide = (TextView)findViewById(R.id.guide);
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        guide.setTextColor(ContextCompat.getColor(this,R.color.black));

        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#137ef5")), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        guide.setText(ssb);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#137ef5")), 5, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        guide.setText(ssb);


        TextView tvGoLogin = (TextView)findViewById(R.id.next);
        tvGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etJoinEmail.getText().toString();
                passwd = etJoinPasswd.getText().toString();
                Toast.makeText(getApplicationContext(), email + ", " + passwd + ", " + nickname + ", " + area, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                // TODO: 회원가입 예외처리 및 파이어베이스 데이터 삽입
                finish();
            }
        });

        ImageView ivGoBack = (ImageView)findViewById(R.id.backButton);
        ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onBackClick(View v){
        super.onBackPressed();
    }
}
