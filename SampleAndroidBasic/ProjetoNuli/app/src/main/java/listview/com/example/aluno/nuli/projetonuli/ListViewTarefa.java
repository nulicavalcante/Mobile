package listview.com.example.aluno.nuli.projetonuli;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import listview.com.example.aluno.nuli.projetonuli.modelo.Categoria;
import listview.com.example.aluno.nuli.projetonuli.modelo.CategoriaDAO;
import listview.com.example.aluno.nuli.projetonuli.modelo.Tarefa;
import listview.com.example.aluno.nuli.projetonuli.modelo.TarefaDAO;

/**
 * Created by Ada on 26/11/2015.
 */
public class ListViewTarefa extends BaseAdapter {
    private List<Tarefa> tarefa;


    private Context contexto;

    public ListViewTarefa(Context contexto,Bundle extras,String lista) throws ParseException {
        super();
        this.contexto = contexto;
        TarefaDAO dao = new TarefaDAO(contexto);

        System.out.println("quase foi");
        if(lista.equals("ok"))
            tarefa = dao.selecionarTodasRealizadas(extras.getString("username"));
        if(lista.equals("notOk"))
           tarefa = dao.selecionarTodos(extras.getString("username"));
        if(lista.equals("todas"))
        tarefa = dao.selecionarTodos();
        System.out.println("criou a lista");
    }

    @Override
    public int getCount() { return tarefa.size(); }

    @Override
    public Object getItem(int position) { return tarefa.get(position);
    }

    @Override
    public long getItemId(int position) { return position;  }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.listview_tarefa,parent,false);

        Tarefa tarefas = (Tarefa) tarefa.get(position);
        TextView txtTarefa = (TextView) view.findViewById(R.id.txtTarefa);
        TextView txtDataLimite = (TextView) view.findViewById(R.id.txtDataLimite);
        TextView txtDataLembrete = (TextView) view.findViewById(R.id.txtDataLembrete);
        TextView txtDataRealizacao = (TextView) view.findViewById(R.id.txtDataRealizacao);
        CategoriaDAO dao = new CategoriaDAO(contexto);
        Categoria cat = dao.getCategoria(tarefas.getIdCategoria());
        txtTarefa.setText(cat.getNome() + ": " + tarefas.getDescricao().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("to aqui");

        if(tarefas.getDataLembrete()!=null&&!tarefas.getDataLembrete().toString().equals("")&&!tarefas.getDataLembrete().toString().equals("0")&&!tarefas.getDataLembrete().toString().isEmpty())
        txtDataLembrete.setText(txtDataLembrete.getText().toString()+sdf.format(tarefas.getDataLembrete()));

        if(tarefas.getDataLimite()!=null&&!tarefas.getDataLimite().toString().equals("")&&!tarefas.getDataLimite().toString().equals("0")&&!tarefas.getDataLimite().toString().isEmpty())
        txtDataLimite.setText(txtDataLimite.getText().toString() + sdf.format(tarefas.getDataLimite()));

        if(tarefas.getDataRealizacao()!=null&&!tarefas.getDataRealizacao().toString().equals("")&&!tarefas.getDataRealizacao().toString().equals("0")&&!tarefas.getDataRealizacao().toString().isEmpty())
        txtDataRealizacao.setText(txtDataRealizacao.getText().toString() +sdf.format(tarefas.getDataRealizacao()));

        return view;
    }
}
