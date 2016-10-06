package com.shawon.yousuf.firebasegoogle.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shawon.yousuf.firebasegoogle.R;
import com.shawon.yousuf.firebasegoogle.model.Student;


// Created by user on 10/6/2016.


public class StudentViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewName;
    public TextView textViewRoll;
    public TextView textViewAge;

    public StudentViewHolder(View itemView) {
        super(itemView);
        textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        textViewRoll = (TextView) itemView.findViewById(R.id.textViewRoll);
        textViewAge = (TextView) itemView.findViewById(R.id.textViewAge);
    }




    public void bindToStudent(Student student){
        if (student == null) {
            return;
        }

        textViewName.setText("Name: " +  student.getName());
        textViewRoll.setText( "Roll: " + student.getRoll());
        textViewAge.setText( "Age: " +  student.getAge());
    }

}
