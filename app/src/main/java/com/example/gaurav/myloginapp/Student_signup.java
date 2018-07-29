package com.example.gaurav.myloginapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Student_signup extends AppCompatActivity
        implements OnItemSelectedListener, View.OnClickListener{

    private Button signup;
    private Button setdob;

    private DatabaseHelper db;

    public Spinner spinner;

    private String branch;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView dob;
    private String dateofbirth;
    private SessionManager sm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity_student);
        db = new DatabaseHelper(this);

        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);

        List<String>categories = new ArrayList<>();
        categories.add("CSE");
        categories.add("ECE");
        categories.add("ME");
        categories.add("CIVIL");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        setdob = (Button) findViewById(R.id.setstudob);
        setdob.setOnClickListener(this);

        init();

    }

    private void init() {
        signup = (Button) findViewById(R.id.stu_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            public boolean auth;
            public boolean exists = false;
            public EditText name;
            public TextView dob;
            public EditText email;
            public EditText pass;


            @Override
            public void onClick(View view) {
                name = (EditText) findViewById(R.id.stu_name);
                dob = (TextView) findViewById(R.id.stu_dob);
                email = (EditText) findViewById(R.id.stu_email);
                pass = (EditText) findViewById(R.id.stu_pass);


                if (name.getText().toString().equals(""))
                    name.setError("Field Required");
                else if (dob.getText().toString().equals(""))
                    dob.setError("Field Required");
                else if (email.getText().toString().equals(""))
                    email.setError("Field Required");
                else if (pass.getText().toString().equals(""))
                    pass.setError("Field Required");
                else {
                    List<Teacher> teacherList = db.getAllTeacher();
                    for (int i = 0; i < teacherList.size(); i++) {
                        if (teacherList.get(i).getEmail().equals(email.getText().toString())) {
                            exists = true;
                        }
                    }
                    if (exists) {
                        email.setError("Already Registered");
                        exists = false;
                    } else {
                        boolean notexists = true;
                        List<Student> studentList = db.getAllStudents();
                        for (int i = 0; i < studentList.size(); i++) {
                            if (studentList.get(i).getEmail().equals(email.getText().toString())) {
                                notexists = false;
                            }
                        }
                        if (notexists) {
                            if (email.getText().toString().contains("@gmail.com")) {
                                boolean id = db.newStudent(name.getText().toString(), branch, dateofbirth,
                                        email.getText().toString(), pass.getText().toString());
                                if (id) {
                                    db.setMarks(String.valueOf(email.getText()),"N/A","N/A","N/A","N/A","N/A");
                                    Toast.makeText(Student_signup.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    sm = new SessionManager(getApplicationContext());
                                    sm.createLoginSession(name.getText().toString(), email.getText().toString(), branch);
                                    Intent intent = new Intent(Student_signup.this, Student_login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else
                                Toast.makeText(Student_signup.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(Student_signup.this, "User already Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        branch = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select a branch", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        dob = (TextView)findViewById(R.id.stu_dob);
        if (view == setdob){
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            dateofbirth = dayOfMonth + "-" +monthOfYear + "-" + year;
                            if (year <= 1998) {
                                dob.setTextColor(0x7f000000);
                                dob.setText(dateofbirth);
                            }
                            else {
                                dob.setTextColor(0x7fff0000);
                                dob.setText("You should be 18+");
                            }
                        }
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();
        }
    }
}
