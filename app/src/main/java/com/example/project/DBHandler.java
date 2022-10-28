package com.example.project;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "UsersDB.db";
    private static final String TABLE_USERS = "Users";
    private static final String COLUMN_USERNAME = "Username";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_ROLE ="Role";



    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +"(" + COLUMN_USERNAME +
                " INTEGER PRIMARY KEY," + COLUMN_PASSWORD + " TEXT," + COLUMN_ROLE +" DOUBLE"+")";
        db.execSQL(CREATE_USERS_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_ROLE, "Admin"); //change later
        db.insert(TABLE_USERS, null,values);
        close();
    }
    public User findUser(String Username,String Password){
        //validates if user exists and confirms their role
        SQLiteDatabase db = this.getWritableDatabase();


        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = \"" +Username+"\"";
        Cursor cursor = db.rawQuery(query,null);
        User user = new User();

        if(cursor.moveToFirst()){
            user.setUsername(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            if(cursor.getString(2) == "Student"){
                user = (Student) user;
            }
            else{
                user = (Instructor) user;
            }
            cursor.close();

        }
        else{
            user = null;
        }
        return(user);
    }

    public boolean deleteProduct(String Username) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = \"" + Username + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String idStr = cursor.getString(0);
            db.delete(TABLE_USERS, COLUMN_USERNAME +"=" + idStr, null);
            cursor.close();
            result = true;
        }

        return (result);
    }
}
