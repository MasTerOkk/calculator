package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button mButton0, mButton1, mButton2, mButton3,
            mButton4, mButton5, mButton6, mButton7,
            mButton8, mButton9, mButtonPoint, mButtonAdd,
            mButtonSub, mButtonDiv, mButtonMul, mButtonEq,
            mButtonRoot, mButtonSquare, mButtonM, mButtonMC;
    EditText mEditText;

    private List<String> expression = new ArrayList<>(); // Список для хранения выражения
    private Double memory = null; // Переменная для хранения промежуточного результата

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация кнопок
        mButton0 = findViewById(R.id.button0);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);
        mButton6 = findViewById(R.id.button6);
        mButton7 = findViewById(R.id.button7);
        mButton8 = findViewById(R.id.button8);
        mButton9 = findViewById(R.id.button9);
        mButtonPoint = findViewById(R.id.button_coma);
        mButtonAdd = findViewById(R.id.button_plus);
        mButtonSub = findViewById(R.id.button_minus);
        mButtonMul = findViewById(R.id.button_multiply);
        mButtonDiv = findViewById(R.id.button_divide);
        mButtonEq = findViewById(R.id.button_equal);
        mButtonRoot = findViewById(R.id.button_root);
        mButtonSquare = findViewById(R.id.button_square);
        mButtonM = findViewById(R.id.button_m);
        mButtonMC = findViewById(R.id.button_mc);
        mEditText = findViewById(R.id.editTextText);

        // Обработка нажатий числовых кнопок
        View.OnClickListener numberClickListener = v -> {
            Button button = (Button) v;
            mEditText.append(button.getText());
        };

        mButton0.setOnClickListener(numberClickListener);
        mButton1.setOnClickListener(numberClickListener);
        mButton2.setOnClickListener(numberClickListener);
        mButton3.setOnClickListener(numberClickListener);
        mButton4.setOnClickListener(numberClickListener);
        mButton5.setOnClickListener(numberClickListener);
        mButton6.setOnClickListener(numberClickListener);
        mButton7.setOnClickListener(numberClickListener);
        mButton8.setOnClickListener(numberClickListener);
        mButton9.setOnClickListener(numberClickListener);
        mButtonPoint.setOnClickListener(numberClickListener);

        // Обработка нажатий операторов
        View.OnClickListener operatorClickListener = v -> {
            Button button = (Button) v;
            String currentText = mEditText.getText().toString();

            if (!currentText.isEmpty()) {
                expression.add(currentText);
                expression.add(button.getText().toString());
                mEditText.setText("");
            }
        };

        mButtonAdd.setOnClickListener(operatorClickListener);
        mButtonSub.setOnClickListener(operatorClickListener);
        mButtonMul.setOnClickListener(operatorClickListener);
        mButtonDiv.setOnClickListener(operatorClickListener);

        mButtonRoot.setOnClickListener(v -> {
            String currentText = mEditText.getText().toString();
            if (!currentText.isEmpty()) {
                double value = Math.sqrt(Double.parseDouble(currentText));
                mEditText.setText(String.valueOf(value));
            }
        });

        mButtonSquare.setOnClickListener(v -> {
            String currentText = mEditText.getText().toString();
            if (!currentText.isEmpty()) {
                double value = Math.pow(Double.parseDouble(currentText), 2);
                mEditText.setText(String.valueOf(value));
            }
        });

        // Вычисление результата
        mButtonEq.setOnClickListener(v -> {
            String currentText = mEditText.getText().toString();
            if (!currentText.isEmpty()) {
                expression.add(currentText);
                double result = calculateExpression(expression);
                mEditText.setText(String.valueOf(result));
                expression.clear();
            }
        });

        // Сохранение в память (M)
        mButtonM.setOnClickListener(v -> {
            String currentText = mEditText.getText().toString();
            if (!currentText.isEmpty()) {
                memory = Double.parseDouble(currentText);
                mEditText.setText("");
            }
        });

        // Очистка памяти (MC)
        mButtonMC.setOnClickListener(v -> {
            memory = null;
        });

        mButtonM.setOnLongClickListener(v -> {
            if (memory != null) {
                mEditText.setText(String.valueOf(memory));
            }
            return true;
        });
    }

    private double calculateExpression(List<String> expression) {
        double result = Double.parseDouble(expression.get(0));
        for (int i = 1; i < expression.size(); i += 2) {
            String operator = expression.get(i);
            double nextValue = Double.parseDouble(expression.get(i + 1));

            switch (operator) {
                case "+":
                    result += nextValue;
                    break;
                case "-":
                    result -= nextValue;
                    break;
                case "*":
                    result *= nextValue;
                    break;
                case "/":
                    if (nextValue != 0) {
                        result /= nextValue;
                    } else {
                        mEditText.setText("Error");
                        return 0;
                    }
                    break;
            }
        }
        return result;
    }
}
