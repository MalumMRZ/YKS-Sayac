package com.yusufmirza.theyksproject.followsubject;

import java.util.ArrayList;

public class Subject {

    String subjectName;
    ArrayList<SubSubject> subSubjects;
    boolean subbedSubject;

    public Subject(String subjectName){
        this.subjectName = subjectName;
        this.subbedSubject = false;
    }

    public Subject(String subjectName, ArrayList<SubSubject> subSubjects ){
        this.subjectName = subjectName;
        this.subSubjects= subSubjects;
        this.subbedSubject = true;
    }



}
