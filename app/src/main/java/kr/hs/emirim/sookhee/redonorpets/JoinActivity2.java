package kr.hs.emirim.sookhee.redonorpets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class JoinActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

        String str = "이메일과 비밀번호를\n설정해주세요!";
        TextView guide = (TextView)findViewById(R.id.guide);
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        guide.setTextColor(ContextCompat.getColor(this,R.color.black));

        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#137ef5")), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        guide.setText(ssb);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#137ef5")), 5, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        guide.setText(ssb);

    }
}
