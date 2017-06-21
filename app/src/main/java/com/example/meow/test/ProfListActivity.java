package com.example.meow.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Meow on 28.04.2017.
 */

public class ProfListActivity extends AppCompatActivity {

    private ArrayList<String> profList;
    ListView list;
    Intent intent;
    DBHelper db;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proflist);
        initDB();
        initViews();
        populateListView();
        registerClickCallback();
    }

    public void initViews(){
        list = (ListView) findViewById(R.id.listView);
        profList = new ArrayList<>();
        searchView = (SearchView)findViewById(R.id.searchView2);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> tempList = new ArrayList<>();
                for(String temp:profList){
                    if(temp.toLowerCase().contains(newText.toLowerCase())){
                        tempList.add(temp);
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ProfListActivity.this,
                        R.layout.prof_item,tempList);
                list.setAdapter(arrayAdapter);

                return true;
            }
        });
    }

    private void initDB(){
        db = new DBHelper(this);
        try {
            db.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        db.openDataBase();
    }

    public void populateListView() {

        profList = db.getProfName();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.prof_item,
                profList);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    public void registerClickCallback() {
        intent = new Intent(this, ResultActivity.class);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String profession =  textView.getText().toString();
                intent.putExtra("prof", textView.getText().toString());
                intent.putExtra("id", db.searchProfID(profession));
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
