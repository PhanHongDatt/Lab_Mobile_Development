package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.sqlite.Student;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentManager.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CLASS = "studentclass";
    public static final String COLUMN_ACADEMICYEAR ="academic";
    public static final String COLUMN_FACTLY= "factly";
    public static final String COLUMN_MAJOR = "major";
    public static final String COLUMN_DATEOFBIRTH = "dateofBirth";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DATEOFBIRTH + " TEXT,"
                + COLUMN_CLASS + " TEXT,"
                + COLUMN_ACADEMICYEAR + " TEXT,"
                + COLUMN_FACTLY + " TEXT,"
                + COLUMN_MAJOR + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());


        values.put(COLUMN_CLASS, student.getStudentClass());
        values.put(COLUMN_ACADEMICYEAR, student.getAcademicYear());
        values.put(COLUMN_FACTLY, student.getFactly());
        values.put(COLUMN_MAJOR, student.getMajor());
        values.put(COLUMN_DATEOFBIRTH, student.getDateofBirth());
        db.insert(TABLE_STUDENTS, null, values);
        db.close();
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();

                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                student.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));

                student.setDateofBirth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATEOFBIRTH)));
                student.setStudentClass(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS)));
                student.setAcademicYear(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACADEMICYEAR)));
                student.setFactly(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FACTLY)));
                student.setMajor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAJOR)));

                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());

        values.put(COLUMN_DATEOFBIRTH, student.getDateofBirth());
        values.put(COLUMN_CLASS, student.getStudentClass());
        values.put(COLUMN_ACADEMICYEAR, student.getAcademicYear());
        values.put(COLUMN_FACTLY, student.getFactly());
        values.put(COLUMN_MAJOR, student.getMajor());

        return db.update(TABLE_STUDENTS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(student.getId())});

    }

    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
        db.close();
    }

    public Student getStudentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STUDENTS, new String[]{
                        COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_DATEOFBIRTH,
                        COLUMN_CLASS,
                        COLUMN_ACADEMICYEAR,
                        COLUMN_FACTLY,
                        COLUMN_MAJOR
                },
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        Student student = null;
        if (cursor != null && cursor.moveToFirst()) {
            student = new Student();

            student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            student.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            student.setDateofBirth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATEOFBIRTH)));
            student.setStudentClass(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS)));
            student.setAcademicYear(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACADEMICYEAR)));
            student.setFactly(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FACTLY)));
            student.setMajor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAJOR)));

            cursor.close();
        }
        db.close();
        return student;
    }
}