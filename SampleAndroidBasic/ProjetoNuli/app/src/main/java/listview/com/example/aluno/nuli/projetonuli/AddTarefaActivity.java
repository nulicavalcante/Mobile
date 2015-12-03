package listview.com.example.aluno.nuli.projetonuli;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import listview.com.example.aluno.nuli.projetonuli.modelo.Categoria;
import listview.com.example.aluno.nuli.projetonuli.modelo.Tarefa;
import listview.com.example.aluno.nuli.projetonuli.modelo.TarefaDAO;

public class AddTarefaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarefa);
        Spinner spCategoria = (Spinner) findViewById(R.id.spCategorias);
        spCategoria.setAdapter(new ListViewCategoria(this));
        Button btSalvar = (Button) findViewById(R.id.btSalvarTarefa);
        Bundle extras = getIntent().getExtras();


        btSalvar.setOnClickListener(inserirTarefa(extras,spCategoria));




    }
    private Context getContexto(){ return this;}
    private View.OnClickListener inserirTarefa(final Bundle extras, final Spinner spCategoria) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TarefaDAO dao = new TarefaDAO(getContexto());
                Tarefa tarefa = new Tarefa();
                EditText edtDesc = (EditText) findViewById(R.id.edtDescricao);
                EditText edtDtlimite = (EditText) findViewById(R.id.edtDataLimite);
                EditText edtDtLembrete = (EditText) findViewById(R.id.edtDataLembrete);

                tarefa.setUsername(extras.getString("username"));
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


                if (dao.inserir(tarefa) < 0)
                    Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL SALVAR A TAREFA", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getContexto(), "TAREFA SALVADA COM SUCESSO", Toast.LENGTH_LONG).show();

                }
            }
        };
    }
}
