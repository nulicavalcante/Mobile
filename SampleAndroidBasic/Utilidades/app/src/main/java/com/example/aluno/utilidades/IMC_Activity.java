package com.example.aluno.utilidades;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IMC_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
        Button btnCalcular = (Button) findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(calcularIMC());
    }

    private View.OnClickListener calcularIMC() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtAltura = (EditText) findViewById(R.id.edtAltura);
                EditText edtPeso = (EditText) findViewById(R.id.edtPeso);
                TextView txtResultado = (TextView) findViewById(R.id.txtResultado);
                TextView txtResult2 = (TextView) findViewById(R.id.txtResultado2);

                double imc = Double.parseDouble(edtPeso.getText().toString())/Math.pow(Double.parseDouble(edtAltura.getText().toString()), 2);
                txtResultado.setText(String.valueOf(imc));
                if(imc<17)
                    txtResult2.setText("Muito abaixo do peso");
                else if(imc<18.49)
                     txtResult2.setText("Abaixo do peso");
                else if (imc<24.99)
                    txtResult2.setText("Peso normal");
                else if (imc<29.99)
                    txtResult2.setText("Acima do peso");
                else if(imc<34.99)
                    txtResult2.setText("Obesidade I");
                else if(imc<39.99)
                    txtResult2.setText("Obesidade II (severa)");
                else
                    txtResult2.setText("Obesidade III (mórbida)");

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_imc, menu);
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
