package com.example.lab1calculator;

//import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Context;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv,solutionTv;
    MaterialButton buttonC,buttonBc,buttonBo;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonMul, buttonDiv, buttonAdd, buttonSub, buttonEq;
    MaterialButton buttonAc,buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignID(buttonC,R.id.button_c);
        assignID(buttonBo,R.id.button_opa);
        assignID(buttonBc,R.id.button_cpa);
        assignID(buttonDiv,R.id.button_div);

        assignID(button7,R.id.button_7);
        assignID(button8,R.id.button_8);
        assignID(button9,R.id.button_9);
        assignID(buttonMul,R.id.button_mul);

        assignID(button4,R.id.button_4);
        assignID(button5,R.id.button_5);
        assignID(button6,R.id.button_6);
        assignID(buttonAdd,R.id.button_add);

        assignID(button1,R.id.button_1);
        assignID(button2,R.id.button_2);
        assignID(button3,R.id.button_3);
        assignID(buttonSub,R.id.button_min);

        assignID(buttonAc,R.id.button_ac);
        assignID(button0,R.id.button_0);
        assignID(buttonDot,R.id.button_dec);
        assignID(buttonEq,R.id.button_eq);


    }

    /**
     *
     * @param btn a MaterialButton object
     * @param id a number representing an object ID
     * assigns a MaterialButton a corresponding ID and sets an OnClicklListener for that button
     */
    void assignID(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("ac")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        }
        if (buttonText.equals("C")) {
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        if(solutionTv.getText().equals("")&&buttonText.equals("0")){
            return;
        }

        solutionTv.setText(dataToCalculate);

        String finalResult=getResults(dataToCalculate);
        if(!finalResult.equals("Error")){
            resultTv.setText(finalResult);
        }

    }

    String getResults(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable,data,"Javascript",1,null).toString();

        }catch (Exception e){
            return"Error";
        }
    }
}