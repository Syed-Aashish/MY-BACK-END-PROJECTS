package com.example.demo2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText number1 = findViewById(R.id.number1);
        EditText number2 = findViewById(R.id.number2);
        Button button = findViewById(R.id.button);
        TextView result = findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1Str = number1.getText().toString();
                String num2Str = number2.getText().toString();

                if (!num1Str.isEmpty() && !num2Str.isEmpty()) {
                    double num1 = Double.parseDouble(num1Str);
                    double num2 = Double.parseDouble(num2Str);
                    double sum = num1 + num2;
                    result.setText("Result: " + sum);
                } else {
                    result.setText("Please enter both numbers");
                }
            }
        });
    }
}
