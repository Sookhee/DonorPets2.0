package kr.hs.emirim.sookhee.redonorpets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        String str = "닉네임과 지역을\n설정해주세요!";
        TextView guide = (TextView)findViewById(R.id.guide);
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        guide.setTextColor(ContextCompat.getColor(this,R.color.black));

        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#137ef5")), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        guide.setText(ssb);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#137ef5")), 5, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        guide.setText(ssb);

        Spinner s = (Spinner)findViewById(R.id.spinner1);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
}
