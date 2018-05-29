package com.example.szymon.mygameproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;



public class OrientationData implements SensorEventListener {
    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnomater;

    private float[] accelOutput;
    private float[] magOutput;
    private float[] orientation = new float[3];
    public float[] getOrientation(){
        return orientation;
    }

    private float[] startOrientation = null;
    public float[] getStartOrientation(){
        return startOrientation;
    }

    public void newGame(){
            startOrientation = null;
    }

    public OrientationData() {
        manager = (SensorManager) Game.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnomater = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }


    public void register(){
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magnomater, SensorManager.SENSOR_DELAY_GAME);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelOutput=event.values;
        else if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
            magOutput=event.values;
        if(accelOutput !=null && magOutput != null){
            float[] R = new float[9];
            float[] I = new float[9];
            boolean succes = SensorManager.getRotationMatrix(R,I,accelOutput,magOutput);
            if(succes){
                SensorManager.getOrientation(R,orientation);
                if(startOrientation==null){
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0,orientation.length);
                }
            }
        }
    }

}
