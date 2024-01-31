package iot.grassnhi.iotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.iotapp.R;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    MQTTHelper mqttHelper;
    TextView txtTemp, txtHumi;
    LabeledSwitch btnLED, btnPUMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNav = (Button)findViewById(R.id.btnNav);
        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startIntent.putExtra("iot.grassnhi.iotapp.secView", "Second View :>");
                startActivity(startIntent);
            }
        });

        Button btnAda = (Button)findViewById(R.id.btnAda);
        btnAda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ada = "https://io.adafruit.com/grassnhi/dashboards/man-hinh-dieu-hanh";
                Uri address = Uri.parse(ada);

                Intent gotAda = new Intent(Intent.ACTION_VIEW, address);
                if(gotAda.resolveActivity(getPackageManager()) != null){
                    startActivity(gotAda);
                }

            }
        });

        txtTemp = findViewById(R.id.txtTemperature);
        txtHumi = findViewById(R.id.txtHumidity);

        btnLED = findViewById(R.id.btnLED);
        btnPUMP = findViewById(R.id.btnPUMP);

        btnLED.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("grassnhi/feeds/nutnhan1", "1");
                }else{
                    sendDataMQTT("grassnhi/feeds/nutnhan1", "0");
                }
            }
        });

        btnPUMP.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("grassnhi/feeds/nutnhan2", "1");
                }else{
                    sendDataMQTT("grassnhi/feeds/nutnhan2", "0");
                }
            }
        });



        startMQTT();
    }

    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){
        }
    }

    public void startMQTT(){
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended(){
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("TEST", topic + "***" + message.toString());
                if(topic.contains("cambien1")){
                    double temperatureValue = Double.parseDouble(message.toString());
                    storeTemperatureData(temperatureValue);
                    txtTemp.setText(message.toString() + "°C");
                }else if(topic.contains("cambien2")){
                    double humidityValue = Double.parseDouble(message.toString());
                    storeHumidityData(humidityValue);
                    txtHumi.setText(message.toString() + "%");
                }else if(topic.contains("nutnhan1")){
                   if(message.toString().equals("1")){
                       btnLED.setOn(true);
                   }else{
                       btnLED.setOn(false);
                   }
                }else if(topic.contains("nutnhan2")){
                    if(message.toString().equals("1")){
                        btnPUMP.setOn(true);
                    }else{
                        btnPUMP.setOn(false);
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }

            // Method to store temperature data in the database
            private void storeTemperatureData(double temperatureValue) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                boolean isInserted = databaseHelper.insertTemperatureData(temperatureValue);

                if (isInserted) {
                    Log.d("MainActivity", "Temperature data inserted into the database");
                } else {
                    Log.d("MainActivity", "Failed to insert temperature data into the database");
                }
            }

            // Method to store humidity data in the database
            private void storeHumidityData(double humidityValue) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                boolean isInserted = databaseHelper.insertHumidityData(humidityValue);

                if (isInserted) {
                    Log.d("MainActivity", "Humidity data inserted into the database");
                } else {
                    Log.d("MainActivity", "Failed to insert humidity data into the database");
                }
            }

        });
    }
}