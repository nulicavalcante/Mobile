package listview.com.example.aluno.nuli.projetonuli;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import listview.com.example.aluno.nuli.projetonuli.modelo.Categoria;
import listview.com.example.aluno.nuli.projetonuli.modelo.CategoriaDAO;
import listview.com.example.aluno.nuli.projetonuli.modelo.Usuario;
import listview.com.example.aluno.nuli.projetonuli.modelo.UsuarioDAO;

public class CategoriaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Bundle extras = getIntent().getExtras();
        ListView listView = listarCategoria();
        listView.setOnItemClickListener(excluirCategoria(extras.getString("username")));
        Button btNovaCateg = (Button) findViewById(R.id.btNovaCateg);

        btNovaCateg.setOnClickListener(inserirCategoria());


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private View.OnClickListener inserirCategoria() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContexto());
                View editView = LayoutInflater.from(getContexto()).inflate(R.layout.activity_add_categ,null,false);
                final EditText editNomeCateg = (EditText) editView.findViewById(R.id.edtNomeCategoria);
                alertDialogBuilder.setView(editView);
                alertDialogBuilder.setMessage("Nova Categoria");

                alertDialogBuilder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        CategoriaDAO dao = new CategoriaDAO(getContexto());
                        Categoria categoria = new Categoria();
                        categoria.setNome(editNomeCateg.getText().toString());

                        if (dao.inserir(categoria) < 0)
                            Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL SALVAR A CATEGORIA", Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(getContexto(), "CATEGORIA SALVADA COM SUCESSO", Toast.LENGTH_LONG).show();
                            listarCategoria();
                        }

                    }
                });

                alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getContexto(), "Ação cancelada", Toast.LENGTH_LONG).show();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        };
    }

    private AdapterView.OnItemClickListener excluirCategoria(final String username) {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContexto());
                View editView = LayoutInflater.from(getContexto()).inflate(R.layout.activity_add_categ,null,false);
                final EditText editNomeCateg = (EditText) editView.findViewById(R.id.edtNomeCategoria);

                alertDialogBuilder.setView(editView);

                alertDialogBuilder.setMessage("Deseja excluir esta categoria?");

                alertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        CategoriaDAO dao = new CategoriaDAO(getContexto());
                        Categoria categoria = (Categoria) parent.getAdapter().getItem(position);
                            Integer ROWID = dao.delete(categoria);
                        if ( ROWID < 0)
                            Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL DELETAR A CATEGORIA", Toast.LENGTH_LONG).show();
                        if(ROWID==-2)
                            Toast.makeText(getContexto(), "CATEGORIA POSSUI TAREFAS CADASTRADAS", Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(getContexto(), "CATEGORIA EXCLUÍDA", Toast.LENGTH_LONG).show();
                            listarCategoria();
                        }

                    }
                });

                alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getContexto(), "Ação cancelada", Toast.LENGTH_LONG).show();

                    }
                });
                alertDialogBuilder.setNeutralButton("Alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CategoriaDAO dao = new CategoriaDAO(getContexto());
                        Categoria categoria = (Categoria) parent.getAdapter().getItem(position);
                        categoria.setNome(editNomeCateg.getText().toString());
                        if (dao.update(username, categoria)) {
                            Toast.makeText(getContexto(), "CATEGORIA ALTERADA COM SUCESSO", Toast.LENGTH_LONG).show();
                            listarCategoria();
                        } else
                            Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL ALTERAR A CATEGORIA", Toast.LENGTH_LONG).show();


                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        };
    }

    private ListView listarCategoria(){
        ListView listView = (ListView) findViewById(R.id.lvCategoria);
        listView.setAdapter(new ListViewCategoria(this));
        return listView;
    }
    private Context getContexto(){
        return this;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categoria, menu);
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
        if(id == R.id.tarefas){
            Intent intent = new Intent(getContexto(), TarefaActivity.class);
            Bundle extras = getIntent().getExtras();
            intent.putExtras(extras);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
