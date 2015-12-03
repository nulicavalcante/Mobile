package listview.com.example.aluno.nuli.projetonuli.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aluno on 11/11/2015.
 */
public class CategoriaDAO {
    private DataBaseHelper helper;

    private SQLiteDatabase db;

    public CategoriaDAO(Context context){
        helper = new DataBaseHelper(context);
    }
    public long inserir(Categoria Categoria){
        //criar um "container" dos valores para cada campo da tabela
        ContentValues valores = new ContentValues();
        valores.put("nome",Categoria.getNome());


        //abrir o banco de dados para escrita
        db = helper.getWritableDatabase();
        //inserir o registro
        long rowID = db.insert("Categoria",null,valores);
        //fechar os objetos
        db.close();
        helper.close();

        return rowID;
    }
    public boolean update(String username, Categoria categoria){
        // se apenas um usuario utiliza aquela categoria atualiza true senão return false
      if(getCategoriaUsuario(username, categoria)) {
            ContentValues valores = new ContentValues();
            valores.put("nome", categoria.getNome());
            //abrir o banco de dados para escrita
            db = helper.getWritableDatabase();
            db.update("categoria", valores, "id = ?", new String[]{Integer.toString(categoria.getId())});
            db.close();
            helper.close();
            return true;
        }else
            return false;


    }
    public Integer delete(Categoria categoria){
        if(getCategoriaTarefa(categoria)) {
            db = helper.getWritableDatabase();
            Integer ROWID = db.delete("categoria", "id = ? ", new String[]{Integer.toString(categoria.getId())});
            db.close();
            helper.close();
            return ROWID;
        }else
        return -2;

    }
    public Categoria getCategoria(Integer id){
            Categoria categoria = new Categoria();
        categoria = null;
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();
        String whereClause = " id = ? ";
        String[] whereArgs = new String[]{
               String.valueOf(id)
        };
        //Obter os dados da tabela order by username
        Cursor cursor = db.query("categoria",null,whereClause,whereArgs,null,null,null);



        if(cursor.moveToFirst()){
            categoria = new Categoria();
                categoria.setId(cursor.getInt(cursor.getColumnIndex("id")));
                categoria.setNome(cursor.getString(cursor.getColumnIndex("nome")));


        }
        cursor.close();
        db.close();
        helper.close();

        return categoria;

    }
    public boolean getCategoriaUsuario(String username, Categoria categoria){
        boolean categoriaUsuario = true; //apenas um usuario usa a categoria
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();
        String whereClause = " username != ? AND idCategoria= ? ";
        String[] whereArgs = new String[]{
                username,
                String.valueOf(categoria.getId())
        };
        //Obter os dados da tabela order by username
        Cursor cursor = db.query("tarefa",null,whereClause,whereArgs,null,null,null);



        if(cursor.moveToFirst()){
            categoriaUsuario = false;
            //encontrou outros usuarios usando a categoria


        }
        cursor.close();
        db.close();
        helper.close();

        return categoriaUsuario;

    }
    public boolean getCategoriaTarefa(Categoria categoria){
        boolean categoriaTarefa = true; // categoria sem tarefas
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();
        String whereClause = " idCategoria= ? ";
        String[] whereArgs = new String[]{
                String.valueOf(categoria.getId())
        };
        //Obter os dados da tabela order by username
        Cursor cursor = db.query("tarefa",null,whereClause,whereArgs,null,null,null);



        if(cursor.moveToFirst()){
            categoriaTarefa = false;
            //encontrou tarefa cadastrada nessa categoria


        }
        cursor.close();
        db.close();
        helper.close();

        return categoriaTarefa;

    }

    public List<Categoria> selecionarTodos(){
        List<Categoria> lista = new ArrayList<Categoria>();
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();

        //Obter os dados da tabela
        Cursor cursor = db.query("Categoria",null,null,null,null,null,"nome");

        //Inserir dados do cursos na lista instanciada anteriormente
        if(cursor.moveToFirst()){
            do {
                Categoria Categoria = new Categoria();
                Categoria.setId(cursor.getInt(cursor.getColumnIndex("id")));
                Categoria.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                lista.add(Categoria);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        helper.close();

        return lista;

    }
}
