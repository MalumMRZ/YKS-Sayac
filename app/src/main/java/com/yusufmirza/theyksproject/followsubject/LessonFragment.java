package com.yusufmirza.theyksproject.followsubject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yusufmirza.theyksproject.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LessonFragment extends Fragment {

    Context context;
    RecyclerView recyclerView;
    ArrayList<Subject> subjects;
    FragmentActivity fragmentActivity;
    String lessonName;
    Button backButton;

    static SubjectsRecyclerViewAdapter subjectsRecyclerViewAdapter;


    public LessonFragment(Context context, ArrayList<Subject> subjects,FragmentActivity fragmentActivity,String lessonName){
        this.context= context;
        this.subjects = subjects;
        this.fragmentActivity=fragmentActivity;
        this.lessonName= lessonName;
    }

    public LessonFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lesson_fragment,container,false);
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = view.findViewById(R.id.button_back);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmant_place,new FollowYourLessons(context,fragmentActivity)).commit();
            }
        });

        recyclerView = view.findViewById(R.id.lessonsRecyclerView);
        subjectsRecyclerViewAdapter =new SubjectsRecyclerViewAdapter(subjects,fragmentActivity,context);
        recyclerView.setAdapter(subjectsRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));



    }

}
