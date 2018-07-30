package com.example.gaurav.myloginapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student_login extends AppCompatActivity {
    private TextView name;
    private TextView branch;
    private TextView email;
    private Button logout;
    private SessionManager sm;
    private TextView maths;
    private TextView physics;
    private TextView chemistry;
    private TextView fcp;
    private TextView bme;
    private DatabaseHelper db;
    private TextView avg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login);
        MainActivity.main.finish();
        init();
    }

    private void init() {
        sm = new SessionManager(getApplicationContext());
        HashMap<String,String> user = sm.getUserDetails();

        db = new DatabaseHelper(this);

        name = (TextView)findViewById(R.id.studentname);
        branch = (TextView)findViewById(R.id.studentbranch);
        email = (TextView)findViewById(R.id.studentemail);

        logout = (Button)findViewById(R.id.stu_logout);

        //Set Basic Details
        name.setText(user.get(sm.KEY_NAME).toUpperCase());
        branch.setText(user.get(sm.KEY_BRANCH).toUpperCase());
        email.setText(user.get(sm.KEY_EMAIL));

        //Set Marks
        List<Marks> marks = new ArrayList<>();
        marks.addAll(db.getMarks());

        maths = (TextView)findViewById(R.id.sub_1);
        physics = (TextView)findViewById(R.id.sub_2);
        chemistry = (TextView)findViewById(R.id.sub_3);
        fcp = (TextView)findViewById(R.id.sub_4);
        bme = (TextView)findViewById(R.id.sub_5);
        avg = (TextView)findViewById(R.id.avg);

        for (int i = 0; i< marks.size(); i++) {
            if (marks.get(i).getEmail().equals(user.get(sm.KEY_EMAIL))) {

                Marks mark = marks.get(i);
                maths.setText(mark.getMaths());
                physics.setText(mark.getPhysics());
                chemistry.setText(mark.getChemistry());
                fcp.setText(mark.getFcp());
                bme.setText(mark.getBme());
                avg.setText((Integer.parseInt(mark.getMaths() + mark.getPhysics() + mark.getPhysics() + mark.getFcp() + mark.getBme())
                        / 5) + " %");

            }
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sm.logoutUser();
                finish();
            }
        });
    }
}
