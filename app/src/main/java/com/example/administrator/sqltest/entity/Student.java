package com.example.administrator.sqltest.entity;

/**
 * Created by Administrator on 2016/12/26 0026.
 * 学生类
 */
public class Student {
    private int _id;
    private String name;
    private String grade;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
