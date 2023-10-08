package com.yusufmirza.theyksproject.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.yusufmirza.theyksproject.MainActivity;
import com.yusufmirza.theyksproject.R;

public class Settings extends Fragment {

    TextView themeView;

    boolean nightmode;
    Switch themeSwitcher;

    Context context;
    SharedPreferences sharedPreferences;

    public Settings(Context context){
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_layout,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = context.getSharedPreferences("MODE",Context.MODE_PRIVATE);

        nightmode = sharedPreferences.getBoolean("night",false);




        themeView = view.findViewById(R.id.theme_view);
        themeSwitcher = view.findViewById(R.id.theme_switcher);

        if (nightmode){
            themeSwitcher.setChecked(true);
        }

        themeSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!themeSwitcher.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    context.setTheme(R.style.Theme_MyApp);
                    sharedPreferences.edit().putBoolean("night",false).apply();

                    MainActivity activity = (MainActivity) requireActivity();
                    activity.reCreate();

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    context.setTheme(R.style.Theme_MyApp_Dark);
                    sharedPreferences.edit().putBoolean("night",true).apply();

                    MainActivity activity = (MainActivity) requireActivity();
                    activity.reCreate();



                }
            }
        });


    }
}
