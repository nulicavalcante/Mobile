package listview.com.example.aluno.nuli.projetonuli;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import listview.com.example.aluno.nuli.projetonuli.modelo.Usuario;
import listview.com.example.aluno.nuli.projetonuli.modelo.UsuarioDAO;

public class UpdateUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        EditText edtNome = (EditText) findViewById(R.id.edtNome);
        EditText edtPwd = (EditText) findViewById(R.id.edtSenhaNova);
        RadioButton rdbF = (RadioButton) findViewById(R.id.rdbFem);
        RadioButton rdbM = (RadioButton) findViewById(R.id.rdbMasc);
        Button btUpdate = (Button) findViewById(R.id.btAtualizar);
        Button btDelete = (Button) findViewById(R.id.btDeleteUser);
       Bundle extras = getIntent().getExtras();
        UsuarioDAO dao = new UsuarioDAO(getContexto());
        Usuario usuario = new Usuario();
        usuario = dao.getDados(extras.getString("username"));
        if (usuario!=null) {
                edtNome.setText(usuario.getNome());
                edtPwd.setText(usuario.getPassword());
                if(usuario.getSexo()=='F')
                    rdbF.setChecked(true);
                else
                    rdbF.setChecked(false);
                if(usuario.getSexo()=='M')
                    rdbM.setChecked(true);
                 else
                    rdbM.setChecked(false);


        }else {
            Toast.makeText(getContexto(), "USUARIO " + extras.getString("username").toString() + " NÃO CADASTRADO", Toast.LENGTH_LONG).show();
            finish();
        }

        btUpdate.setOnClickListener(updateUser(usuario, edtNome, edtPwd, rdbF, rdbM));
        btDelete.setOnClickListener(deleteUser(usuario));
    }

    private View.OnClickListener deleteUser(final Usuario usuario) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContexto());
                alertDialogBuilder.setMessage("Deseja excluir este usuario?");

                alertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        UsuarioDAO dao = new UsuarioDAO(getContexto());
                        if (dao.delete(usuario) < 0)
                            Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL DELETAR O USUARIO", Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(getContexto(), "USUÁRIO EXCLUÍDO", Toast.LENGTH_LONG).show();

                        }
                        Toast.makeText(getContexto(),"USUÁRIO EXCLUIDO",Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("Não",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContexto(),"Ação cancelada",Toast.LENGTH_LONG).show();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        };
    }

    private View.OnClickListener updateUser(final Usuario usuario, final EditText edtNome, final EditText edtPwd, final RadioButton rdbF, final RadioButton rdbM) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNome(edtNome.getText().toString());
                usuario.setPassword(edtPwd.getText().toString());

                if(rdbF.isChecked())
                    usuario.setSexo('F');
                if(rdbM.isChecked())
                    usuario.setSexo('M');
                UsuarioDAO dao = new UsuarioDAO(getContexto());
                if(dao.update(usuario)) {
                    Toast.makeText(getContexto(), "USUÁRIO ATUALIZADO", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL ATUALIZAR O USUARIO", Toast.LENGTH_LONG).show();

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
        getMenuInflater().inflate(R.menu.menu_update_user, menu);
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
