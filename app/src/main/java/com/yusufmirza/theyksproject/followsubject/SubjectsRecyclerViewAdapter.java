package com.yusufmirza.theyksproject.followsubject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yusufmirza.theyksproject.R;

import java.util.ArrayList;
import java.util.Random;

public class SubjectsRecyclerViewAdapter extends RecyclerView.Adapter<SubjectsRecyclerViewAdapter.SubjectsViewHolder> {

    static ArrayList<Subject> subjects;
    FragmentActivity fragmentActivity;
    Context context;
    DBHelper dbHelper;

    static double ratioCheckBox;


   public SubjectsRecyclerViewAdapter(ArrayList<Subject> subjects, FragmentActivity fragmentActivity,Context context) {
       this.subjects= subjects;
       this.fragmentActivity= fragmentActivity;
       this.context= context;
       dbHelper= new DBHelper(context);
    }


    @NonNull
    @Override
    public SubjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ratioCheckBox=0;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.lessonrecyclerviewcell,parent,false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight()/12);

        return new SubjectsViewHolder(view);






    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsViewHolder holder, int position) {

       String name = subjects.get(position).subjectName;
       Cursor cursor= dbHelper.findData(name);
        holder.textSubjectName.setText(name);

       if(cursor.moveToFirst()){

           if(cursor.getInt(3)==1){
               holder.checkBox.setChecked(true);
           } else {
               holder.checkBox.setChecked(false);
           }



       } else {
           Random random = new Random();
           int uniqueId = random.nextInt();
           String id = Integer.toString(uniqueId);
           String note= " ";
           int number = 0;

           dbHelper.insertData(id,name,note,number);


       }


          holder.checkBox.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  Cursor cursor1 = dbHelper.findData(name);

                      if (cursor1.moveToFirst()) {
                          String id = cursor1.getString(0);
                          String name = cursor1.getString(1);
                          String note = cursor1.getString(2);

                          if (holder.checkBox.isChecked()) {
                              int number = 1;
                              dbHelper.updateData(id, name, note, number);

                          } else {
                              int number = 0;
                              dbHelper.updateData(id, name, note, number);
                          }
                      }

                  }

          });






         holder.imageEdit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Cursor cursor1=dbHelper.findData(name);

                 if (cursor1.moveToFirst()){
                     String id = cursor1.getString(0);
                     String name = cursor1.getString(1);
                     String note = cursor1.getString(2);
                     int number = cursor1.getInt(3);

                     fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmant_place,new SubjectDetail(context,fragmentActivity,id,name,note,number)).commit();



                 }
             }
         });

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }


    public static class SubjectsViewHolder extends RecyclerView.ViewHolder{

       TextView textSubjectName;
       ImageButton imageEdit;
       CheckBox checkBox;
        public SubjectsViewHolder(@NonNull View itemView) {
            super(itemView);
            textSubjectName = itemView.findViewById(R.id.textSubjectName);
            imageEdit = itemView.findViewById(R.id.imageEdit);
            checkBox = itemView.findViewById(R.id.checkBoxSubjects);



        }
    }






}
