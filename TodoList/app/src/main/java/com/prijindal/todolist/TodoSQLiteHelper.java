package com.prijindal.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Priyanshu on 12/10/15.
 */
public class TodoSQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "TodoList";
    private static final String TABLE_NAME = "todos";

    public TodoSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODOS_TABLE = "CREATE TABLE "+ TABLE_NAME +" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "task TEXT," +
                "status INTEGER" +
                ") ";
        db.execSQL(CREATE_TODOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTask(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("task", item.getTask());
        values.put("status", item.getStatus());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE id=" + id;
        db.execSQL(DELETE_QUERY);
        db.close();
    }

    public void setStatus(int id, Boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();
        int stCode = status ? 1 : 0;
        String UPDATE_QUERY = "UPDATE " + TABLE_NAME + " SET status=" + stCode +" WHERE id=" + id;
        db.execSQL(UPDATE_QUERY);
        db.close();
    }

    public ArrayList<TodoItem> getData() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, task, status FROM " + TABLE_NAME, new String[] {});
        ArrayList<TodoItem> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String task = cursor.getString(cursor.getColumnIndex("task"));
            int stCode = cursor.getInt(cursor.getColumnIndex("status"));
            Boolean status = stCode != 0;
            TodoItem item = new TodoItem(task, status);
            item.setId(id);
            items.add(item);
        }
        cursor.close();
        return items;
    }

    public TodoItem getItem(int id) {
        ArrayList<TodoItem> items = getData();
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getId() == id) {
                return items.get(i);
            }
        }
        return null;
    }
}
