package com.prijindal.todolist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressBar progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);

        final TextView menu_text = (TextView) findViewById(R.id.menu_text);
        menu_text.setText("Priyanshu");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final TodoSQLiteHelper database = new TodoSQLiteHelper(this);
        populateItems(database.getData());

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                progress_bar.setVisibility(View.VISIBLE);
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                ArrayList<TodoItem> items = database.getData();
                int chosen = menuItem.getItemId();
                switch (chosen) {
                    case R.id.navigation_all:
                        populateItems(items);
                        return true;
                    case R.id.navigation_pending:
                        populateItems(filterItems(items, false));
                        return true;
                    case R.id.navigation_completed:
                        populateItems(filterItems(items, true));
                        return true;
                }
                progress_bar.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateItems(ArrayList<TodoItem> items) {
        ProgressBar progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        TextView emptyText = (TextView) findViewById(R.id.empty_text);
        ListView listView = (ListView) findViewById(R.id.listview);

        progress_bar.setVisibility(View.VISIBLE);
        if (items.isEmpty()) {
            listView.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        }
        else {
            TodoItemsAdapter adapter = new TodoItemsAdapter(this, items);
            listView.setAdapter(adapter);

            listView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.INVISIBLE);
        }
        progress_bar.setVisibility(View.INVISIBLE);
    }

    public ArrayList<TodoItem> filterItems(ArrayList<TodoItem> items, Boolean status) {
        ArrayList<TodoItem> newItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getStatus() == status) {
                newItems.add(items.get(i));
            }
        }
        return newItems;
    }
}
