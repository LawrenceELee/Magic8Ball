package com.example.lawrence.fortuneteller;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mAnswer;
    private TextView mQuestionTextField;
    private EditText mQuestionEditText;
    private Button mAskButton;
    private ImageView mBackgroundImageView;
    private Animation shakeAnimation;

    private FortuneFragment mFortuneFragment;

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

        mFortuneFragment = new FortuneFragment();

        // get refs to view widgets via R class.
        mAnswer = (TextView) findViewById(R.id.fortuneTextView);
        mQuestionTextField = (TextView) findViewById(R.id.questionTextView);
        mQuestionEditText = (EditText) findViewById(R.id.userInputEditText);
        mAskButton = (Button) findViewById(R.id.fortuneButton);
        mBackgroundImageView = (ImageView) findViewById(R.id.backgroundImageView);

        // load animation layout file
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);

        // click listener for ask button (it's kind of redundant now with the enter button on soft keyboard)
        mAskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAnimation();
                displayFortune();
            }
        });

        // key listener for when user presses "Enter" on soft keyboard
        mQuestionEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                // If the event is a key-down event on the "enter" button
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    // hide keyboard
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(mQuestionEditText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    // Perform action on key press
                    // a workaround for the bug is to place edittext at the top of screen.
                    // workaround doesn't fix bug.
                    //displayFortune();

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
            input = mQuestionEditText.getText().toString().replaceAll("^\\s+|\\s+$", "");
            // get input, trim all whitespace at beginning and end of string.
        } catch (Exception e) {
            mAnswer.setText("You did not enter any input.");
            mAnswer.setTextColor(Color.BLACK);
            mQuestionTextField.setText("");
        }

        // checks if question is blank (after trimming whitespace)
        if (input.equals("")) {
            mAnswer.setText("You did not ask a question.");
            mAnswer.setTextColor(Color.BLACK);
            mQuestionTextField.setText("");
        } else {
            Fortune fortune = mFortuneFragment.getFortune();
            mAnswer.setText(fortune.getText()); // get and set the text
            mAnswer.setTextColor(fortune.getColor()); //get and set color of text
            mQuestionTextField.setText(input);
        }

        // clear EditText for next question.
        clearQuestion();
    }

    // helper method to display shake animation for magic 8 ball image
    private void displayAnimation(){
        mBackgroundImageView.startAnimation(shakeAnimation);
    }

    private void clearQuestion() {
        mQuestionEditText.setText("");
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
