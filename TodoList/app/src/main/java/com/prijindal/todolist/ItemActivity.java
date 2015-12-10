package com.prijindal.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        int id = intent.getExtras().getInt("id");
        toolbar.setTitle("Item #" + id);
        TodoSQLiteHelper database = new TodoSQLiteHelper(this);
        TodoItem item = database.getItem(id);

        TextView item_text = (TextView) findViewById(R.id.item_text);
        item_text.setText(item.getTask());
    }
}
