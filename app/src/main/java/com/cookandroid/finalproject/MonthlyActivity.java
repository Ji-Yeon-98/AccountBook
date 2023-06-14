package com.cookandroid.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MonthlyActivity extends AppCompatActivity {

    TextView tvExpesne, tvIncome;
    CalendarView calendarView;

    String fileName;

    EditText edtText;
    Button btnSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly);
        setTitle("Monthly");

        tvExpesne = (TextView) findViewById(R.id.tvExpense);
        tvIncome = (TextView) findViewById(R.id.tvIncome);
        calendarView = (CalendarView) findViewById(R.id.CalenderView);

        edtText = (EditText) findViewById(R.id.edtText);
        btnSelect = (Button) findViewById(R.id.Button);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        Intent resultIntent = getIntent();
        int sumincome = resultIntent.getIntExtra("SumIncome", 0);
        int sumexpense = resultIntent.getIntExtra("SumExpense", 0);

        tvExpesne.setText(Integer.toString(sumexpense));
        tvIncome.setText(Integer.toString(sumincome));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int monthOfYear, int dayOfMonth) {
                fileName = Integer.toString(year) + "_" + Integer.toString(monthOfYear+1) + "_" + Integer.toString(dayOfMonth) + ".txt";
                String str = readDiary(fileName);
                edtText.setText(str);
                btnSelect.setEnabled(true);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FileOutputStream outFs = openFileOutput(fileName, Context.MODE_PRIVATE);
                    String str = edtText.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(), "저장", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    String readDiary(String fName){
        String diaryStr = null;
        FileInputStream inFs;

        try{
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();

            diaryStr = (new String(txt)).trim();
            btnSelect.setText("수정하기");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            edtText.setHint("내용 없음");
            btnSelect.setText("새로 저장");
        }

        return diaryStr;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menuMonthly:
                Intent monthlyIntent = new Intent(getApplicationContext(), MonthlyActivity.class);
                startActivity(monthlyIntent);
                break;

            case R.id.menuDaily:
                Intent dailyIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(dailyIntent);
                break;
        }

        return false;
    }
}
