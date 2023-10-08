package com.yusufmirza.theyksproject.followsubject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.yusufmirza.theyksproject.R;
import com.yusufmirza.theyksproject.followsubject.FollowLessonsTYT;

public class FollowYourLessons extends Fragment {

    Context context;
    LinearLayout linearLayoutTYT,linearLayoutAYT;
    FragmentActivity fragmentActivity;

    public FollowYourLessons(){

    }
    public FollowYourLessons(Context context,FragmentActivity fragmentActivity){
        this.context=context;
        this.fragmentActivity= fragmentActivity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainlessonfollow_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayoutTYT = view.findViewById(R.id.linearLayoutTYT);
        linearLayoutAYT = view.findViewById(R.id.linearLayoutAYT);




        linearLayoutTYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Fragment fragment =new FollowLessonsTYT(context,fragmentActivity);
             switchToFragment(fragment);

            }
        });

    }

    public void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmant_place,fragment);
        transaction.commit();
    }

}
