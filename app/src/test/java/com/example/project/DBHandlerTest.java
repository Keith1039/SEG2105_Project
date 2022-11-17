package com.example.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import org.junit.Test;


public class DBHandlerTest extends AppCompatActivity {


    DBHandler dbHandler = new DBHandler(this );



    //this function will test the findCourse() function. In this test the function should return null because the course given is not in the database
    @Test
    public void testFindCourseNull() {
        try

        {
            Course actual = dbHandler.findCourse("NOTExist");
            assertNull(actual);

        }catch (Exception e)
        {
            System.out.println(e.toString());
        }


    }

    //this function will test the findCourse() function. In this test the function should return a course because the course given is  in the database
    @Test
    public void testFindCourseSuccess() {

        //find the course with the findCourse() function
        Course actual = dbHandler.findCourse("SEG2105");

        //find the course with a direct query in the database
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();

        String query = "SELECT * FROM courses WHERE courseCode = \" SEG2105\"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        //Create a course and put the result on it
        Course course;
        if (cursor.moveToFirst()){
            course= new Course(cursor.getString(0),cursor.getString(1));
            cursor.close();
        }
        else course = null;
        sqLiteDatabase.close();

        //compare the two courseCode found
        assert course != null;
        assertEquals(course.getCode(),actual.getCode());

    }

    @Test
    public void testDeleteUser() {
        boolean actual = dbHandler.deleteUser("falseUser");
        assertFalse(actual);
    }

    //this function tests if a course is not on the database the deleteCourse() function should return false
    @Test
    public void testDeleteCourseFalse() {
        boolean actual = dbHandler.deleteCourse("MAT2577");
        assertFalse(actual);
    }

    //this function tests if a course is on the database the deleteCourse() function should return true
    @Test
    public void testDeleteCourseTrue() throws NullPointerException {
        Boolean actual;
        actual = dbHandler.deleteCourse("MAT2777");
        System.out.println(actual);
        assertTrue(actual);
    }


}