package com.example.aluno.utilidades;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

public class PopulacaoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populacao);
        Button btnEstimar = (Button) findViewById(R.id.btnEstimar);

        btnEstimar.setOnClickListener(estimarPopulacao());
    }

    private View.OnClickListener estimarPopulacao() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar data = Calendar.getInstance();
                int ano = data.get(Calendar.YEAR);

                EditText edtPopulacao = (EditText) findViewById(R.id.edtPopulacao);
                EditText edtIndice = (EditText) findViewById(R.id.edtIndice);
                EditText edtAnos = (EditText) findViewById(R.id.edtAnos);
                long populacao = Integer.parseInt(edtPopulacao.getText().toString());
                double indice = Double.parseDouble(edtIndice.getText().toString());
                int anos = Integer.parseInt(edtAnos.getText().toString());
                long crescimento = 0;

                TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutDados);
                tableLayout.removeAllViews();
                for (int i=1; i<= anos; i++){
                    TableRow row = new TableRow(getContexto());
                    TextView txtAnos = new TextView(getContexto());
                    txtAnos.setText(String.valueOf(ano));
                    row.addView(txtAnos);

                    TextView txtPopulacao = new TextView(getContexto());
                    txtPopulacao.setText(String.valueOf(populacao));
                    row.addView(txtPopulacao);

                    TextView txtCrescimento = new TextView(getContexto());
                    if(i==1)
                        txtCrescimento.setText("-");
                    else
                        txtCrescimento.setText(String.valueOf(crescimento));

                    row.addView(txtCrescimento);
                        crescimento = Math.round(populacao * indice / 100);

                        populacao+= crescimento;
                    ano++;

                    tableLayout.addView(row);
                }
            }
        };
    }
    private Context getContexto(){
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_populacao, menu);
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
