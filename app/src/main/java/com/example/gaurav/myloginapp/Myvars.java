package com.example.gaurav.myloginapp;

public class Myvars {
    public static final String db_name = "Mydb";
    public static final String stu_table = "stu_table";
    public static final String teach_table = "teach_table";
    public static final String marks_table = "marks_table";

    public static final String stu_col_1 = "id";
    public static final String stu_col_2 = "name";
    public static final String stu_col_3 = "branch";
    public static final String stu_col_4 = "dob";
    public static final String stu_col_5 = "email";
    public static final String stu_col_6 = "password";

    public static final String teach_col_1 = "id";
    public static final String teach_col_2 = "name";
    public static final String teach_col_3 = "department";
    public static final String teach_col_4 = "dob";
    public static final String teach_col_5 = "email";
    public static final String teach_col_6 = "password";

    public static final String marks_col_1 = "id";
    public static final String marks_col_2 = "email";
    public static final String marks_col_3 = "maths";
    public static final String marks_col_4 = "physics";
    public static final String marks_col_5 = "chemistry";
    public static final String marks_col_6 = "fcp";
    public static final String marks_col_7 = "bme";


    public static final String CREATE_STU_TABLE = "CREATE TABLE "+stu_table+"("
            +stu_col_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+stu_col_2+" TEXT,"
            +stu_col_3+" TEXT,"+stu_col_4+" TEXT,"+stu_col_5+" TEXT,"+stu_col_6+" TEXT)";

    public static final String CREATE_TEACH_TABLE = "CREATE TABLE "+teach_table+"("
            +teach_col_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+teach_col_2+" TEXT,"
            +teach_col_3+" TEXT,"+teach_col_4+" TEXT,"+teach_col_5+" TEXT,"+teach_col_6+" TEXT)";

    public static final String CREATE_MARKS_TABLE = "CREATE TABLE "+marks_table+"("
            +marks_col_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+marks_col_2+" TEXT,"
            +marks_col_3+" TEXT," +marks_col_4+" TEXT,"+marks_col_5+" TEXT,"+marks_col_6+
            " TEXT,"+marks_col_7+" TEXT)";

}
