package com.yusufmirza.theyksproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yusufmirza.theyksproject.databinding.AnimationScreenBinding;

public class OpenAnimation extends AppCompatActivity {

     AnimationScreenBinding animationScreenBinding;
     Animation top_anim,bottom_anim;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animationScreenBinding = AnimationScreenBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(animationScreenBinding.getRoot());

        top_anim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottom_anim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        animationScreenBinding.slashImage.setAnimation(top_anim);
        animationScreenBinding.slashName.setAnimation(bottom_anim);
        animationScreenBinding.slahSlogan.setAnimation(bottom_anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(OpenAnimation.this, AuthenticationActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);




    }
}
