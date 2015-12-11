package com.prijindal.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        final int id = intent.getExtras().getInt("id");
        toolbar.setTitle("Item #" + id);
        final TodoSQLiteHelper database = new TodoSQLiteHelper(this);
        TodoItem item = database.getItem(id);

        final EditText item_text = (EditText) findViewById(R.id.item_text);
        item_text.setText(item.getTask());

        AppCompatButton delete_button = (AppCompatButton) findViewById(R.id.delete_button);
        AppCompatButton save_button = (AppCompatButton) findViewById(R.id.save_button);

        delete_button.setOnClickListener(new AppCompatButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteItem(id);
                goToMain();
            }
        });

        save_button.setOnClickListener(new AppCompatButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.saveTask(id, item_text.getText().toString());
                goToMain();
            }
        });
    }

    public void goToMain() {
        Intent intent = new Intent(ItemActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
    }
}
