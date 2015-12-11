package com.prijindal.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.todo_item, null);
        }
        else {
            view = convertView;
        }

        final AppCompatCheckBox todoCheckBox = (AppCompatCheckBox) view.findViewById(R.id.todo_checkbox);

        final TodoSQLiteHelper database = new TodoSQLiteHelper(mContext);
        TextView todoText = (TextView) view.findViewById(R.id.todo_text);

        todoCheckBox.setOnClickListener(new AppCompatCheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {
                // current is the new Value
                Boolean current = todoCheckBox.isChecked();
                database.setStatus(mList.get(position).getId(), current);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ItemActivity.class);
                intent.putExtra("id", mList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        todoCheckBox.setChecked( mList.get(position).getStatus() );
        todoText.setText( mList.get(position).getTask() );

        return view;
    }
}
