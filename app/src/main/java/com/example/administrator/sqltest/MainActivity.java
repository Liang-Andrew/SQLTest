package com.example.administrator.sqltest;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.sqltest.adapter.MyAdapter;
import com.example.administrator.sqltest.db.StudentDbHelper;
import com.example.administrator.sqltest.entity.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private Button btn_add, btn_search;
    private StudentDbHelper dbHelper;
    private List<Student> allStudent;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_search = (Button) findViewById(R.id.btn_search);
        mListView = (ListView) findViewById(R.id.stduent_list);
        //初始化dbHelper
        dbHelper = new StudentDbHelper(this);
        //设置点击事件
        btn_add.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        allStudent = dbHelper.getAllStudent();
        //把数据传递给适配器
        adapter = new MyAdapter(this, allStudent);
        mListView.setAdapter(adapter);


        //条目单击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                intent.putExtra("type", "Look");
                intent.putExtra("_id", allStudent.get(position).get_id());
                intent.putExtra("name", allStudent.get(position).getName());
                intent.putExtra("grade", allStudent.get(position).getGrade());
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Intent intent = new Intent(this, StudentActivity.class);
                intent.putExtra("type", "Add");
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_search:
                //弹出对话框-->自定义的
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_search, null);
                final AlertDialog dialog = builder.create();
                builder.setView(layout);
                builder.show();
                //为Dialog中的控件添加点击事件
                final EditText searchName = (EditText) layout.findViewById(R.id.search_name);
                Button btn_search = (Button) layout.findViewById(R.id.btn_search_dialog);
                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchName.setVisibility(View.GONE);
                        ListView listView = (ListView) layout.findViewById(R.id.search_result);
                        final List<Student> studentList = dbHelper.queryStudent(searchName.getText().toString());
                        if (studentList != null) {
                            listView.setAdapter(new MyAdapter(getApplicationContext(), studentList));
                            listView.setVisibility(View.VISIBLE);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                                    intent.putExtra("type", "Look");
                                    intent.putExtra("_id", studentList.get(position).get_id());
                                    intent.putExtra("name", studentList.get(position).getName());
                                    intent.putExtra("grade", studentList.get(position).getGrade());
                                    startActivityForResult(intent, 2);
                                }
                            });
                        } else {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "无此学生", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (resultCode == 2) {
                    Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                if (resultCode == 3) {
                    Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        //刷新数据方式1:
        //onCreate(null);
        //刷新数据方式2:
        List<Student> allStudent = dbHelper.getAllStudent();
        adapter.setmData(allStudent);
    }
}
