package com.example.project09_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker dp;
    EditText edtDiary;
    Button btnW;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 일기장");

        dp = (DatePicker) findViewById(R.id.dataP);
        edtDiary = (EditText) findViewById(R.id.editD);
        btnW = (Button) findViewById(R.id.btnW);

        Calendar cal = Calendar.getInstance();

        int cY = cal.get(Calendar.YEAR);
        int cM = cal.get(Calendar.MONTH);
        int cD = cal.get(Calendar.DAY_OF_MONTH);

        dp.init(cY, cM, cD, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fileName = Integer.toString(i) + "_" + Integer.toString(i1 = 1) + "_" + Integer.toString(i2) + ".txt";
                String str = readDiary(fileName);
                edtDiary.setText(str);
                btnW.setEnabled(true);
            }
        });

        btnW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outFs = openFileOutput(fileName, Context.MODE_PRIVATE);
                    String str = edtDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(), fileName + " 이 저장됨", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    String readDiary (String fName) {
        String diaryStr = null;
        FileInputStream inFs;

        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            btnW.setText("Modify");

        } catch (IOException e) {
//            throw new RuntimeException(e);
            edtDiary.setHint("해당 날짜 내용 없음");
            btnW.setText("새로 저장");
        }
        return diaryStr;
    }

}