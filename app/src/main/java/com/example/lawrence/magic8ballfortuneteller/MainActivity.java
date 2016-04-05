package com.example.lawrence.magic8ballfortuneteller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Formatter;

public class MainActivity extends AppCompatActivity {

    private Fortune mFortune = new Fortune();

    private TextView mAnswer;
    private EditText mQuestion;
    private Button mAskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get refs to view widgets via R class.
        mAnswer = (TextView) findViewById(R.id.fortuneTextView);
        mQuestion = (EditText) findViewById(R.id.userInputEditText);
        mAskButton = (Button) findViewById(R.id.fortuneButton);

        // click listener for ask button
        mAskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayFortune();
                clearQuestion();
            }
        });

    }

    private void displayFortune(){
        String input = null;

        // check if user input is null.
        // null.toString might be null if user didn't input anything. hence the try-catch.
        try {
            input = mQuestion.getText().toString().replaceAll("\\s+", "");  // get input, trim all whitespace.
        } catch (Exception e){
            mAnswer.setText("You did not enter any input.");
        }

        // checks if question is blank (after trimming whitespace)
        if( input.equals("") ) {
            mAnswer.setText("You did not ask a question.");
        } else {
            String fortune = mFortune.getFortune();
            mAnswer.setText(fortune);
        }
    }

    private void clearQuestion(){
        mQuestion.setText("");
    }
}
