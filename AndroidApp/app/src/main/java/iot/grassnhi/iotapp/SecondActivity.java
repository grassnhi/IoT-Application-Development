package iot.grassnhi.iotapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iotapp.R;

public class SecondActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    //widgets


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

}