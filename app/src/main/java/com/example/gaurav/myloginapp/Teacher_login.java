package com.example.gaurav.myloginapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Teacher_login extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<Student> dataList = new ArrayList<>();
    private MyAdapter adapter;
    private DatabaseHelper db;
    private TextView email;
    private TextView uname;
    private TextView branch;
    private SessionManager sm;
    private Button logout;
    private Button refresh;
    private List<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_login);

        adapter = new MyAdapter(dataList, this);

        sm = new SessionManager(getApplicationContext());
        HashMap<String, String> User = sm.getUserDetails();

        uname = (TextView)findViewById(R.id.teachername);
        branch = (TextView)findViewById(R.id.teacherbranch);
        email = (TextView)findViewById(R.id.teacheremail);

        logout = (Button)findViewById(R.id.teach_logout);
        refresh = (Button)findViewById(R.id.refresh);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        uname.setText(User.get(sm.KEY_NAME).toUpperCase());
        branch.setText(User.get(sm.KEY_BRANCH).toUpperCase());
        email.setText(User.get(sm.KEY_EMAIL));

        init();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sm.logoutUser();
                finish();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            public List<Student> list;

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Teacher_login.this, Teacher_login.class));
                finish();
            }
        });

    }

    private void init() {
        HashMap<String, String> User = sm.getUserDetails();

        List<Student> All = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        db = new DatabaseHelper(this);

        All.addAll(db.getAllStudents());
        for (int i=0;i<All.size();i++){
            if (User.get(sm.KEY_BRANCH).equals(All.get(i).getBranch()))
                students.add(All.get(i));
    }
        dataList.addAll(students);
    }
}
