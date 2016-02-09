package com.example.kenneth.mortgagecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends Activity {

    double amount = 10000.00; //Loan amount
    double interestrate = 7.0; //Interest Rate
    double loanterm = 30; //Loan Term in years
    boolean tax = false; //Include tax or not

    EditText loantext;
    EditText interesttext;
    CheckBox taxtext;
    TextView paymenttext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loantext = (EditText) findViewById(R.id.amount);
        interesttext = (EditText) findViewById(R.id.interest);
        taxtext = (CheckBox) findViewById(R.id.checkBox);
        paymenttext = (TextView) findViewById(R.id.payment);

        RadioGroup termsgroup = (RadioGroup) findViewById(R.id.RadioGroup);
        RadioButton button1 = (RadioButton) findViewById(R.id.rb1);
        RadioButton button2 = (RadioButton) findViewById(R.id.rb2);
        RadioButton button3 = (RadioButton) findViewById(R.id.rb3);

        Button calculate = (Button)findViewById(R.id.calculatebtn);


        termsgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb1) {
                    loanterm = 7;
                } else if (checkedId == R.id.rb2) {
                    loanterm = 15;
                } else {
                    loanterm = 30;
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Got a new calculation result!";

                if(loantext.getText().toString().trim().length() == 0) {
                    loantext.setError("You have to set a amount!");
                    return;
                }
                if(interesttext.getText().toString().trim().length() == 0 || Double.parseDouble(interesttext.getText().toString()) > 20 || Double.parseDouble(interesttext.getText().toString()) < 0)  {
                    interesttext.setError("You have to set a interest rate between 0 to 20!");
                    return;
                }

                amount = Double.parseDouble(loantext.getText().toString());
                interestrate = Double.parseDouble(interesttext.getText().toString());
                tax = taxtext.isChecked();




                paymenttext.setText("$"+String.format("%.2f",calculateformular(amount, interestrate, loanterm, tax))+"");


                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public double calculateformular(double am, double ra, double tm, boolean tx) {

            double j = ra / 1200; //Monthly interest rate
            double n = tm * 12; //Number of months of loans
            double t = tx ? am / 1000 : 0; //Tax
            return am * j / (1 - Math.pow(1 + j, -n)) + t;
        }

    public boolean isEmpty(EditText mytext) {
        if (mytext.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }


}
