package com.yusufmirza.theyksproject.followsubject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.yusufmirza.theyksproject.R;

public class SubjectDetail extends Fragment {

    Context context;
    EditText editTextNote;

    String id,name,note;
    int number;

    FragmentActivity fragmentActivity;
    Button buttonBackFromSubjectDetail;

    public SubjectDetail(Context context,FragmentActivity fragmentActivity,String id, String name, String note , int number ){
        this.context=context;
        this.fragmentActivity=fragmentActivity;
        this.id=id;
        this.name= name;
        this.note = note;
        this.number = number;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subject_detail,container,false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DBHelper dbHelper = new DBHelper(context);
        editTextNote = view.findViewById(R.id.editTextNote);

        editTextNote.setText(note);

        buttonBackFromSubjectDetail=view.findViewById(R.id.buttonBackFromSubjectDetail);
        buttonBackFromSubjectDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newnote = editTextNote.getText().toString();

                dbHelper.updateData(id,name,newnote,number);

                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmant_place,new FollowLessonsTYT(context,fragmentActivity)).commit();
            }
        });
    }













}
