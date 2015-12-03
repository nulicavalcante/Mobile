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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import listview.com.example.aluno.nuli.projetonuli.modelo.Tarefa;
import listview.com.example.aluno.nuli.projetonuli.modelo.TarefaDAO;

public class TarefaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);
        ListView listView = atualizarListView("notOk");


        listView.setOnItemClickListener(excluirTarefa());
    }

    @Override
    protected void onStart() {
        super.onStart();
        atualizarListView("notOk");
    }
    private ListView atualizarListView(String lista){
        ListView listView = (ListView) findViewById(R.id.listTarefas);
        Bundle extras = getIntent().getExtras();
        try {
            listView.setAdapter(new ListViewTarefa(this,extras,lista));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listView;
    }

    private AdapterView.OnItemClickListener excluirTarefa() {
        return new AdapterView.OnItemClickListener() {
            final CharSequence[] items = {"Editar","Data Realizada","Excluir"};
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContexto());
                alertDialog.setItems(items,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Tarefa tarefa = (Tarefa) parent.getAdapter().getItem(position);
                                switch (which) {
                                    case 0:
                                        if (tarefa.getDataRealizacao() != null)
                                            Toast.makeText(getContexto(), "TAREFA REALIZADA, NÃO FOI POSSÍVEL ALTERAR", Toast.LENGTH_LONG).show();

                                        else {
                                            Intent intentEditarTarefa = new Intent(getContexto(), EditarTarefaActivity.class);
                                            Bundle extras = getIntent().getExtras();
                                            extras.putInt("tarefa", tarefa.getId());
                                            intentEditarTarefa.putExtras(extras);

                                            startActivity(intentEditarTarefa);
                                        }

                                        break;
                                case 1:
                                        alertaDataRealizacao(parent, position);
                                        break;
                                case 2:
                                        TarefaDAO dao = new TarefaDAO(getContexto());
                                        if (dao.delete(tarefa) < 0)
                                            Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL EXCLUIR A TAREFA", Toast.LENGTH_LONG).show();
                                        else {
                                            Toast.makeText(getContexto(), "TAREFA EXCLUÍDA COM SUCESSO", Toast.LENGTH_LONG).show();
                                            atualizarListView("notOk");
                                        }
                                        break;
                            }
                        }
            });
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        };
    }

    public void alertaDataRealizacao(final AdapterView<?> parent,final int position){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContexto());
        View editView = LayoutInflater.from(getContexto()).inflate(R.layout.alert_tarefa_ok,null,false);
        final EditText edtDataOK = (EditText) editView.findViewById(R.id.edtUpDataRealizada);
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        alertDialogBuilder.setView(editView);

        alertDialogBuilder.setMessage("Tarefa já foi realizada?");

        alertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edtDataOK.getText().toString().isEmpty())
                    Toast.makeText(getContexto(), "DIGITE A DATA DE REALIZAÇÃO", Toast.LENGTH_LONG).show();
                else {
                    TarefaDAO dao = new TarefaDAO(getContexto());
                    Tarefa tarefa = (Tarefa) parent.getAdapter().getItem(position);
                    try {
                        tarefa.setDataRealizacao(sdf.parse(edtDataOK.getText().toString()));
                        tarefa.setStatus("R");
                        if (dao.update(tarefa)) {
                            Toast.makeText(getContexto(), "DATA REALIZAÇÃO ALTERADA COM SUCESSO", Toast.LENGTH_LONG).show();
                            atualizarListView("notOk");
                        } else
                            Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL ALTERAR A DATA DA REALIZAÇÃO", Toast.LENGTH_LONG).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContexto(), "AÇÃO CANCELADA", Toast.LENGTH_LONG).show();
            }
        });

        alertDialogBuilder.setNeutralButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TarefaDAO dao = new TarefaDAO(getContexto());
                Tarefa tarefa = (Tarefa) parent.getAdapter().getItem(position);

                if (dao.delete(tarefa) < 0)
                    Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL EXCLUIR A TAREFA", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getContexto(), "TAREFA EXCLUÍDA COM SUCESSO", Toast.LENGTH_LONG).show();
                    atualizarListView("notOk");
                }
            }
        });
                AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tarefa, menu);
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
        if(id == R.id.add_tarefa){
            Intent intent = new Intent(getContexto(), AddTarefaActivity.class);
            Bundle extras = getIntent().getExtras();
            intent.putExtras(extras);
            startActivity(intent);
        }
        if(id == R.id.tarefa_ok){
            atualizarListView("ok");
        }
        if(id == R.id.tarefa_not){
            atualizarListView("notOk");
        }
        if(id==R.id.tarefa_todas){
            atualizarListView("todas");
        }



        return super.onOptionsItemSelected(item);
    }

    private Context getContexto() { return this;   }
}
