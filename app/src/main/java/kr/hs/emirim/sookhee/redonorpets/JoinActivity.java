package kr.hs.emirim.sookhee.redonorpets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {
    EditText etJoinNickname;
    Spinner spJoinArea;

    String nickname, area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        etJoinNickname = (EditText)findViewById(R.id.joinNicknameEditText);
        spJoinArea = (Spinner)findViewById(R.id.joinAreaSpinner);

        String str = "닉네임과 지역을\n설정해주세요!";
        TextView guide = (TextView)findViewById(R.id.guide);
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        guide.setTextColor(ContextCompat.getColor(this,R.color.black));

        etJoinNickname.setText(nickname);

        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#137ef5")), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        guide.setText(ssb);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#137ef5")), 5, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        guide.setText(ssb);

        spJoinArea.setPrompt("지역을 선택하세요.");
        spJoinArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        etJoinNickname.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( etJoinNickname.getWindowToken(), 0);
                    return true;

                }
                return false;
            }
        });


        TextView next = (TextView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = etJoinNickname.getText().toString().trim();
                area = spJoinArea.getSelectedItem().toString();

                Intent intent = new Intent(getApplicationContext(),JoinActivity2.class);
                intent.putExtra("nickname", nickname);
                intent.putExtra("area", area);
                startActivity(intent);
                finish();
            }
        });

    }

    public void onBackClick(View v){
        super.onBackPressed();
    }
}
