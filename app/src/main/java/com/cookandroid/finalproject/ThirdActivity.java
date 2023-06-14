package com.cookandroid.finalproject;

        import android.app.Activity;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    View dialogView2, dialogView3;
    Button btnExpense, btnIncome;
    EditText edt1, edt2, edt3, edt4;

    public static final int Request_Code1 = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
        setTitle("지출/수입");

        btnExpense = (Button) findViewById(R.id.btnExpense);
        btnIncome= (Button) findViewById(R.id.btnIncome);

        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView2 = (View) View.inflate(ThirdActivity.this, R.layout.dialog2, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ThirdActivity.this);
                dlg.setView(dialogView2);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edt1 = (EditText) dialogView2.findViewById(R.id.edt1);
                        edt2 = (EditText) dialogView2.findViewById(R.id.edt2);

                        String euse = edt1.getText().toString();
                        String ecost = edt2.getText().toString();

                        final Intent outIntent = new Intent(getApplicationContext(), SecondActivity.class);

                        outIntent.putExtra("EUse", euse);
                        outIntent.putExtra("ECost", ecost);
                        outIntent.putExtra("Expense", "지출");
                        setResult(10, outIntent);
                        finish();
                    }
                });
                dlg.show();
            }
        });

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView3 = (View) View.inflate(ThirdActivity.this, R.layout.dialog3, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ThirdActivity.this);
                dlg.setView(dialogView3);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edt3 = (EditText) dialogView3.findViewById(R.id.edt3);
                        edt4 = (EditText) dialogView3.findViewById(R.id.edt4);

                        String iuse = edt3.getText().toString();
                        String icost = edt4.getText().toString();

                        final Intent outIntent2 = new Intent(getApplicationContext(), SecondActivity.class);

                        outIntent2.putExtra("IUse", iuse);
                        outIntent2.putExtra("ICost", icost);
                        outIntent2.putExtra("Income", "수입");
                        setResult(20, outIntent2);
                        finish();
                    }
                });
                dlg.show();
            }
        });

    }


}
