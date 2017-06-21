package com.example.meow.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class DetailsActivity extends AppCompatActivity {

    private ArrayList<Details> detailsList = new ArrayList<>();
    int idProf;
    DBHelper db;
    SharedPreferences sPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        db = new DBHelper(this);
        Intent intent = getIntent();
        idProf = intent.getIntExtra("idProf",1);
        populateListView();

    }

    public void populateListView() {

        ArrayList<Action> arrAction = new ArrayList<>();
        arrAction = db.searchAction(idProf);
        for (int i = 0; i < arrAction.size(); i++) {
            Action action = arrAction.get(i);
            String descr=action.getDescr();
            sPref = getSharedPreferences("result",0);
            String selfrate = sPref.getString(String.valueOf(action.getActID()),"");
            Details details = new Details();
            details.setDescr(descr);
            details.setSelfrate(selfrate);
            detailsList.add(details);
        }


        ListView list = (ListView) findViewById(R.id.lvDetails);
        ListViewAdapter lvadapter = new ListViewAdapter(this,detailsList);
        list.setAdapter(lvadapter);
        lvadapter.notifyDataSetChanged();

    }


}
