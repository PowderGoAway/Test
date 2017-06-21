package com.example.meow.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPassTest, btnSeeResult, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       initViews();
    }

    private void initViews(){
        btnPassTest = (Button)findViewById(R.id.btnPassTest);
        btnPassTest.setOnClickListener(this);
        btnSeeResult = (Button) findViewById(R.id.btnSeeResult);
        btnSeeResult.setOnClickListener(this);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPassTest:
                Intent intent = new Intent (this, InstructionActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSeeResult:
                SharedPreferences sPref = getSharedPreferences("result",0);
                String selfrate = sPref.getString("34","");
                if (selfrate.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Результаты отсутствуют. Желаете пройти тест?")
                            .setCancelable(false)
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent2 = new Intent (MainActivity.this, InstructionActivity.class);
                                    startActivity(intent2);
                                }
                            })
                            .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else{
                    Intent intent2 = new Intent (this, ProfListActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.btnExit:
                super.onBackPressed();
                break;
            default:
                break;
        }

    }
}
