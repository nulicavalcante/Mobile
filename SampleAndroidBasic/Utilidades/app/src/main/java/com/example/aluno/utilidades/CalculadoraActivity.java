package com.example.aluno.utilidades;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculadoraActivity extends Activity {
    private int conta=0;
    private int visor=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);
        Button btn4 = (Button) findViewById(R.id.button4);
        Button btn5 = (Button) findViewById(R.id.button5);
        Button btn6 = (Button) findViewById(R.id.button6);
        Button btn7 = (Button) findViewById(R.id.button7);
        Button btn8 = (Button) findViewById(R.id.button8);
        Button btn9 = (Button) findViewById(R.id.button9);
        Button btn0 = (Button) findViewById(R.id.button0);
        Button btnPlus = (Button) findViewById(R.id.btPlus);
        Button btnIgual = (Button) findViewById(R.id.btIgual);
        Button btnClear = (Button) findViewById(R.id.buttonC);
        Button btnLess = (Button) findViewById(R.id.btLess);
        Button btnDot = (Button) findViewById(R.id.btDot);
        Button btnBar = (Button) findViewById(R.id.btBar);

        btn1.setOnClickListener(exibirVisor());
        btn2.setOnClickListener(exibirVisor());
        btn3.setOnClickListener(exibirVisor());
        btn4.setOnClickListener(exibirVisor());
        btn5.setOnClickListener(exibirVisor());
        btn6.setOnClickListener(exibirVisor());
        btn7.setOnClickListener(exibirVisor());
        btn8.setOnClickListener(exibirVisor());
        btn9.setOnClickListener(exibirVisor());
        btn0.setOnClickListener(exibirVisor());
        btnClear.setOnClickListener(exibirVisor());

        btnPlus.setOnClickListener(setConta());
        btnLess.setOnClickListener(setConta());
        btnDot.setOnClickListener(setConta());
        btnBar.setOnClickListener(setConta());

        btnIgual.setOnClickListener(calcular());

    }


    private View.OnClickListener exibirVisor(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = 0;
                switch (v.getId()){
                    case R.id.button1:
                        num =1;
                        break;
                    case R.id.button2:
                        num =2;
                        break;
                    case R.id.button3:
                        num=3;
                        break;
                    case R.id.button4:
                        num=4;
                        break;
                    case R.id.button5:
                        num=5;
                        break;
                    case R.id.button6:
                        num=6;
                        break;
                    case R.id.button7:
                        num=7;
                        break;
                    case R.id.button8:
                        num=8;
                        break;
                    case R.id.button9:
                        num=9;
                        break;
                    case R.id.button0:
                        num=0;
                        break;
                    case R.id.buttonC:
                        num=0;
                        visor=0;
                        conta=0;
                        break;

                }
                TextView txtVisor = (TextView) findViewById(R.id.txtVisor);
                txtVisor.setText(String.valueOf(num));

            }
        } ;
    }
    private View.OnClickListener setConta(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btPlus:
                        conta = 1;
                        break;
                    case R.id.btLess:
                        conta = 2;
                        break;
                    case R.id.btDot:
                        conta = 3;
                        break;
                    case R.id.btBar:
                        conta = 4;
                        break;
                }

                TextView txtVisor = (TextView) findViewById(R.id.txtVisor);
                visor = Integer.parseInt(txtVisor.getText().toString());

            }
        };
    }

    private View.OnClickListener calcular(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtVisor = (TextView) findViewById(R.id.txtVisor);
                int resultado = 0;
                switch(conta) {
                    case 1:   resultado = visor + Integer.parseInt(txtVisor.getText().toString());
                        break;
                    case 2:    resultado = visor - Integer.parseInt(txtVisor.getText().toString());
                        break;
                    case 3:    resultado = visor * Integer.parseInt(txtVisor.getText().toString());
                        break;
                    case 4:    if(0 != Integer.parseInt(txtVisor.getText().toString()))
                        resultado = visor / Integer.parseInt(txtVisor.getText().toString());
                    else
                    {
                        Toast.makeText(getBaseContext(),"NÃO É POSSÍVEL DIVIDIR POR ZERO", Toast.LENGTH_LONG).show();

                    }
                        break;

                }
                txtVisor.setText(String.valueOf(resultado));
                visor = resultado;

            }
        };
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculadora, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
