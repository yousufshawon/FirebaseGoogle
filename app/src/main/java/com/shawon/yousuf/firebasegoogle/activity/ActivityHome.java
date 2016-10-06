package com.shawon.yousuf.firebasegoogle.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shawon.yousuf.firebasegoogle.R;
import com.shawon.yousuf.firebasegoogle.adapter.StudentListAdapter;
import com.shawon.yousuf.firebasegoogle.dialog.DialogAddStudent;
import com.shawon.yousuf.firebasegoogle.model.Student;
import com.shawon.yousuf.firebasegoogle.util.Constants;
import com.shawon.yousuf.firebasegoogle.util.Log;
import com.shawon.yousuf.firebasegoogle.viewholder.StudentViewHolder;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ActivityHome extends AppCompatActivity {

    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    TextView textView;

    //Firebase reference
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("condition");
    DatabaseReference studentRef = mRootRef.child(Constants.STUDENT_LIST_REF_KEY);

    StudentListAdapter mAdapter;
    private LinearLayoutManager mManager;

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

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(this);
      //  mManager.setReverseLayout(true);
      //  mManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mManager);

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


        // set up Recycler View
        Query studentQuery = getQuery(mRootRef);
        mAdapter = new StudentListAdapter(this, Student.class, R.layout.list_item, StudentViewHolder.class, studentQuery);
        recyclerView.setAdapter(mAdapter);


    }

    private void onAddClick() {
        DialogAddStudent mDialog = new DialogAddStudent(this, "Add Student", "Enter information");
        mDialog.setOnSelectListener(new DialogAddStudent.OnSelect() {
            @Override
            public void onOkSelect(Student student) {
                Log.d(TAG, "Ok Select");
                Log.d(TAG, student.toString());

                addStudent(student);

            }

            @Override
            public void onCancelSelect() {
                Log.d(TAG, "Cancel Select");
            }
        });

        mDialog.show();
    }


    public void addStudent(Student student){

        String studentId = mRootRef.child(Constants.STUDENT_LIST_REF_KEY).push().getKey();
        Log.d(TAG, "student Id: " + studentId);
        Map<String, Object>  studentsValue = student.toMap();

        Map<String, Object> studentUpdates = new HashMap<>();
        studentUpdates.put("/"+ Constants.STUDENT_LIST_REF_KEY +"/" + studentId, studentsValue);

        mRootRef.updateChildren(studentUpdates);

    }



    private Query getQuery(DatabaseReference databaseReference){
        // get 50 item
        Query query = databaseReference.child(Constants.STUDENT_LIST_REF_KEY).limitToFirst(50);
        return query;
    }


    @OnClick(R.id.fab)
    public void onClick() {

        onAddClick();
    }
}
