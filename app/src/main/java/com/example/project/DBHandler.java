package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.*;

import java.io.Serializable;
import java.util.Observer;

public class DBHandler extends SQLiteOpenHelper{

    //information about the users table
    private static final String USER_TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PASSWORD = "password";

    //information about the courses table
    private static final String COURSE_TABLE_NAME = "courses";
    private static final String COLUMN_COURSE_CODE = "courseCode";
    private static final String COLUMN_COURSE_NAME = "courseName";
    private static final String COLUMN_COURSE_ONE = "course1";
    private static final String COLUMN_COURSE_TWO = "course2";
    private static final String COLUMN_COURSE_THREE = "course3";
    private static final String COLUMN_COURSE_FOUR = "course4";
    private static final String COLUMN_COURSE_FIVE = "course5";
    //additional information about the course table
    private static final String COLUMN_COURSE_INSTRUCTOR = "courseInstructor";
    private static final String COLUMN_COURSE_FIRSTDAY = "courseDay1";
    private static final String COLUMN_COURSE_SECONDDAY = "courseDay2";
    private static final String COLUMN_COURSE_FIRSTTIME = "courseTime1";
    private static final String COLUMN_COURSE_SECONDTIME = "courseTime2";
    private static final String COLUMN_COURSE_DESCRIPTION = "courseDescription";
    private static final String COLUMN_COURSE_CAPACITY = "courseCapacity";
    private static final String COLUMN_COURSE_CURRENT_CAP = "currentCap";

    private static final String DATABASE_NAME = "university.db";
    private static final int DATABASE_VERSION = 5;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // Create a the user table user (id, password) and id is a primary key

        // Create the user table
        String create_user_table_cmd = "CREATE TABLE " + USER_TABLE_NAME +
                "(" + COLUMN_ID + " varchar(20) PRIMARY KEY, " +
                COLUMN_PASSWORD + " varchar(20), "+
                COLUMN_COURSE_ONE + " varchar(100), " +
                COLUMN_COURSE_TWO + " varchar(100), "+
                COLUMN_COURSE_THREE + " varchar(100), "+
                COLUMN_COURSE_FOUR + " varchar(100), "+
                COLUMN_COURSE_FIVE + " varchar(100))";

        //Columns Username, Password, Course[1-5]
        //Course columns store Course Codes
        //When we need to view our courses, we do a lookup of the codes in the user's row

        sqLiteDatabase.execSQL(create_user_table_cmd);

        //Create the courses table
        String create_course_table_cmd = "CREATE TABLE " + COURSE_TABLE_NAME +
                "(" + COLUMN_COURSE_CODE + " varchar(20) PRIMARY KEY, " +
                COLUMN_COURSE_NAME + " varchar(20), " +
                COLUMN_COURSE_INSTRUCTOR + " varchar(20), "+
                COLUMN_COURSE_FIRSTDAY + " varchar(100), " +
                COLUMN_COURSE_SECONDDAY + " varchar(100), "+
                COLUMN_COURSE_FIRSTTIME + " varchar(100), "+
                COLUMN_COURSE_SECONDTIME + " varchar(100), "+
                COLUMN_COURSE_DESCRIPTION + " varchar(100), " +
                COLUMN_COURSE_CAPACITY + "int, " +
                COLUMN_COURSE_CURRENT_CAP + " int)"
                ;

