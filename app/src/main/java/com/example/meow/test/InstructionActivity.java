package com.example.meow.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class InstructionActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAccept;
    TextView tv_Instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        initViews();
    }

    private void initViews(){
        btnAccept = (Button) findViewById(R.id.btnAcceptInstr);
        btnAccept.setOnClickListener(this);
        tv_Instruction = (TextView) findViewById(R.id.tv_Instruction);
        tv_Instruction.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View v) {
                Intent intent = new Intent (this, SChoiceActivity.class);
                startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
