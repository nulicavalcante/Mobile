package com.example.aluno.pesoideal;

import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button  btnCalcular = (Button) findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(calcularPesoIdeal() );
    }

    private View.OnClickListener calcularPesoIdeal() {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText edtAltura = (EditText) findViewById(R.id.edtAltura);
                double altura = Double.parseDouble(edtAltura.getText().toString());
                double pesoIdeal;

                RadioButton rbtFem = (RadioButton) findViewById(R.id.rbtFem);
                RadioButton rbtMas = (RadioButton) findViewById(R.id.rbtMas);
                if (rbtFem.isChecked())
                    pesoIdeal = (62.1 * altura) - 44.7;
                else
                    if(rbtMas.isChecked())
                        pesoIdeal = (72.7 * altura) - 58;
                    else
                        pesoIdeal = 0;
                TextView txtPesoIdeal = (TextView) findViewById(R.id.txtPesoIdeal);
                txtPesoIdeal.setText("Peso ideal = " + pesoIdeal);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
