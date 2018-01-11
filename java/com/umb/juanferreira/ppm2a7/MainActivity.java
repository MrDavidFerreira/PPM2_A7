/*
Juan David Ferreira G
Ingeniería de Software
Programación en Plataformas Móviles 2
UMB Virtual
Actividad 7
Descripción: Construir una aplicación en Android que permita cambiar el color de la pantalla en diferentes tonalidades de verde
de acuerdo a la cantidad de luz detectada por el sensor. De tal forma, que  cuando haya menos luz, la pantalla del dispositivo
mostrará una tonalidad de verde más oscura y cuando haya más luz el verde será más claro.
 */

package com.umb.juanferreira.ppm2a7;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager = null;
    private Sensor sensorDeLuz = null;
    private TextView textViewValue;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        textViewValue = (TextView)findViewById(R.id.textViewValue);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorDeLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) { }

    @Override
    public void onSensorChanged(SensorEvent arg0) {
        synchronized (this) {
            float[] values;
            if(arg0.sensor.getType() == Sensor.TYPE_LIGHT){
                values = arg0.values;

                float divisor = 40000.0f / 255.0f;
                float green = values[0] / divisor;
                int color = Color.rgb(0, Math.round(green), 0);

                linearLayout.setBackgroundColor(color);

                String light = String.format("%s lux", Float.toString(values[0]));
                textViewValue.setText(light);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(sensorDeLuz == null)
            Toast.makeText(getApplicationContext(), "No hay sensor de Luz", Toast.LENGTH_SHORT).show();
        else
            sensorManager.registerListener(this, sensorDeLuz, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }
}
