package com.example.bloodpressurerecorder;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class handin extends AppCompatActivity {
    private EditText editTextData1;
    private EditText editTextData2;
    private static final int REQUEST_WRITE_STORAGE = 112;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_handin);
        editTextData1 = findViewById(R.id.editTextData1);
        editTextData2 = findViewById(R.id.editTextData2);
        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

            }
        });
    }

    private void saveData() {

            Toast.makeText(handin.this, "Button Clicked!", Toast.LENGTH_SHORT).show();

            String data1 = editTextData1.getText().toString();
            String data2 = editTextData2.getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());

            String dataToSave = "Time: " + timestamp + ", Data1: " + data1 +", Data2: " + data2 + "\n";
            File file = new File(getFilesDir(), "data_log.txt");
            Log.d("FilePath", "File path: " + file.getAbsolutePath());
            Toast.makeText(handin.this, "Time: " + timestamp + ", Data: " + data1 +", Data2: " + data2 + "\n", Toast.LENGTH_SHORT).show();

            try (FileOutputStream fos = openFileOutput("data_log.txt", MODE_APPEND)) {
                fos.write(dataToSave.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
