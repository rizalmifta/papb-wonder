package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView sensorText;
    private TextView sensorProximityText;
    private TextView sensorAccelerometerText;
    private SensorManager sensorManager;
    private Sensor sensorProximity;
    private Sensor sensorAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorText=findViewById(R.id.sensor_list);
        sensorProximityText= findViewById(R.id.sensor_proximity);
        sensorAccelerometerText= findViewById(R.id.sensor_accelerometer);
        sensorManager=(SensorManager)getSystemService(
                Context.SENSOR_SERVICE);
        sensorProximity=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorAccelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(sensorProximity==null){
            Toast.makeText(this,"No Proximity Sensor",Toast.LENGTH_SHORT).show();
        }


        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sensorTx= new StringBuilder();
        for(Sensor sensor:sensorList){
            sensorTx.append(sensor.getName()+" "
                            +sensor.getVendor()+"\n");

        }
        sensorText.setText(sensorTx.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sensorProximity!=null){
            sensorManager.registerListener(this,sensorProximity,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(sensorAccelerometer!=null){
            sensorManager.registerListener(this,sensorAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType=sensorEvent.sensor.getType();
        float value=sensorEvent.values[0];
        if(sensorType==Sensor.TYPE_PROXIMITY){
            sensorProximityText.setText("Proximity Sensor: "+value);
        }
        if(sensorType==Sensor.TYPE_ACCELEROMETER){
            sensorAccelerometerText.setText("Accelerometer Sensor:"+value);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
