package iot.grassnhi.iotapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iotapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    private LineChart lineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnMainPage = findViewById(R.id.btnNav);
        btnMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

//        if(getIntent().hasExtra("iot.grassnhi.iotapp.secView")){
//            TextView tv = (TextView)findViewById(R.id.textView);
//            String text = getIntent().getExtras().getString("iot.grassnhi.iotapp.secView");
//            tv.setText(text);
//        }

        myDB = new DatabaseHelper(SecondActivity.this);

        Button btnTemp = findViewById(R.id.btnTemp);
        Button btnHumi = findViewById(R.id.btnHumi);
        Button btnLight = findViewById(R.id.btnLight);
        Button btnAI = findViewById(R.id.btnAI);
        Button btnDel = findViewById(R.id.btnDel);


        btnTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(myDB.getAllTemperatureData());
            }
        });

        btnHumi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(myDB.getLatestHumidityData());
            }
        });

        btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(myDB.getLatestLightData());
            }
        });

        btnAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(myDB.getLatestAICameraData());
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllTemperatureData();
            }
        });

        lineChart = findViewById(R.id.lineChart);

        setupLineChart();
        displayTemperatureData();
    }

    private void showData(Cursor cursor) {
        StringBuilder dataBuilder = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String data = "ID: " + cursor.getString(0) + "\n" +
                        "VALUE: " + cursor.getString(2) + "\n\n";
                dataBuilder.append(data);
            } while (cursor.moveToNext());

            showMessage("Data", dataBuilder.toString());
        } else {
            showMessage("Data", "No data found");
        }
    }

    private void deleteAllTemperatureData() {
        boolean result = myDB.deleteAllTemperatureData();

        if (result) {
            Toast.makeText(SecondActivity.this, "All temperature data deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SecondActivity.this, "Failed to delete temperature data", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMessage(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }

    private void setupLineChart() {
        // Customize the appearance of the line chart
        Description description = new Description();
        description.setText("Temperature Data Chart");
        lineChart.setDescription(description);
        lineChart.setDrawGridBackground(false);

        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new DateValueFormatter());
        xAxis.setLabelRotationAngle(45f); // Rotate x-axis labels for better visibility

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        // Set line color and other attributes
        LineDataSet dataSet = new LineDataSet(new ArrayList<>(), "Temperature Data");
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);


    }


    private class DateValueFormatter extends ValueFormatter {
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        @Override
        public String getFormattedValue(float value) {
            // Convert timestamp to formatted date-time string
            return dateFormat.format(new Date((long) value));
        }
    }

    private void displayTemperatureData() {
        List<Entry> entries = new ArrayList<>();
        Cursor cursor = myDB.getAllTemperatureData();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long timestamp = convertDateTimeToTimestamp(cursor.getString(cursor.getColumnIndex("DATE_TIME")));
                double value = cursor.getDouble(cursor.getColumnIndex("VALUE"));
                entries.add(new Entry(timestamp, (float) value));
            } while (cursor.moveToNext());
        }

        LineDataSet dataSet = new LineDataSet(entries, "Temperature Data");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLUE);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        lineChart.invalidate(); // Refresh the chart
    }

    private long convertDateTimeToTimestamp(String dateTime) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = dateFormat.parse(dateTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }


}