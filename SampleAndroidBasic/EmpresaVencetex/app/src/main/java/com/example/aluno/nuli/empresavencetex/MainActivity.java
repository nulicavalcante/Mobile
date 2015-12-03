package com.example.aluno.nuli.empresavencetex;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluno.nuli.empresavencetex.modelo.Cliente;
import com.example.aluno.nuli.empresavencetex.modelo.ClienteDAO;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(inserirCliente());
        listarClientes();
    }
private void listarClientes(){
    ClienteDAO dao = new ClienteDAO(this);

    List<Cliente> lista = dao.selecionarTodos();
    TextView txtListagem = (TextView) findViewById(R.id.txtListagem);
    txtListagem.setText("");

    for(Cliente c: lista){
        txtListagem.setText(txtListagem.getText() + c.getNomeFantasia() + " | " + c.getTelefone() + "\n");


    }
}
    private View.OnClickListener inserirCliente() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Acessar os valores digitados pelo usuário
                EditText edtCnpj = (EditText) findViewById(R.id.edtCNPJ);
                EditText edtNomeFantasia = (EditText) findViewById(R.id.edtNomeFantasia);
                EditText edtTelefone = (EditText) findViewById(R.id.edtTelefone);

                //Armazenar os valores em um objeto do tipo Cliente
                Cliente cliente = new Cliente();
                cliente.setCnpj(Long.parseLong( edtCnpj.getText().toString() ));
                cliente.setNomeFantasia(edtNomeFantasia.getText().toString());
                cliente.setTelefone( edtTelefone.getText().toString());

                //Solicitar a inserção do registro por meio do DAO
                ClienteDAO dao = new ClienteDAO(getContexto());
                if (dao.inserir(cliente) < 0)
                    Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL INSERIR O CLIENTE", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getContexto(), "CLIENTE CADASTRADO", Toast.LENGTH_LONG).show();
                    listarClientes();
                }

            }


        };
    }
    private Context getContexto() { return this; }
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
