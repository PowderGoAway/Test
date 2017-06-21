package com.example.meow.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SChoiceActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAcceptS;
    RadioGroup radioGroup;
    RadioButton rbtn_Man, rbtn_Woman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoice);
        initViews();
    }

    private void initViews(){
        btnAcceptS = (Button) findViewById(R.id.btnAcceptS);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        rbtn_Man = (RadioButton) findViewById(R.id.radio_man);
        rbtn_Woman = (RadioButton) findViewById(R.id.radio_woman);
        btnAcceptS.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if(selectedId == rbtn_Man.getId()) {
            Intent intent = new Intent (this, TestActivity.class);
            startActivity(intent);
        } else if(selectedId == rbtn_Woman.getId()) {
            Intent intent = new Intent (this, TestActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Не выбран пол",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 150);
            toast.show();
        }

    }


    @Override
    public void onBackPressed() {

    }
}
