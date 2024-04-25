package iot.grassnhi.iotapp;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTHelper {
    public MqttAndroidClient mqttAndroidClient;

    public final String[] arrayTopics = {"grassnhi/feeds/cambien1", "grassnhi/feeds/cambien2", "grassnhi/feeds/cambien3", "grassnhi/feeds/nutnhan1", "grassnhi/feeds/nutnhan2"};

    final String clientId = "nhinhinhi";
    final String username = "grassnhi";
    final String password = "aio_ZIXZ95Gx2FBGoWgab5STZP7Wbuqm";

    final String serverUri = "tcp://io.adafruit.com:1883";

    public MQTTHelper(Context context){

        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                Log.w("Mqtt", "Connection lost!");
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Mqtt", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                Log.w("Mqtt", "Delivery complete!");
            }
        });
        connect();
    }

    public void setCallback(MqttCallback callback) {
        mqttAndroidClient.setCallback(callback);
    }

    private void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                }
            });
        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }

    private void subscribeToTopic() {
        for(int i = 0; i < arrayTopics.length; i++) {
            try {
                mqttAndroidClient.subscribe(arrayTopics[i], 0, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.d("TEST", "Subscribed to topic: " + asyncActionToken.getTopics()[0]);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.d("TEST", "Failed to subscribe to topic: " + asyncActionToken.getTopics()[0]);
                    }
                });

            } catch (MqttException ex) {
                System.err.println("Exception subscribing");
                ex.printStackTrace();
            }
        }
    }
}
