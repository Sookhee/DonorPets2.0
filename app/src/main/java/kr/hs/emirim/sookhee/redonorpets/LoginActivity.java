package kr.hs.emirim.sookhee.redonorpets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.regex.Pattern;

import kr.hs.emirim.sookhee.redonorpets.model.StoryData;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etPasswd;
    Button btnLogin;
    TextView tvGoJoin;

    String email, passwd;

    FirebaseAuth firebaseAuth;
    private FirebaseDatabase FirebaseDatabase;
    private DatabaseReference userDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = firebaseAuth.getInstance();

        etEmail = (EditText)findViewById(R.id.loginEmailEditText);
        etPasswd = (EditText)findViewById(R.id.loginPasswdEditText);
        btnLogin = (Button)findViewById(R.id.loginButton);
        tvGoJoin = (TextView)findViewById(R.id.loginGoJoinTextView);

        FirebaseDatabase =FirebaseDatabase.getInstance();
        userDatabaseReference = FirebaseDatabase.getReference("user");

        etEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){
                    etPasswd.requestFocus();
                    return true;
                }
                return false;
            }
        });

        etPasswd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( etPasswd.getWindowToken(), 0);
                    return true;

                }
                return false;
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString().trim();
                passwd = etPasswd.getText().toString().trim();

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(getApplicationContext(), "이메일 형식을 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$", passwd)){
                    Toast.makeText(getApplicationContext(), "비밀번호 형식을 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(email, passwd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                                        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                                        final SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("userEmail", email);
                                        editor.putBoolean("isLogin", true);
                                        editor.commit();

                                        Query userQuery = userDatabaseReference.orderByChild("email").equalTo(email);
                                        userQuery.addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                                String key = dataSnapshot.getKey();

                                                Log.e("KEY", key);
                                                editor.putString("userKey", key);
                                            }

                                            @Override
                                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                                String key = dataSnapshot.getKey();

                                                editor.putString("userKey", key);
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

                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "가입되지 않은 계정입니다", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        tvGoJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(intent);
            }
        });


    }
}
