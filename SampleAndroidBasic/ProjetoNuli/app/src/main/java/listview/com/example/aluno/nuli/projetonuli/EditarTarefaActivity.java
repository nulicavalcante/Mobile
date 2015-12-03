package listview.com.example.aluno.nuli.projetonuli;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import listview.com.example.aluno.nuli.projetonuli.modelo.Categoria;
import listview.com.example.aluno.nuli.projetonuli.modelo.Tarefa;
import listview.com.example.aluno.nuli.projetonuli.modelo.TarefaDAO;

public class EditarTarefaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarefa);
        Spinner spCategoria = (Spinner) findViewById(R.id.spCategorias);
        spCategoria.setAdapter(new ListViewCategoria(this));
        EditText edtDesc = (EditText) findViewById(R.id.edtDescricao);
        EditText edtDtlimite = (EditText) findViewById(R.id.edtDataLimite);
        EditText edtDtLembrete = (EditText) findViewById(R.id.edtDataLembrete);

        Button btEditar = (Button) findViewById(R.id.btEditarTarefa);
        Bundle extras = getIntent().getExtras();

        TarefaDAO dao = new TarefaDAO(getContexto());
        Tarefa tarefa = new Tarefa();
        try {
            tarefa = dao.getTarefa(extras.getInt("tarefa"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        edtDesc.setText(tarefa.getDescricao());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        edtDtlimite.setText(sdf.format(tarefa.getDataLimite()));
        edtDtLembrete.setText(sdf.format(tarefa.getDataLembrete()));

        ListViewCategoria adapter = new ListViewCategoria(this);

        for(int position = 0; position <adapter.getCount();position++)
        {
            Categoria categoria = (Categoria) adapter.getItem(position);
            if(categoria.getId()==tarefa.getIdCategoria()) {
                spCategoria.setSelection(position);
                break;
            }

        }

        btEditar.setOnClickListener(alterarTarefa(tarefa,spCategoria));




    }

    private Context getContexto() { return this;  }
    private View.OnClickListener alterarTarefa(final Tarefa tarefa, final Spinner spCategoria) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TarefaDAO dao = new TarefaDAO(getContexto());

                EditText edtDesc = (EditText) findViewById(R.id.edtDescricao);
                EditText edtDtlimite = (EditText) findViewById(R.id.edtDataLimite);
                EditText edtDtLembrete = (EditText) findViewById(R.id.edtDataLembrete);

                tarefa.setDescricao(edtDesc.getText().toString());

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    tarefa.setDataLembrete(sdf.parse(edtDtLembrete.getText().toString()));
                    tarefa.setDataLimite(sdf.parse(edtDtlimite.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Categoria categoria = (Categoria) spCategoria.getSelectedItem();
                tarefa.setIdCategoria(categoria.getId());


                if (dao.update(tarefa))
                    Toast.makeText(getContexto(), "TAREFA ALTERADA COM SUCESSO", Toast.LENGTH_LONG).show();
                else {

                    Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL ALTERAR A TAREFA", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

}
