package iot.grassnhi.iotapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iotapp.R;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    MQTTHelper mqttHelper;
    TextView txtTemp, txtHumi, txtLight;
    LabeledSwitch btnLED, btnPUMP;
    ImageView imageView1, imageView2, imageView3;
    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNav = findViewById(R.id.btnNav);
        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startIntent.putExtra("iot.grassnhi.iotapp.secView", "Second View :>");
                startActivity(startIntent);
            }
        });

        Button btnAda = findViewById(R.id.btnAda);
        btnAda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ada = "https://io.adafruit.com/grassnhi/dashboards/man-hinh-dieu-hanh";
                Uri address = Uri.parse(ada);

                Intent gotAda = new Intent(Intent.ACTION_VIEW, address);
                if (gotAda.resolveActivity(getPackageManager()) != null) {
                    startActivity(gotAda);
                }

            }
        });

        txtTemp = findViewById(R.id.txtTemperature);
        txtHumi = findViewById(R.id.txtHumidity);
        txtLight = findViewById(R.id.txtLight);

        btnLED = findViewById(R.id.btnLED);
        btnPUMP = findViewById(R.id.btnPUMP);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);

        myDB = new DatabaseHelper(MainActivity.this);

        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
//                btnLED.setEnabled(false);
//                btnPUMP.setEnabled(false);
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Mqtt", mqttMessage.toString());
                onMessageReceived(topic, mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//                btnLED.setEnabled(true);
//                btnPUMP.setEnabled(true);
            }
        });

        btnLED.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, final boolean isOn) {

                if (isOn == true) {
                    sendDataMQTT("grassnhi/feeds/nutnhan1", "1"); // Pass current button state
                } else {
                    sendDataMQTT("grassnhi/feeds/nutnhan1", "0"); // Pass current button state
                }
            }
        });

        btnPUMP.setOnToggledListener(new OnToggledListener() {

            @Override
            public void onSwitched(ToggleableView toggleableView, final boolean isOn) {
                if (isOn == true) {
                    sendDataMQTT("grassnhi/feeds/nutnhan2", "1"); // Pass current button state

                } else {
                    sendDataMQTT("grassnhi/feeds/nutnhan2", "0"); // Pass current button state
                }
            }
        });

        fetchInitialStateFromServer();

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(myDB.getAllTemperatureData());
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(myDB.getAllHumidityData());
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(myDB.getAllLightData());
            }
        });

        txtTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LineChartActivity
                startActivity(new Intent(MainActivity.this, LineChartActivity.class));
            }
        });
    }

    private void fetchInitialStateFromServer() {

//        mqttHelper.mqttAndroidClient.subscribe("grassnhi/feeds/nutnhan1", 0);
//        mqttHelper.mqttAndroidClient.subscribe("grassnhi/feeds/nutnhan2", 0);
    }
    public void sendDataMQTT(final String topic, final String value) {

        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);
        msg.setPayload(value.getBytes(Charset.forName("UTF-8")));

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "Failed to send message. Please check your connection.", Toast.LENGTH_SHORT).show();
                    // Revert the button state to its previous state
                    if (topic.equals("grassnhi/feeds/nutnhan1")) {
                        btnLED.setOn(!btnLED.isOn());

//                        btnLED.setEnabled(false);
                    } else if (topic.equals("grassnhi/feeds/nutnhan2")) {
                        btnPUMP.setOn(!btnPUMP.isOn());
//                        btnPUMP.setEnabled(false);
                    }
                }
            });
        } catch (MqttException e) {
            Toast.makeText(MainActivity.this, "Failed to send message.", Toast.LENGTH_SHORT).show();
            // Revert the button state to its previous state
            if (topic.equals("grassnhi/feeds/nutnhan1")) {
                btnLED.setOn(!btnLED.isOn());
            } else if (topic.equals("grassnhi/feeds/nutnhan2")) {
                btnPUMP.setOn(!btnPUMP.isOn());
            }
        }
    }


    private void onMessageReceived(String topic, String message) {
        if (topic.contains("cambien1")) {
            double temperatureValue = Double.parseDouble(message);
            storeTemperatureData(temperatureValue);
            txtTemp.setText(message + "°C");
        } else if (topic.contains("cambien2")) {
            double humidityValue = Double.parseDouble(message);
            storeHumidityData(humidityValue);
            txtHumi.setText(message + "%");
        } else if (topic.contains("cambien3")) {
            double luxValue = Double.parseDouble(message);
            storeLightData(luxValue);
            txtLight.setText(message + " lux");
        } else if (topic.contains("nutnhan1")) {
            if (message.equals("1")) {
                btnLED.setOn(true);
            } else {
                btnLED.setOn(false);
            }
        } else if (topic.contains("nutnhan2")) {
            if (message.equals("1")) {
                btnPUMP.setOn(true);
            } else {
                btnPUMP.setOn(false);
            }
        }
    }

    private void storeTemperatureData(double temperatureValue) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        boolean isInserted = databaseHelper.insertTemperatureData(temperatureValue);

        if (isInserted) {
            Log.d("MainActivity", "Temperature data inserted into the database");
        } else {
            Log.d("MainActivity", "Failed to insert temperature data into the database");
        }
    }

    private void storeHumidityData(double humidityValue) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        boolean isInserted = databaseHelper.insertHumidityData(humidityValue);

        if (isInserted) {
            Log.d("MainActivity", "Humidity data inserted into the database");
        } else {
            Log.d("MainActivity", "Failed to insert humidity data into the database");
        }
    }

    private void storeLightData(double luxValue) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        boolean isInserted = databaseHelper.insertLightData(luxValue);

        if (isInserted) {
            Log.d("MainActivity", "Light data inserted into the database");
        } else {
            Log.d("MainActivity", "Failed to insert Light data into the database");
        }
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
            Toast.makeText(MainActivity.this, "All temperature data deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Failed to delete temperature data", Toast.LENGTH_SHORT).show();
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
