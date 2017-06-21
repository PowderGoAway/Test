package com.example.meow.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_Action, tv_ActionNumber;
    Button btnAccept;
    EditText etRate;
    DBHelper db;
    Integer ID = 1;
    SharedPreferences sPref;
    int sum = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initDB();
        initViews();
        checkFieldsForEmptyValues();
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

    private void setAction(){

        tv_Action.setText(db.searchDescription(ID));
        tv_ActionNumber.setText(String.valueOf(ID));

    }

    private void initViews(){
        tv_Action = (TextView)findViewById(R.id.tv_Action);
        tv_ActionNumber = (TextView)findViewById(R.id.tv_ActionNumber);
        setAction();
        btnAccept = (Button) findViewById(R.id.btnAcceptRate);
        btnAccept.setOnClickListener(this);
        etRate = (EditText)findViewById(R.id.et_Rate);
        etRate.addTextChangedListener(mTextWatcher);
    }

    private int getRate(int Sum){
        Rate rate = db.searchRate(ID);
        int input=Integer.parseInt(etRate.getText().toString());
        int dbminRate =rate.getMinRate();
        int dbmaxRate = rate.getMaxRate();
        if (input<dbminRate){
            sum=Sum-1;
        }
        else if(input>dbmaxRate){
            sum=Sum+1;
        }
        else{ sum=Sum+0;}
    return sum;
    }





    @Override
    public void onClick(View v) {
        if (Integer.parseInt(etRate.getText().toString())<0||Integer.parseInt(etRate.getText().toString())>10)
        {
            Toast toast_wrong = Toast.makeText(getApplicationContext(), "Введите оценку от 0 до 10",
                    Toast.LENGTH_SHORT);
            toast_wrong.setGravity(Gravity.CENTER, 0, -55);
            toast_wrong.show();
            etRate.setText("");
        }
        else if (ID!=34)
        {
            sum=getRate(sum);
            String IDPref = String.valueOf(ID);
            sPref = getSharedPreferences("result",0);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(IDPref, etRate.getText().toString());
            ed.commit();
            ID++;
            setAction();
            etRate.setText("");
        }
        else{
            sum=getRate(sum);
            SharedPreferences sharedPreferences = getSharedPreferences("rate",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("sum", sum);
            editor.commit();
            String IDPref = String.valueOf(ID);
            sPref = getSharedPreferences("result",0);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(IDPref, etRate.getText().toString());
            ed.commit();
            Intent intent = new Intent (this, ProfListActivity.class);
            startActivity(intent);
        }
    }

    private void checkFieldsForEmptyValues(){
        if(etRate.getText().toString().equals("")){
            btnAccept.setEnabled(false);
        } else {
            btnAccept.setEnabled(true);
        }
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFieldsForEmptyValues();
        }
    };


    @Override
    public void onBackPressed() {

    }
}
