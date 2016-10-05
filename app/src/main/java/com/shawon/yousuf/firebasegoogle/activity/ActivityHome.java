package com.shawon.yousuf.firebasegoogle.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shawon.yousuf.firebasegoogle.R;
import com.shawon.yousuf.firebasegoogle.dialog.DialogAddStudent;
import com.shawon.yousuf.firebasegoogle.model.Student;
import com.shawon.yousuf.firebasegoogle.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityHome extends AppCompatActivity {

    @Bind(R.id.fab)
    FloatingActionButton fab;



    TextView textView;

    //Firebase reference
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("condition");

    String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        textView = (TextView) findViewById(R.id.textVew);

    }


    @Override
    protected void onStart() {
        super.onStart();

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                textView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void onAddClick(){
        DialogAddStudent mDialog = new DialogAddStudent(this, "Add Student", "Enter information");
        mDialog.setOnSelectListener(new DialogAddStudent.OnSelect() {
            @Override
            public void onOkSelect(Student student) {
                Log.d(TAG, "Ok Select");
                Log.d(TAG, student.toString());
            }

            @Override
            public void onCancelSelect() {
                Log.d(TAG, "Cancel Select");
            }
        });

        mDialog.show();
    }


    @OnClick(R.id.fab)
    public void onClick() {

        onAddClick();
    }
}
