package com.example.administrator.sqltest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.sqltest.R;
import com.example.administrator.sqltest.entity.Student;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class MyAdapter extends BaseAdapter {
    private List<Student> mData;
    private Context context;

    public MyAdapter(Context context, List<Student> mData) {
        this.context = context;
        this.mData = mData;
    }

    public void setmData(List<Student> mData) {
        this.mData = mData;
        //通知改变数据
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            holder.item_name = (TextView) convertView.findViewById(R.id.name);
            holder.item_grade = (TextView) convertView.findViewById(R.id.grade);
            holder.item_iv = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_name.setText(mData.get(position).getName());
        holder.item_grade.setText(mData.get(position).getGrade());
        //随机设置头像
        if (mData.get(position).get_id() % 2 == 0) {
            holder.item_iv.setImageResource(R.mipmap.boy3);
        } else {
            holder.item_iv.setImageResource(R.mipmap.girl1);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView item_iv;
        TextView item_name;
        TextView item_grade;
    }
}
