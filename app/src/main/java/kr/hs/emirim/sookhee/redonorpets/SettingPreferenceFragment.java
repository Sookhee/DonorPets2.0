package kr.hs.emirim.sookhee.redonorpets;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;

public class SettingPreferenceFragment extends PreferenceFragment {

    SharedPreferences prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting_preference);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }
}
