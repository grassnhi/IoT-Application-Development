package iot.grassnhi.iotapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.iotapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LineChartActivity extends AppCompatActivity {

    LineChart tempChart, humiChart, luxChart;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        tempChart = (LineChart) findViewById(R.id.tempChart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValue1(), "Temperature History");
        ArrayList<ILineDataSet> dataSets1 = new ArrayList<>();
        dataSets1.add(lineDataSet1);

        LineData data1 = new LineData(dataSets1);
        tempChart.setData(data1);
        tempChart.invalidate();

        humiChart = (LineChart) findViewById(R.id.humiChart);
        LineDataSet lineDataSet2 = new LineDataSet(dataValue2(), "Humidity History");
        ArrayList<ILineDataSet> dataSets2 = new ArrayList<>();
        dataSets2.add(lineDataSet2);

        LineData data2 = new LineData(dataSets2);
        humiChart.setData(data2);
        humiChart.invalidate();

        luxChart = (LineChart) findViewById(R.id.luxChart);
        LineDataSet lineDataSet3 = new LineDataSet(dataValue3(), "Light History");
        ArrayList<ILineDataSet> dataSets3 = new ArrayList<>();
        dataSets3.add(lineDataSet3);

        LineData data3 = new LineData(dataSets3);
        luxChart.setData(data3);
        luxChart.invalidate();
    }

    private ArrayList<Entry> dataValue1(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 29));
        dataVals.add(new Entry(1, 30));
        dataVals.add(new Entry(2, 28));
        dataVals.add(new Entry(3, 29.5F));
        dataVals.add(new Entry(4, 32));

        return dataVals;
    }
    private ArrayList<Entry> dataValue2(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 80));
        dataVals.add(new Entry(1, 65));
        dataVals.add(new Entry(2, 70.9F));
        dataVals.add(new Entry(3, 50));
        dataVals.add(new Entry(4, 45));

        return dataVals;
    }
    private ArrayList<Entry> dataValue3(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 123));
        dataVals.add(new Entry(1, 200));
        dataVals.add(new Entry(2, 234));
        dataVals.add(new Entry(3, 130));
        dataVals.add(new Entry(4, 110));

        return dataVals;
    }
}
