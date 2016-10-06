package com.shawon.yousuf.firebasegoogle.adapter;

// Created by user on 10/6/2016.
//public class StudentListAdapter{

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.shawon.yousuf.firebasegoogle.dialog.DialogAddStudent;
import com.shawon.yousuf.firebasegoogle.model.Student;
import com.shawon.yousuf.firebasegoogle.util.Log;
import com.shawon.yousuf.firebasegoogle.viewholder.StudentViewHolder;

import java.util.Map;

public class StudentListAdapter extends FirebaseRecyclerAdapter<Student, StudentViewHolder> {

    Activity parentActivity;

    private String TAG = getClass().getSimpleName();

    public StudentListAdapter(Activity mActivity, Class<Student> modelClass, int modelLayout, Class<StudentViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        parentActivity = mActivity;
    }

    @Override
    protected void populateViewHolder(StudentViewHolder viewHolder, final Student model, final int position) {
        //DatabaseReference studentRef = getRef(position);
        Log.d(TAG, model.toString());


        // set ClickListener
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked at position: " + position );
                showUpdateDialog(position, model);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.d(TAG, "on long press at: " + position);
                showDeleteDialog(position);
                return true;
            }
        });



        // bind view
        viewHolder.bindToStudent(model);
    }



    private void showUpdateDialog(final int position, Student student){

        DialogAddStudent mDialog = new DialogAddStudent(parentActivity,  "Edit Student", "Update information", student);
        mDialog.setOnSelectListener(new DialogAddStudent.OnSelect() {
            @Override
            public void onOkSelect(Student student) {
                DatabaseReference studentRef = getRef(position);
                Map<String, Object> studentsValue = student.toMap();
                studentRef.updateChildren(studentsValue);

            }

            @Override
            public void onCancelSelect() {

            }
        });

        mDialog.show();

    }

    private void showDeleteDialog(final int position){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(parentActivity);
        mBuilder.setTitle("Alert")
                .setMessage("Want to delete")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Deleting at position: " + position);
                        DatabaseReference studentRef = getRef(position);
                        studentRef.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    Toast.makeText(parentActivity, "Error in deleting data", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        mBuilder.create().show();
    }




}
