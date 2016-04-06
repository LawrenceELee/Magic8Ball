package com.example.lawrence.magic8ballfortuneteller;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mAnswer;
    private EditText mQuestion;
    private Button mAskButton;

    private Fortune mFortune = new Fortune();

    // for shake detection
    private double acceleration;
    private double currentAcceleration;
    private double lastAcceleration;
    // value used to determine whether user shook hard enough
    private static final int ACCELERATION_THRESHOLD = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get refs to view widgets via R class.
        mAnswer = (TextView) findViewById(R.id.fortuneTextView);
        mQuestion = (EditText) findViewById(R.id.userInputEditText);
        mAskButton = (Button) findViewById(R.id.fortuneButton);

        // click listener for ask button (it's kind of redundant now with the enter button on soft keyboard)
        mAskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayFortune();
            }
        });

        // key listener for when user presses "Enter" on soft keyboard
        mQuestion.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                // If the event is a key-down event on the "enter" button
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    // Perform action on key press
                    // a little buggy that you don't see question
                    //displayFortune();

                    // hide keyboard
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(mQuestion.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    return true;
                }
                return false;
            }
        });

        // detect shake events
        SensorManager sensorManager =
                (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        // register to listen for accelerometer events
        sensorManager.registerListener(
                sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    private void displayFortune() {
        String input = null;

        // check if user input is null.
        // null.toString might be null if user didn't input anything. hence the try-catch.
        try {
            input = mQuestion.getText().toString().replaceAll("\\s+", "");  // get input, trim all whitespace.
        } catch (Exception e) {
            mAnswer.setText("You did not enter any input.");
        }

        // checks if question is blank (after trimming whitespace)
        if (input.equals("")) {
            mAnswer.setText("You did not ask a question.");
        } else {
            String[] fortune = mFortune.getFortune();
            mAnswer.setText(fortune[0]); // get and set the text
            mAnswer.setTextColor(Color.parseColor(fortune[1])); //get and set color of text
        }

        // clear EditText for next question.
        clearQuestion();
    }

    private void clearQuestion() {
        mQuestion.setText("");
    }

    // event handler for accelerometer events
    private final SensorEventListener sensorEventListener = new SensorEventListener() {

        // use accelerometer to determine whether user shook device
        @Override
        public void onSensorChanged(SensorEvent event) {
            long lastUpdate = 0;
            long curTime = System.currentTimeMillis();

            // only allow one shake detect every 100 milliseconds.
            if ((curTime - lastUpdate) > 100) {
                lastUpdate = curTime;
                curTime = System.currentTimeMillis();

                // get x, y, and z values for the SensorEvent
                double x = event.values[0];
                double y = event.values[1];
                double z = event.values[2];

                // save previous acceleration value
                lastAcceleration = currentAcceleration;

                // calculate the current acceleration
                currentAcceleration = x * x + y * y + z * z;

                // calculate the change in acceleration
                acceleration = currentAcceleration * (currentAcceleration - lastAcceleration);

                // if the acceleration is above a certain threshold
                if (acceleration > ACCELERATION_THRESHOLD) {
                    displayFortune();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // not used
            // required method of interface SensorEventListener
        }
    };

}
