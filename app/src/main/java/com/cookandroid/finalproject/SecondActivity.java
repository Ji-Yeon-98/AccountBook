package com.cookandroid.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.R.id.text1;
import static android.R.id.text2;

public class SecondActivity extends AppCompatActivity {

    Button btnAdd, btnDay;
    View dialogView1;
    TextView tvYear, tvMonth, tvDay;
    ListView listView;

    String fileName;

    ArrayList<HashMap<String, String>> midList = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    int cyear;
    int cmonth;
    int cday;

    public static final int Request_Code = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        setTitle("Daily");

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDay = (Button) findViewById(R.id.btnDay);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);

        listView = (ListView) findViewById(R.id.listView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent thirdIntent = new Intent(getApplicationContext(), ThirdActivity.class);
                startActivityForResult(thirdIntent, Request_Code);
            }
        });

        btnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView1 = (View) View.inflate(SecondActivity.this, R.layout.dialog1, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(SecondActivity.this);
                dlg.setTitle("날짜 선택");
                dlg.setView(dialogView1);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final DatePicker datePicker = (DatePicker) dialogView1.findViewById(R.id.DatePicker1);

                        tvYear.setText(Integer.toString(datePicker.getYear()));
                        tvMonth.setText(Integer.toString(datePicker.getMonth() + 1));
                        tvDay.setText(Integer.toString(datePicker.getDayOfMonth()));

//                        Calendar cal = Calendar.getInstance();
//                        int cYear = cal.get(Calendar.YEAR);
//                        int cMonth = cal.get(Calendar.MONTH);
//                        int cDay = cal.get(Calendar.DAY_OF_MONTH);
//
//                        datePicker.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
//                            @Override
//                            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//
//                                cyear = year;
//                                cmonth = monthOfYear;
//                                cday = dayOfMonth;
//
//                                fileName = Integer.toString(year) + "_" + Integer.toString(monthOfYear+1) + "_" +Integer.toString(dayOfMonth) + ".txt";
//
//
//                            }
//                        });

                    }
                });
                dlg.show();
            }
        });
    }

    @Override
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
                monthlyIntent.putExtra("SumExpense", sumexpense);
                monthlyIntent.putExtra("SumIncome", sumincome);
                startActivity(monthlyIntent);
                break;

            case R.id.menuDaily:
                Intent dailyIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(dailyIntent);
                break;
        }

        return false;
    }

    int sumexpense = 0;
    int sumincome = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case 10:
                final String cost = data.getStringExtra("ECost");
                final String use = data.getStringExtra("EUse");
                final String expense = data.getStringExtra("Expense");

                int sumcost = Integer.parseInt(cost);

                sumexpense += sumcost;

                HashMap<String, String> Item = new HashMap<String, String>();
                Item.put("E/I", expense + " : " + cost);
                Item.put("Use", use);
                midList.add(Item);

                break;

            case 20:
                final String icost = data.getStringExtra("ICost");
                final String iuse = data.getStringExtra("IUse");
                final String income = data.getStringExtra("Income");

                int sumcost2 = Integer.parseInt(icost);

                sumincome += sumcost2;

                HashMap<String, String> Item2 = new HashMap<String, String>();
                Item2.put("E/I", income + " : " + icost);
                Item2.put("Use", iuse);
                midList.add(Item2);

                break;
        }

        adapter = new SimpleAdapter(this, midList,android.R.layout.simple_list_item_2,
                new String[]{"E/I", "Use"}, new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                midList.remove(i);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        final int sumIncome = sumincome;
        final int sumExpense = sumexpense;

    }

//    public ArrayList<HashMap<String, String>> getSavedArrayList() {
//        ArrayList<HashMap<String, String>> savedArrayList = null;
//
//        try {
//            FileInputStream inputStream = openFileInput(fileName);
//            ObjectInputStream in = new ObjectInputStream(inputStream);
//            savedArrayList = (ArrayList<HashMap<String, String>>) in.readObject();
//            in.close();
//            inputStream.close();
//
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return savedArrayList;
//    }
//
//    public void saveArrayList(ArrayList<HashMap<String, String>> arrayList) {
//        try {
//            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
//            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
//            out.writeObject(arrayList);
//            out.close();
//            fileOutputStream.close();
//
//            Toast.makeText(getApplicationContext(), "저장", Toast.LENGTH_SHORT).show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
