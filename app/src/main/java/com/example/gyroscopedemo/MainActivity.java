package com.example.gyroscopedemo;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Button button;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener;
    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(gyroscopeSensor == null) {
            Toast.makeText(this, "device has no gyroscope",Toast.LENGTH_SHORT).show();
            finish();
        }

        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[1]>5f) {
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,200);
                } else if(sensorEvent.values[1]<-5f){
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,200);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
    }
}