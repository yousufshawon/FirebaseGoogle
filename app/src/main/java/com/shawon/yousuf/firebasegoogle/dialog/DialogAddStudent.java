package com.shawon.yousuf.firebasegoogle.dialog;
//Created by Yousuf on 10/4/2016.

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shawon.yousuf.firebasegoogle.R;
import com.shawon.yousuf.firebasegoogle.model.Student;
import com.shawon.yousuf.firebasegoogle.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class DialogAddStudent {

    @Bind(R.id.editTextName)
    EditText editTextName;
    @Bind(R.id.editTextRoll)
    EditText editTextRoll;
    @Bind(R.id.editTextAge)
    EditText editTextAge;
    @Bind(R.id.buttonOk)
    Button buttonOk;
    @Bind(R.id.buttonCancel)
    Button buttonCancel;


    private Context context;
    private String title;
    private String message;
    AlertDialog mDialog;

    private Student currentStudent;

    private String TAG = getClass().getSimpleName();



    public interface OnSelect {
        public void onOkSelect(Student student);

        public void onCancelSelect();
    }

    OnSelect mOnSelect;


    public DialogAddStudent(Context context, String title, String message) {
        this.context = context;
        this.title = title;
        this.message = message;

        makeDialog();
    }

    public DialogAddStudent(Context context, String title, String message, Student student) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.currentStudent = student;

        makeDialog();
    }



    public void setOnSelectListener(OnSelect mListener) {
        mOnSelect = mListener;
    }


    public void makeDialog() {

        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_add_student, null);
        ButterKnife.bind(this, contentView);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setTitle(title)
                .setMessage(message)
                .setView(contentView);


        if(currentStudent!= null){
            updateUI();
        }

        mDialog = mBuilder.create();

    }

    private void updateUI(){
        if (currentStudent != null) {
            editTextName.setText(currentStudent.getName());
            editTextRoll.setText( ""+ currentStudent.getRoll());
            editTextAge.setText( "" +  currentStudent.getAge());
        }
    }

    private void onCancelClick() {
        if (mOnSelect != null) {
            mOnSelect.onCancelSelect();
            mDialog.dismiss();
        }
    }

    private void onOkClick() {
        if (mOnSelect != null) {

            if (validate()) {

                String name = editTextName.getText().toString();
                String roll = editTextRoll.getText().toString();
                String age = editTextAge.getText().toString();

                long rollNum = Long.parseLong(roll);
                int ageInt = Integer.parseInt(age);

                if (currentStudent == null) {
                    currentStudent = new Student(name, rollNum, ageInt);
                }else {
                    currentStudent.setName(name);
                    currentStudent.setAge(ageInt);
                    currentStudent.setRoll(rollNum);
                    currentStudent.setUpdatedAt(System.currentTimeMillis());
                }

                mOnSelect.onOkSelect(currentStudent);
                mDialog.dismiss();
            } else {
                Log.d(TAG, "not valid");
            }

        }
    }


    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    private boolean validate() {

        String name = editTextName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Required");
            return false;
        }
        String roll = editTextRoll.getText().toString();
        if (roll.length() == 0 || !TextUtils.isDigitsOnly(roll)) {
            editTextRoll.setError("Invalid");
            return false;
        }

        String age = editTextAge.getText().toString();
        if (age.length() == 0 || !TextUtils.isDigitsOnly(age)) {
            editTextAge.setError("Invalid");
            return false;
        }

        return true;

    }



    @OnClick({R.id.buttonOk, R.id.buttonCancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonOk:
                onOkClick();
                break;
            case R.id.buttonCancel:
                onCancelClick();
                break;
        }
    }


}
