package com.example.bloodpressurerecorder;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChartLineViewActivity extends AppCompatActivity {
    private LineChart lineChart;
    List<String> xVals = new ArrayList<>(); // 存储时间戳字符串

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        lineChart = findViewById(R.id.lineChart);
        List<Entry> entriesData1 = new ArrayList<>();
        List<Entry> entriesData2 = new ArrayList<>();

        // 读取文件数据并分离 data1 和 data2
        readFileData(entriesData1, entriesData2);

        setupChart(lineChart, entriesData1, entriesData2);

        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

    }

    private void readFileData(List<Entry> entriesData1, List<Entry> entriesData2) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(getFilesDir(), "data_log.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(", Data1: ") && line.contains(", Data2: ")) {
                    // 提取时间戳
                    int timeEndIndex = line.indexOf(", Data1: ");
                    String timestampPart = line.substring("Time: ".length(), timeEndIndex).trim();
                    xVals.add(timestampPart);

                    // 提取 Data1
                    int data1StartIndex = line.indexOf(", Data1: ") + ", Data1: ".length();
                    int data2StartIndex = line.indexOf(", Data2: ");
                    String dataPart1 = line.substring(data1StartIndex, data2StartIndex).trim();

                    // 提取 Data2
                    String dataPart2 = line.substring(data2StartIndex + ", Data2: ".length()).trim();

                    float yValue1 = Float.parseFloat(dataPart1);
                    float yValue2 = Float.parseFloat(dataPart2);

                    entriesData1.add(new Entry(xVals.size() - 1, yValue1));
                    entriesData2.add(new Entry(xVals.size() - 1, yValue2));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupChart(LineChart lineChart, List<Entry> entriesData1, List<Entry> entriesData2) {
        // 创建两条折线的数据集
        LineDataSet dataSet1 = new LineDataSet(entriesData1, "Data1");
        LineDataSet dataSet2 = new LineDataSet(entriesData2, "Data2");

        // 设置折线的颜色和宽度
        dataSet1.setColor(Color.parseColor("#FF4081"));
        dataSet1.setLineWidth(2f);
        dataSet2.setColor(Color.parseColor("#3F51B5"));
        dataSet2.setLineWidth(2f);

        LineData lineData = new LineData(dataSet1, dataSet2);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);

        lineChart.setData(lineData);
        lineChart.invalidate(); // 刷新图表
    }
}