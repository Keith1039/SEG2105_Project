package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler  extends SQLiteOpenHelper {

    //infomration about the users table
    private static final String USER_TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PASSWORD = "password";

    //information about the courses table
    private static final String COURSE_TABLE_NAME = "courses";
    private static final String COLUMN_COURSE_CODE = "courseCode";
    private static final String COLUMN_COURSE_NAME = "courseName";

    private static final String DATABASE_NAME = "university.db";
    private static final int DATABASE_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // Create a the user table user (id, password) and id is a primary key

        // Create the user table
        String create_user_table_cmd = "CREATE TABLE " + USER_TABLE_NAME +
                "(" + COLUMN_ID + " varchar(20) PRIMARY KEY, " +
                COLUMN_PASSWORD + " varchar(20))";

        sqLiteDatabase.execSQL(create_user_table_cmd);

        //Create the courses table
        String create_course_table_cmd = "CREATE TABLE " + COURSE_TABLE_NAME +
                "(" + COLUMN_COURSE_CODE + " varchar(20) PRIMARY KEY, " +
                COLUMN_COURSE_NAME + " varchar(20))";

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

    //add a new user in the table users in the data base
    public void addUsers(User user) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID,user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());

        sqLiteDatabase.insert(USER_TABLE_NAME, null, values);
        sqLiteDatabase.close();
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

        String query = "SELECT "+ COLUMN_ID + " FROM " + USER_TABLE_NAME + " WHERE " + COLUMN_ID + "= \"" + userID + "\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        //Create a user and put the result on it
        User user = new User();
        if (cursor.moveToFirst()){
            user = new User(cursor.getString(0),cursor.getString(1));
            cursor.close();
        }
        else user = null;

        sqLiteDatabase.close();
        return user ;
    }

    //given a course code find it in the database if not there return null
    public Course findCourse(String courseCode){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COLUMN_COURSE_CODE + "= \"" + courseCode + "\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        //Create a course and put the result on it
        Course course;
        if (cursor.moveToFirst()){
            course= new Course(cursor.getString(0),cursor.getString(1));
            cursor.close();
        }
        else course = null;

        sqLiteDatabase.close();
        return course ;
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


}








