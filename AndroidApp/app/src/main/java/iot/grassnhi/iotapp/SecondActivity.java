package iot.grassnhi.iotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.iotapp.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if(getIntent().hasExtra("iot.grassnhi.iotapp.secView")){
            TextView tv = (TextView)findViewById(R.id.textView);
            String text = getIntent().getExtras().getString("iot.grassnhi.iotapp.secView");
            tv.setText(text);
        }
    }
}