package com.cookandroid.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnOK;
    EditText edtPW;
    String PW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("WALLET");

        btnOK = (Button) findViewById(R.id.btnOK);
        edtPW = (EditText) findViewById(R.id.edtPW);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PW = edtPW.getText().toString();
                if(PW.equals("1234")){
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "비밀번호를 잘못입력하셨습니다.", Toast.LENGTH_SHORT).show();
                    edtPW.setText("");
                }
            }
        });
    }
}
