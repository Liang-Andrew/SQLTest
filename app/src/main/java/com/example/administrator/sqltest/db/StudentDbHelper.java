package com.example.administrator.sqltest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.sqltest.entity.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26 0026.
 * 数据库类
 */
public class StudentDbHelper extends SQLiteOpenHelper {

    //数据库名称
    private static final String DATABASE_NAME = "Student.db";
    //数据库表名
    private static final String TABLE_NAME = "student_grade";
    //数据库版本
    private static final int VERSION = 1;

    //用户名
    private static final String KEY_NAME = "name";
    //年级
    private static final String KEY_GRADE = "grade";
    //创建表
    private static final String CREATETABLE = "create table " + TABLE_NAME +
            "(_id integer primary key autoincrement," +
            KEY_NAME + " text not null," +
            KEY_GRADE + " text not null)";

    /**
     * 构造方法，一般是传递一个要创建的数据库名称那么参数
     */
    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * 创建数据库时调用
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE);
    }

    /**
     * 添加
     */
    public void addStudent(Student student) {
        //读取数据库
        SQLiteDatabase db = getReadableDatabase();
        //存储基本类型的数据，像string，int之类的，不能存储对象
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_GRADE, student.getGrade());
        //添加方法
        db.insert(TABLE_NAME, null, values);
        //关闭数据库
        db.close();
    }

    /**
     * 查询所有学生信息
     */
    public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "select * from " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        //Cursor 是每行的集合。
        //使用 moveToFirst() 定位第一行。
        //必须知道每一列的名称。
        //必须知道每一列的数据类型。
        //Cursor 是一个随机的数据源。
        //所有的数据都是通过下标取得。
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.set_id(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                student.setGrade(cursor.getString(2));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        db.close();
        return studentList;
    }

    /**
     * 更新数据库
     */
    public void updateStudent(Student student) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_GRADE, student.getGrade());
        db.update(TABLE_NAME, values, "_id=?", new String[]{String.valueOf(student.get_id())});
        db.close();
    }

    /**
     * 删除
     */
    public void deleteStudent(Student student) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_NAME, "_id=?", new String[]{String.valueOf(student.get_id())});
        db.close();
    }

    public List<Student> queryStudent(String name) {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{"name"},
                "name like ?",
                new String[]{"%" + name + "%"},
                null, null, null);
        while (cursor.moveToNext()) {
            Student student = new Student();
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            // 让集合中的数据不重复;
            if (!studentList.contains(student)) {
                studentList.add(student);
            }
        }
        cursor.close();
        return studentList;
    }

    /**
     * 数据库版本更新时调用
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