        sqLiteDatabase.execSQL(create_course_table_cmd);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //when updating the database we drop the old version of the tables  and we create a new version by calling onCreate(sqLiteDatabase).
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE_NAME);

        onCreate(sqLiteDatabase);

    }

    // return "cursor" all users from the database
    public Cursor getUserData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE_NAME;
        return sqLiteDatabase.rawQuery(query, null);
    }

    // return "cursor" all courses from the database
    public Cursor getCourseData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + COURSE_TABLE_NAME;
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getSpecificData(String CCode){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COLUMN_COURSE_CODE + " =\"" + CCode + "\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        return cursor;
    }

    public Cursor getSpecificUser(String username){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + COLUMN_ID + " =\"" + username + "\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        return cursor;
    }


    //add a new user in the table users in the data base
    public void addUsers(User user) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID,user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());

        sqLiteDatabase.insert(USER_TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public void editCourse(String oldCID, String newCID, String newCName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_COURSE_CODE, newCID);
        values.put(COLUMN_COURSE_NAME, newCName);

        sqLiteDatabase.update(COURSE_TABLE_NAME, values, COLUMN_COURSE_CODE + "=" + oldCID, null);
        sqLiteDatabase.close();

    }


    public void updateCourse(String Ccode, String CCode, String CName, String PName, String day1, String day2, String desc, int Capacity, String time1, String time2, int ccap){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //Ccode == current code, CCode == desired code
        values.put(COLUMN_COURSE_NAME, CName);
        values.put(COLUMN_COURSE_CODE, CCode);
        values.put(COLUMN_COURSE_INSTRUCTOR, PName);
        values.put(COLUMN_COURSE_DESCRIPTION, desc);
        values.put(COLUMN_COURSE_CAPACITY, Capacity);
        values.put(COLUMN_COURSE_FIRSTDAY, day1);
        values.put(COLUMN_COURSE_SECONDDAY, day2);
        values.put(COLUMN_COURSE_FIRSTTIME, time1);
        values.put(COLUMN_COURSE_SECONDTIME, time2);
        values.put(COLUMN_COURSE_CURRENT_CAP, ccap);

        sqLiteDatabase.update(COURSE_TABLE_NAME, values, COLUMN_COURSE_CODE + "=" + Ccode, null);
        sqLiteDatabase.close();
    }


    //returns true if it enrolled
    //false otherwise
    public boolean enroll(String code, String username, String column){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + COLUMN_ID + " =\"" + username + "\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        //Check times my course is offered vs Times all my current courses are taking


        //this is where the value gets staged to get updated
        values.put(column, code);
        //this is where the update occurs, all checks must happen before this
        sqLiteDatabase.update(USER_TABLE_NAME, values, COLUMN_ID + "= \"" + username + "\"", null);
        sqLiteDatabase.close();



        return true;
    }












    //add a new course in the table courses in the data base
    public void addCourse(Course course) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_COURSE_CODE, course.getCode());
        values.put(COLUMN_COURSE_NAME, course.getName());

        sqLiteDatabase.insert(COURSE_TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    //given a userid find it in the database if not there return null
    public User findUser(String userID){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + COLUMN_ID + " =\"" + userID + "\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        //Create a user and put the result on it
        User user;
        if (cursor.moveToFirst()){
            user = new User(cursor.getString(0),cursor.getString(1));
            cursor.close();
        }
        else user = null;

        sqLiteDatabase.close();
        return user ;
    }

    public Cursor findCourse(String courseCode){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COLUMN_COURSE_CODE + " LIKE \"" + courseCode + "%\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        return cursor;
    }

    public Cursor findCoursebyName(String courseName){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COLUMN_COURSE_NAME + " LIKE \"" + courseName + "%\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        return cursor;
    }


    public Cursor findCourse(String courseName, String courseCode){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COLUMN_COURSE_NAME + " LIKE \"" + courseName + "%\" AND " + COLUMN_COURSE_CODE + " LIKE \"" + courseCode + "%\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        return cursor;
    }

    //given a userid find it in the database and delete it
    public boolean deleteUser(String userID){

        boolean result = false;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + COLUMN_ID + " =\"" + userID + "\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            String idStr = cursor.getString(0);
            sqLiteDatabase.delete(USER_TABLE_NAME, COLUMN_ID + " = \"" + idStr + "\"",  null);
            result = true;
            cursor.close();
        }
        sqLiteDatabase.close();
        return result;
    }

    //given a course code  find it in the database and delete it
    public boolean deleteCourse(String courseCode){

        boolean result = false;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COLUMN_COURSE_CODE + " =\"" + courseCode + "\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            String courseStr = cursor.getString(0);
            sqLiteDatabase.delete(COURSE_TABLE_NAME, COLUMN_COURSE_CODE + " = \"" + courseStr + "\"",  null);
            result = true;
            cursor.close();
        }
        sqLiteDatabase.close();
        return result;
    }

    public void deleteClass(String username,  String column){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(column, "");
        sqLiteDatabase.update(USER_TABLE_NAME, values, COLUMN_ID + "= \"" + username + "\"", null);
        sqLiteDatabase.close();


    }

    public boolean isProf(String CCode, String provided){
        boolean result = false;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COLUMN_COURSE_CODE + " =\"" + CCode + "\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            String courseStr = cursor.getString(2);
            if(courseStr == null || courseStr.equals("")){
                result = true;
                cursor.close();
            }else if(courseStr.equals(provided)){
                result = true;
                cursor.close();
            }
        }
        sqLiteDatabase.close();
        return result;

    }


}








