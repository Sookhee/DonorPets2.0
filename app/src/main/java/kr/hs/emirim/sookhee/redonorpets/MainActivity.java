package kr.hs.emirim.sookhee.redonorpets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentShelter fragmentShelter = new FragmentShelter();
    private FragmentHome fragmentHome = new FragmentHome();
    private FragmentMypage fragmentMypage = new FragmentMypage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        boolean isLogin = pref.getBoolean("isLogin", false);

        if(isLogin == false) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();

            BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
            bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
            bottomNavigationView.setSelectedItemId(R.id.homeItem);
        }
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.shelterItem:
                    transaction.replace(R.id.frameLayout, fragmentShelter).commitAllowingStateLoss();

                    break;
                case R.id.homeItem:
                    transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();
                    break;
                case R.id.mypageItem:
                    transaction.replace(R.id.frameLayout, fragmentMypage).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}