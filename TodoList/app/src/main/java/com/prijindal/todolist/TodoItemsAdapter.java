package com.prijindal.todolist;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Priyanshu on 12/08/15.
 */
public class TodoItemsAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<TodoItem> mList;
    public TodoItemsAdapter(Context context, ArrayList<TodoItem> list) {
        super(context, R.layout.todo_item, list);

        mContext = context;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.todo_item, null);
        }
        else {
            view = convertView;
        }

        final AppCompatCheckBox todoCheckBox = (AppCompatCheckBox) view.findViewById(R.id.todo_checkbox);
        TextView todoText = (TextView) view.findViewById(R.id.todo_text);

        todoCheckBox.setOnClickListener(new AppCompatCheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {
                // current is the new Value
                Boolean current = todoCheckBox.isChecked();
                Toast.makeText(mContext, "Hello, For setting to " + current, Toast.LENGTH_SHORT).show();
            }
        });

        todoCheckBox.setChecked( mList.get(position).getStatus() );
        todoText.setText( mList.get(position).getTask() );

        return view;
    }
}
