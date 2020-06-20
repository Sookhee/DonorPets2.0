package kr.hs.emirim.sookhee.redonorpets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import kr.hs.emirim.sookhee.redonorpets.model.CommentData;
import kr.hs.emirim.sookhee.redonorpets.model.UserData;

public class JoinActivity2 extends AppCompatActivity {
    Intent intent;
    EditText etJoinEmail;
    EditText etJoinPasswd;

    String nickname, area, email, passwd;

    private DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user");
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

        firebaseAuth = firebaseAuth.getInstance();

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


        etJoinEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){
                    etJoinPasswd.requestFocus();
                    return true;

                }
                return false;
            }
        });

        etJoinPasswd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( etJoinPasswd.getWindowToken(), 0);
                    return true;

                }
                return false;
            }
        });

        TextView tvGoLogin = (TextView)findViewById(R.id.next);
        tvGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etJoinEmail.getText().toString();
                passwd = etJoinPasswd.getText().toString();

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(getApplicationContext(), "올바른 이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
                }
                else if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{6,15}.$", passwd)){
                    Toast.makeText(getApplicationContext(), "올바른 비밀번호 형식이 아닙니다", Toast.LENGTH_SHORT).show();
                }
                else{

                    firebaseAuth.createUserWithEmailAndPassword(email, passwd)
                            .addOnCompleteListener(JoinActivity2.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        UserData userData = new UserData(nickname, email, area);
                                        userDatabaseReference.push().setValue(userData);

                                        Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(JoinActivity2.this, "이미 가입된 메일입니다", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });

                }

            }
        });

        ImageView ivGoBack = (ImageView)findViewById(R.id.backButton);
        ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(newIntent);
                finish();
            }
        });
    }

}
