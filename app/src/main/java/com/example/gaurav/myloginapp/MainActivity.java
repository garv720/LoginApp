package com.example.gaurav.myloginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Student> students = new ArrayList<>();
    public List<Teacher> teachers = new ArrayList<>();

    private EditText email;
    private EditText pass;
    private Button login_btn;
    private Button teacher_btn;
    private Button student_btn;
    private DatabaseHelper db;

    private boolean empty;
    private SessionManager sm;
    private HashMap<String, String> User;
    private List<Student> studentList;
    private List<Teacher> teacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sm = new SessionManager(getApplicationContext());
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if(sm.isLoggedIn()){
            User = sm.getUserDetails();
            db = new DatabaseHelper(this);
            studentList = db.getAllStudents();
            teacherList = db.getAllTeacher();
            for (int i=0;i<studentList.size();i++) {
                if (User.get(sm.KEY_EMAIL).equals(studentList.get(i).getEmail())) {
                    startActivity(new Intent(MainActivity.this, Student_login.class));
                    finish();
                }
            }
            for (int j=0;j<teacherList.size();j++){
                if (User.get(sm.KEY_EMAIL).equals(teacherList.get(j).getEmail())) {
                    startActivity(new Intent(MainActivity.this, Teacher_login.class));
                    finish();
                }
            }
        }
        init();
    }

    private void init() {

        login_btn = (Button)findViewById(R.id.login);
        teacher_btn = (Button)findViewById(R.id.teacher_signup);
        student_btn = (Button)findViewById(R.id.student_signup);

        db = new DatabaseHelper(this);

        login_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                email = (EditText)findViewById(R.id.login_email);
                pass = (EditText)findViewById(R.id.pass);

                if (email.getText().toString().equals(""))
                    email.setError("Field Required");
                else if (pass.getText().toString().equals(""))
                    pass.setError("Field Required");
                else {
                        checkinStudents();
                }
                }
        });

        teacher_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Teacher_signup.class);
                startActivity(intent);
                finish();
            }
        });

        student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Student_signup.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkinStudents() {
        int j =0;
        boolean check= false;
        students =  db.getAllStudents();
        if (students.size()>0){
            empty = false;
            for (int i=0;i<students.size();i++) {
                Student student = students.get(i);
                j = i;
                if (student.getEmail().equals(email.getText().toString().trim())) {
                    check = true;
                    break;
                }
            }if (check == true) {
                if (students.get(j).getPassword().trim().equals(pass.getText().toString().trim())) {
                    sm = new SessionManager(getApplicationContext());
                    sm.createLoginSession(students.get(j).getName(), students.get(j).getEmail(), students.get(j).getBranch());
                    Intent intent = new Intent(MainActivity.this, Student_login.class);
                    startActivity(intent);
                    finish();
                }
            }
                else
                    checkinTeachers();
        }
        else{
            empty = true;
            checkinTeachers();
        }
    }

    public void checkinTeachers() {
        int j = 0;
        boolean check =false;
        teachers = db.getAllTeacher();
        if (teachers.size() == 0) {
            if (empty){
                Toast.makeText(this, "No record found", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(MainActivity.this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < teachers.size(); i++) {
                Teacher teacher = teachers.get(i);
                j = i;
                if (teacher.getEmail().trim().equals(email.getText().toString().trim())) {
                    check = true;
                    break;
                }
                else
                    Toast.makeText(MainActivity.this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
            }
            if (check == true) {
                if (teachers.get(j).getPassword().trim().equals(pass.getText().toString().trim())) {
                    sm = new SessionManager(getApplicationContext());
                    sm.createLoginSession(teachers.get(j).getName(), teachers.get(j).getEmail(), teachers.get(j).getDepartment());
                    Intent intent = new Intent(MainActivity.this, Teacher_login.class);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(MainActivity.this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
            }
        }
        }
}
