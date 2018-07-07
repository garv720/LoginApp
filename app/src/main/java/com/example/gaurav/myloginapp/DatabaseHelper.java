package com.example.gaurav.myloginapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, Myvars.db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Myvars.CREATE_STU_TABLE);
        db.execSQL(Myvars.CREATE_TEACH_TABLE);
        db.execSQL(Myvars.CREATE_MARKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+Myvars.stu_table);
        db.execSQL("DROP TABLE IF EXISTS "+Myvars.teach_table);
        db.execSQL("DROP TABLE IF EXISTS "+Myvars.marks_table);

        onCreate(db);
    }

    public boolean newTeacher(String uname, String department, String dob, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Myvars.teach_col_2, uname);
        values.put(Myvars.teach_col_3, department);
        values.put(Myvars.stu_col_4, dob);
        values.put(Myvars.stu_col_5, email);
        values.put(Myvars.stu_col_6, password);

        long id = db.insert(Myvars.teach_table,null, values);

        db.close();

        if(id == -1)
            return false;
        else
            return true;
    }

    public boolean newStudent(String uname, String branch, String dob, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Myvars.stu_col_2, uname);
        values.put(Myvars.stu_col_3, branch);
        values.put(Myvars.stu_col_4, dob);
        values.put(Myvars.stu_col_5, email);
        values.put(Myvars.stu_col_6, password);

        long id = db.insert(Myvars.stu_table,null, values);

        db.close();
        if(id == -1)
            return false;
        else
            return true;
    }


    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        String select_query = "SELECT * FROM "+Myvars.stu_table+" ORDER BY "+Myvars.stu_col_2+" ASC";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        if(cursor.moveToFirst()){
            do{
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex(Myvars.stu_col_1)));
                student.setName(cursor.getString(cursor.getColumnIndex(Myvars.stu_col_2)));
                student.setBranch(cursor.getString(cursor.getColumnIndex(Myvars.stu_col_3)));
                student.setDob(cursor.getString(cursor.getColumnIndex(Myvars.stu_col_4)));
                student.setEmail(cursor.getString(cursor.getColumnIndex(Myvars.stu_col_5)));
                student.setPassword(cursor.getString(cursor.getColumnIndex(Myvars.stu_col_6)));
                student.setMarks("N/A");

                students.add(student);
            }while (cursor.moveToNext());
        }
        db.close();
        return students;
    }

    public List<Teacher> getAllTeacher(){
        List<Teacher> teachers = new ArrayList<>();
        String select_query = "SELECT * FROM "+Myvars.teach_table;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        if(cursor.moveToFirst()){
            do{
                Teacher teacher = new Teacher();
                teacher.setId(cursor.getInt(cursor.getColumnIndex(Myvars.teach_col_1)));
                teacher.setName(cursor.getString(cursor.getColumnIndex(Myvars.teach_col_2)));
                teacher.setDepartment(cursor.getString(cursor.getColumnIndex(Myvars.teach_col_3)));
                teacher.setDob(cursor.getString(cursor.getColumnIndex(Myvars.teach_col_4)));
                teacher.setEmail(cursor.getString(cursor.getColumnIndex(Myvars.teach_col_5)));
                teacher.setPassword(cursor.getString(cursor.getColumnIndex(Myvars.teach_col_6)));

                teachers.add(teacher);
            }while (cursor.moveToNext());
        }
        db.close();
        return teachers;
    }
    public List<Marks> getMarks(){
        List<Marks> marksList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+Myvars.marks_table, null);

        if (cursor.moveToFirst()){
            do {
                Marks marks = new Marks();
                marks.setEmail(cursor.getString(cursor.getColumnIndex(Myvars.marks_col_2)));
                marks.setMaths(cursor.getString(cursor.getColumnIndex(Myvars.marks_col_3)));
                marks.setPhysics(cursor.getString(cursor.getColumnIndex(Myvars.marks_col_4)));
                marks.setChemistry(cursor.getString(cursor.getColumnIndex(Myvars.marks_col_5)));
                marks.setFcp(cursor.getString(cursor.getColumnIndex(Myvars.marks_col_6)));
                marks.setBme(cursor.getString(cursor.getColumnIndex(Myvars.marks_col_7)));
                marksList.add(marks);

            } while (cursor.moveToNext());
        }
        return marksList;
    }


    public Marks getOneMark(String email){
        Marks marks = new Marks();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+Myvars.marks_table+" WHERE "+Myvars.marks_col_2+" = "+email+";", null);

        marks.setMaths(cursor.getString(cursor.getColumnIndex(Myvars.marks_col_3)));
        marks.setPhysics(cursor.getString(cursor.getColumnIndex(Myvars.marks_col_4)));
        marks.setChemistry(cursor.getString(cursor.getColumnIndex(Myvars.marks_col_5)));
        marks.setFcp(cursor.getString(cursor.getColumnIndex(Myvars.marks_col_6)));
        marks.setBme(cursor.getString(cursor.getColumnIndex(Myvars.marks_col_7)));
        return marks;
    }


    public boolean setMarks(String email, String maths, String physics, String chemistry, String fcp, String bme) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Myvars.marks_col_2, email);
        values.put(Myvars.marks_col_3, maths);
        values.put(Myvars.marks_col_4, physics);
        values.put(Myvars.marks_col_5, chemistry);
        values.put(Myvars.marks_col_6, fcp);
        values.put(Myvars.marks_col_7, bme);

        long id = db.insert(Myvars.marks_table, null, values);

        db.close();
        return id != -1;
    }

    public boolean updateMarks(String email, String maths, String physics, String chemistry, String fcp, String bme) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Myvars.marks_col_3, maths);
        values.put(Myvars.marks_col_4, physics);
        values.put(Myvars.marks_col_5, chemistry);
        values.put(Myvars.marks_col_6, fcp);
        values.put(Myvars.marks_col_7, bme);

        db.update(Myvars.marks_table, values, "email = ?", new String[]{email});

        return true;
    }
}
