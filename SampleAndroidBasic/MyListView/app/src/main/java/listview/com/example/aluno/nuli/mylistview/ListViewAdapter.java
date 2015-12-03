package listview.com.example.aluno.nuli.mylistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aluno on 11/11/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    private List<Cliente> clientes;


    private Context contexto;

    public ListViewAdapter (Context contexto){
        super();
        this.contexto = contexto;

        clientes = new ArrayList<Cliente>();

        clientes.add(new Cliente(10,"Joaquim Nabuco", "2333-4444"));
        clientes.add(new Cliente(20,"Washington Luiz", "6666-7777"));
        clientes.add(new Cliente(33,"Bernardino de Campos", "4545-3232"));
        clientes.add(new Cliente(57,"Floriano Peixoto", "8523-4236"));
        clientes.add(new Cliente(62,"Castro Alves", "7854-4444"));
        clientes.add(new Cliente(71,"Luiz Pereira Barreto", "2121-4343"));
        clientes.add(new Cliente(72,"Rubem Alves", "3333-3334"));
        clientes.add(new Cliente(77,"Camilo Castelo Branco", "7777-8899"));
        clientes.add(new Cliente(88,"Jozineide Farias Lima", "5287-7814"));
        clientes.add(new Cliente(89,"Orlando Vasconcelos Lins", "2158-6562"));
        clientes.add(new Cliente(96,"Maria Therodora Albuquerque", "7895-2563"));

    }

    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.listview,parent,false);

        Cliente cliente = (Cliente) clientes.get(position);
        TextView txtCliente = (TextView) view.findViewById(R.id.txtCliente);
        txtCliente.setText(cliente.getNome());

        TextView txtTelefone = (TextView) view.findViewById(R.id.txtTelefone);
        txtTelefone.setText(cliente.getTelefone());

        return view;
    }
}
