package com.example.aluno.utilidades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnPopulacao = (ImageButton) findViewById(R.id.btnPopulacao);
        ImageButton btnIMC = (ImageButton) findViewById(R.id.btnImc);
        ImageButton btnCalc = (ImageButton) findViewById(R.id.btnCalculadora);
        btnPopulacao.setOnClickListener(abrirActivity());
        btnIMC.setOnClickListener(abrirActivity());
        btnCalc.setOnClickListener(abrirActivity());
    }

    private View.OnClickListener abrirActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.btnPopulacao:
                        intent = new Intent(getContexto(), PopulacaoActivity.class);
                    case R.id.btnImc:
                        intent = new Intent(getContexto(), IMC_Activity.class);
                    case R.id.btnCalculadora:
                        intent = new Intent(getContexto(), CalculadoraActivity.class);
                }

                startActivity(intent);
            }
        };
    }
    private Context getContexto(){
        return this;
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
