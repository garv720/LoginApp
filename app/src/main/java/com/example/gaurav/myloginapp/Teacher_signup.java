package com.example.gaurav.myloginapp;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Teacher_signup extends AppCompatActivity
        implements OnItemSelectedListener, View.OnClickListener{
    private Button signup;
    public DatabaseHelper db;

    private Spinner spinner;
    private String department;
    private Button setdob;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView dob;
    private String dateofbirth;
    private SessionManager sm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity_teacher);
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

        setdob = (Button) findViewById(R.id.setteachdob);
        setdob.setOnClickListener(this);

        init();
    }

    private void init() {
        signup = (Button)findViewById(R.id.teach_signup);
        sm = new SessionManager(getApplicationContext());
        signup.setOnClickListener(new View.OnClickListener() {
            public boolean auth;
            public boolean exists = false;
            public EditText name;
            public EditText email;
            public EditText pass;

            @Override
            public void onClick(View view) {
                name = (EditText) findViewById(R.id.teach_name);
                email = (EditText) findViewById(R.id.teach_email);
                pass = (EditText) findViewById(R.id.teach_pass);
                dob = (TextView)findViewById(R.id.teach_dob);

                if (name.getText().toString().equals(""))
                    name.setError("Field Required");
                else if (dob.getText().toString().equals(""))
                    dob.setError("Field Required");
                else if (email.getText().toString().equals(""))
                    email.setError("Field Required");
                else if (pass.getText().toString().equals(""))
                    pass.setError("Field Required");
                else {
                    List<Student> studentList = db.getAllStudents();
                    for (int i = 0; i < studentList.size(); i++) {
                        if (studentList.get(i).getEmail().equals(email.getText().toString())) {
                            exists = true;
                        }
                    }
                    if (exists) {
                        email.setError("Already Registered");
                        exists = false;
                    } else {
                        boolean notexists = true;
                        List<Teacher> teacherList = db.getAllTeacher();
                        for (int i = 0; i < teacherList.size(); i++) {
                            if (teacherList.get(i).getEmail().equals(email.getText().toString())) {
                                notexists = false;
                            }
                        }
                        if (notexists) {
                            if (email.getText().toString().contains("@gmail.com")) {
                                boolean id = db.newStudent(name.getText().toString(), department, dateofbirth,
                                        email.getText().toString(), pass.getText().toString());
                                if (id) {
                                    Toast.makeText(Teacher_signup.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    sm = new SessionManager(getApplicationContext());
                                    sm.createLoginSession(name.getText().toString(), email.getText().toString(), department);
                                    Intent intent = new Intent(Teacher_signup.this, Student_login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else
                                Toast.makeText(Teacher_signup.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(Teacher_signup.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        department = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select a branch", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        dob = (TextView)findViewById(R.id.teach_dob);
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
                            dateofbirth = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            if (year <= 1990 ) {
                                dob.setTextColor(0x7f000000);
                                dob.setText(dateofbirth);
                            }
                            else {
                                dob.setTextColor(0x7fff0000);
                                dob.setText("You should be 28+");
                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}
