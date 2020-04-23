package kr.hs.emirim.sookhee.redonorpets;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

public class SettingPreferenceFragment extends PreferenceFragment {

    SharedPreferences prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting_preference);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }
}
