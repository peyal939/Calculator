package com.example.calculatorproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public EditText num1input, num2input;
    public TextView resultView;
    public ArrayList<String> historyArray;
    public ArrayAdapter<String> historyAdapter;
    public ListView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1input = findViewById(R.id.num1input);
        num2input = findViewById(R.id.num2input);
        resultView = findViewById(R.id.resultView);
        historyList = findViewById(R.id.historyList);
        historyArray = new ArrayList<>();

        historyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyArray);
        historyList.setAdapter(historyAdapter);

        Button buttonDel = findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1input.setText("");
                num2input.setText("");
                resultView.setText("");
            }
        });

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCalc('+');
            }
        });

        Button buttonSub = findViewById(R.id.buttonSub);
        buttonSub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doCalc('-');
            }
        });

        Button buttonMul = findViewById(R.id.buttonMul);
        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCalc('*');
            }
        });

        Button buttonDiv = findViewById(R.id.buttonDiv);
        buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCalc('/');
            }
        });

        Button buttonSin = findViewById(R.id.buttonSin);
        buttonSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doScientificCalc("sin");
            }
        });

        Button buttonCos = findViewById(R.id.buttonCos);
        buttonCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doScientificCalc("cos");
            }
        });

        Button buttonTan = findViewById(R.id.buttonTan);
        buttonTan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doScientificCalc("tan");
            }
        });

        Button buttonLog = findViewById(R.id.buttonLog);
        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doScientificCalc("log");
            }
        });

        Button buttonSqrt = findViewById(R.id.buttonSqrt);
        buttonSqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doScientificCalc("sqrt");
            }
        });


        historyList.setOnItemLongClickListener((parent, view, position, id) -> {
            historyArray.remove(position);
            historyAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    public void doCalc(char operator) {
        String input1str = num1input.getText().toString();
        String input2str = num2input.getText().toString();

        if (input1str.isEmpty() || input2str.isEmpty()) {
            Toast.makeText(this, "Enter both Numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        double num1 = Double.parseDouble(input1str);
        double num2 = Double.parseDouble(input2str);
        double result = 0;
        String operatorSymbol = String.valueOf(operator);

        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    Toast.makeText(this, "Error, Divide by zero is not possible", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }

        DecimalFormat df = new DecimalFormat("#.####");
        resultView.setText("Result: " + df.format(result));
        DecimalFormat sf =new DecimalFormat("#");

        String historyEntry = num1 + " " + operatorSymbol + " " + num2 + " = " + df.format(result);
        historyArray.add(0, historyEntry);
        historyAdapter.notifyDataSetChanged();
    }

    public void doScientificCalc(String operator) {
        String input1str = num1input.getText().toString();

        if (input1str.isEmpty()) {
            Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
            return;
        }

        double num1 = Double.parseDouble(input1str);
        double result = 0;

        switch (operator) {
            case "sin":
                result = Math.sin(Math.toRadians(num1));
                break;
            case "cos":
                result = Math.cos(Math.toRadians(num1));
                break;
            case "tan":
                result = Math.tan(Math.toRadians(num1));
                break;
            case "sqrt":
                if (num1 >= 0) {
                    result = Math.sqrt(num1);
                } else {
                    Toast.makeText(this, "Number is negative", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case "log":
                if (num1 > 0) {
                    result = Math.log10(num1);
                } else {
                    Toast.makeText(this, "Error: Log of non-positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }

        DecimalFormat df = new DecimalFormat("#.####");
        resultView.setText("Result: " + df.format(result));
        DecimalFormat sf =new DecimalFormat("#.##");

        String historyEntry = operator + " " + num1 + " " + " = " + df.format(result);
        historyArray.add(0, historyEntry);
        historyAdapter.notifyDataSetChanged();
    }
}
