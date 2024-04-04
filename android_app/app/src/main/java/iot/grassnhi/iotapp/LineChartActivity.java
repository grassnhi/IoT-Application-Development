package iot.grassnhi.iotapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.iotapp.R;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.charts.LineChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LineChartActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    private LineChart tempChart;
    private LineDataSet lineDataSet;
    private LineData lineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        tempChart = findViewById(R.id.tempChart);
        myDB = new DatabaseHelper(this);

        setupChart();
        updateChart();
    }

    private void setupChart() {
        List<Entry> entries = getDataValues();

        lineDataSet = new LineDataSet(entries, "Temperature");
        lineDataSet.setLineWidth(4);
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setValueTextColor(Color.BLACK);

        lineData = new LineData(lineDataSet);

        Description description = new Description();
        description.setText("Temperature Data Chart");
        tempChart.setDescription(description);
        tempChart.setDrawGridBackground(false);

        Legend legend = tempChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);

        XAxis xAxis = tempChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new TimeValueFormatter());
        xAxis.setLabelRotationAngle(45f);

        YAxis leftAxis = tempChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = tempChart.getAxisRight();
        rightAxis.setEnabled(false);

        tempChart.setData(lineData);
        tempChart.invalidate();
    }

    private List<Entry> getDataValues() {
        List<Entry> entries = new ArrayList<>();

        Cursor cursor = myDB.getAllTemperatureData();

        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                long timestamp = getTimestampFromDateString(cursor.getString(cursor.getColumnIndex("DATE_TIME")));
                float temperatureValue = cursor.getFloat(cursor.getColumnIndex("VALUE"));
                entries.add(new Entry(timestamp, temperatureValue));

                // Limit to at most 10 newest values
                count++;
                if (count >= 10) {
                    break;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return entries;
    }

    private void updateChart() {
        List<Entry> updatedEntries = getDataValues();
        lineDataSet.setValues(updatedEntries);
        lineData.notifyDataChanged();
        tempChart.notifyDataSetChanged();
        tempChart.invalidate();
    }

    private long getTimestampFromDateString(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = dateFormat.parse(dateString);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private class TimeValueFormatter extends ValueFormatter {
        private final SimpleDateFormat timeFormatWithSeconds = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        @Override
        public String getFormattedValue(float value) {
            long timestamp = (long) value;
            Date date = new Date(timestamp);
            return timeFormatWithSeconds.format(date);
        }
    }

}
