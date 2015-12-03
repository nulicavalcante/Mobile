package listview.com.example.aluno.nuli.projetonuli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import listview.com.example.aluno.nuli.projetonuli.modelo.Categoria;
import listview.com.example.aluno.nuli.projetonuli.modelo.CategoriaDAO;

/**
 * Created by aluno on 18/11/2015.
 */
public class ListViewCategoria  extends BaseAdapter {
    private List<Categoria> categoria;


    private Context contexto;

    public ListViewCategoria(Context contexto){
        super();
        this.contexto = contexto;
        CategoriaDAO dao = new CategoriaDAO(contexto);

        categoria = dao.selecionarTodos();


    }

    @Override
    public int getCount() {
        return categoria.size();
    }

    @Override
    public Object getItem(int position) {
        return categoria.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.listview,parent,false);

        Categoria categ = (Categoria) categoria.get(position);
        TextView txtCategoria = (TextView) view.findViewById(R.id.txtCategoria);
        txtCategoria.setText(categ.getNome());


        return view;
    }
}
