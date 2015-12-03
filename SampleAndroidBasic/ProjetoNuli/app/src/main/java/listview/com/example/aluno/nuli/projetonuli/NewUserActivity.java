package listview.com.example.aluno.nuli.projetonuli;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import listview.com.example.aluno.nuli.projetonuli.modelo.Usuario;
import listview.com.example.aluno.nuli.projetonuli.modelo.UsuarioDAO;

public class NewUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        Button btCadastrar = (Button) findViewById(R.id.btCadastrar);
        btCadastrar.setOnClickListener(inserirUsuario());
    }
    private View.OnClickListener inserirUsuario() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Acessar os valores digitados pelo usuário
                EditText edtNome = (EditText) findViewById(R.id.edtNome);
                EditText edtUser = (EditText) findViewById(R.id.edtUsuarioNovo);
                EditText edtPwd = (EditText) findViewById(R.id.edtSenhaNova);
                RadioButton rdbF = (RadioButton) findViewById(R.id.rdbFem);
                RadioButton rdbM = (RadioButton) findViewById(R.id.rdbMasc);


                //Armazenar os valores em um objeto do tipo Usuario
                Usuario usuario = new Usuario();
                usuario.setNome(edtNome.getText().toString());
                usuario.setUsername(edtUser.getText().toString());
                usuario.setPassword(edtPwd.getText().toString());

                if(rdbF.isChecked())
                    usuario.setSexo('F');
                if(rdbM.isChecked())
                    usuario.setSexo('M');

                //Solicitar a inserção do registro por meio do DAO
                UsuarioDAO dao = new UsuarioDAO(getContexto());
                if (dao.inserir(usuario) < 0)
                    Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL INSERIR O USUARIO", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getContexto(), "USUARIO CADASTRADO", Toast.LENGTH_LONG).show();

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
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
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
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
