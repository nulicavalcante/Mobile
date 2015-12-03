package listview.com.example.aluno.nuli.projetonuli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import listview.com.example.aluno.nuli.projetonuli.modelo.Categoria;
import listview.com.example.aluno.nuli.projetonuli.modelo.Usuario;
import listview.com.example.aluno.nuli.projetonuli.modelo.UsuarioDAO;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText edtUser = (EditText) findViewById(R.id.edtUsuario);
        EditText edtSenha = (EditText) findViewById(R.id.edtSenha);
        Button btAcessar = (Button) findViewById(R.id.btAcessar);
        Button btNew = (Button) findViewById(R.id.btNovoUser);
        Button btUpdateUser = (Button) findViewById(R.id.btUpdateUser);

        btAcessar.setOnClickListener(abrirActivity(edtUser,edtSenha));
        btNew.setOnClickListener(abrirActivity(edtUser,edtSenha));
        btUpdateUser.setOnClickListener(abrirActivity(edtUser,edtSenha));
    }
    private View.OnClickListener abrirActivity(final EditText edtUser, final EditText edtSenha) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.btAcessar:
                        if(validarAcesso(edtUser,edtSenha)) {
                            Toast.makeText(getContexto(), "BEM-VINDO", Toast.LENGTH_LONG).show();

                            intent = new Intent(getContexto(), CategoriaActivity.class);
                            intent.putExtra("username", edtUser.getText().toString());
                            startActivity(intent);
                        } else {
                            intent = new Intent(getContexto(), MainActivity.class);
                            Toast.makeText(getContexto(), "USUARIO NÃO CADASTRADO", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.btNovoUser:
                        intent = new Intent(getContexto(), NewUserActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.btUpdateUser:
                        if(edtUser.getText().toString().isEmpty())
                            Toast.makeText(getContexto(), "DIGITE O NOME DO USUÁRIO", Toast.LENGTH_LONG).show();
                        else {
                            intent = new Intent(getContexto(), UpdateUserActivity.class);
                            intent.putExtra("username", edtUser.getText().toString());
                            startActivity(intent);
                        }
                        break;
                }


            }
        };
    }
    private boolean validarAcesso(EditText edtUser, EditText edtPwd){
        boolean userOK = false;

        //Solicitar o select do registro por meio do DAO
        UsuarioDAO dao = new UsuarioDAO(getContexto());
        if (dao.validar(edtUser.getText().toString(),edtPwd.getText().toString()))
            userOK = true;
        else
            userOK = false;

       return userOK;

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
