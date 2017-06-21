package com.example.meow.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;


public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_ProfResult, tv_Possibility;
    Button btnBackToMenu, btnReturn, btnDetails;
    SharedPreferences sPref;
    DBHelper db;
    int idProf;
    double sum=0;
    String res;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initDB();
        Intent intent = getIntent();
        idProf = intent.getIntExtra("id",1);
        res = String.valueOf(roundUp(getResult(),1));
        initViews();

    }
    public BigDecimal roundUp(double value, int digits){
        return new BigDecimal(""+value).setScale(digits, BigDecimal.ROUND_HALF_UP);
    }

    private void initViews(){
        tv_ProfResult = (TextView) findViewById(R.id.tv_ProfResult);
        Intent intent = getIntent();
        tv_ProfResult.setText(intent.getStringExtra("prof"));
        btnDetails = (Button)findViewById(R.id.btnDetails);
        btnDetails.setOnClickListener(this);
        btnBackToMenu = (Button) findViewById(R.id.btnBackToMenu);
        btnBackToMenu.setOnClickListener(this);
        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);
        tv_Possibility = (TextView) findViewById(R.id.tv_Possibility);
        tv_Possibility.setText(res+"%");

    }

    private double getResult(){
        ArrayList<Data> arrData = db.searchCoefficient(idProf);
        SharedPreferences sharedPreferences = getSharedPreferences("rate",0);
        int rate = sharedPreferences.getInt("sum",0);
        double rateCoef=1;
       if (rate<-5){
            rateCoef = 1.3;
        }
        if (rate>6){
            rateCoef = 0.7;
        }

        for (int i = 0; i < arrData.size(); i++) {
            Data data = arrData.get(i);
            int coef=data.getCoef();
            sPref = getSharedPreferences("result",0);
            int selfrate = Integer.parseInt(sPref.getString(String.valueOf(data.getActId()),""));
            sum+=coef * selfrate;
        }

        sum = sum*rateCoef;
        return sum;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDetails:
                Intent intent = new Intent (this, DetailsActivity.class);
                intent.putExtra("idProf", idProf);
                startActivityForResult(intent,1);
                break;
            case R.id.btnBackToMenu:
                Intent intent2 = new Intent (this, MainActivity.class);
                startActivity(intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.btnReturn:
               finish();
                break;
        }

    }
}
