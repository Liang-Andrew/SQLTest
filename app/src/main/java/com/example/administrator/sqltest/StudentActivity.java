package com.example.administrator.sqltest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.sqltest.db.StudentDbHelper;
import com.example.administrator.sqltest.entity.Student;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName, etGrade;
    private ImageView imageView;
    private Button btnChange, btnDelete, btnAdd;
    private int id;
    private StudentDbHelper dbHelper;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        etName = (EditText) findViewById(R.id.student_name);
        etGrade = (EditText) findViewById(R.id.student_grade);
        btnChange = (Button) findViewById(R.id.btn_change);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnAdd = (Button) findViewById(R.id.btn_add_student);
        imageView = (ImageView) findViewById(R.id.student_image);
        //添加监听事件
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnChange.setOnClickListener(this);

        dbHelper = new StudentDbHelper(this);
        //获取传递过来的数据
        intent = getIntent();

        String type = intent.getStringExtra("type");
        switch (type) {
            case "Add"://增加
                btnChange.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                btnAdd.setVisibility(View.VISIBLE);
                break;
            case "Look"://删除和修改
                btnAdd.setVisibility(View.GONE);
                btnDelete.setVisibility(View.VISIBLE);
                btnChange.setVisibility(View.VISIBLE);
                id = intent.getIntExtra("_id", 0);
                if (id % 2 == 0) {
                    imageView.setImageResource(R.mipmap.boy3);
                } else {
                    imageView.setImageResource(R.mipmap.girl1);
                }
                etName.setText(intent.getStringExtra("name"));
                etGrade.setText(intent.getStringExtra("grade"));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Student student;
        String name = etName.getText().toString();
        String grade = etGrade.getText().toString();
        if ("".equals(name)) {
            Toast.makeText(StudentActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(grade)) {
            Toast.makeText(StudentActivity.this, "请输入分数", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.btn_add_student:
                student = new Student();
                student.setName(name);
                student.setGrade(grade);
                dbHelper.addStudent(student);//添加到数据库中
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_change://改
                student = new Student();
                student.setName(name);
                student.setGrade(grade);
                student.set_id(id);
                dbHelper.updateStudent(student);
                setResult(2, intent);
                finish();//关闭当前界面
                break;
            case R.id.btn_delete://删
                student = new Student();
                student.set_id(id);
                dbHelper.deleteStudent(student);
                setResult(3, intent);
                finish();
                break;
        }
    }
}
