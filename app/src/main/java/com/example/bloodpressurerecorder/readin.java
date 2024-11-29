package com.example.bloodpressurerecorder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class readin extends AppCompatActivity {
    private TextView textViewFileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readin);

        textViewFileContent = findViewById(R.id.textViewFileContent);
        Button buttonReadFile = findViewById(R.id.buttonReadFile);
        buttonReadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readAndDisplayFile();
            }
        });
    }

    private void readAndDisplayFile() {
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fis = null;
        try {
            fis = openFileInput("data_log.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        textViewFileContent.setText(stringBuilder.toString());
    }
}