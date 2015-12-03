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
public class UsuarioDAO {

    private DataBaseHelper helper;

    private SQLiteDatabase db;

    public UsuarioDAO(Context context){
        helper = new DataBaseHelper(context);
    }
    public long inserir(Usuario Usuario){
        //criar um "container" dos valores para cada campo da tabela
        ContentValues valores = new ContentValues();
        valores.put("username",Usuario.getUsername());
        valores.put("password",Usuario.getPassword());
        valores.put("nome",Usuario.getNome());
        valores.put("sexo",String.valueOf(Usuario.getSexo()));
        


        //abrir o banco de dados para escrita
        db = helper.getWritableDatabase();
        //inserir o registro
        long rowID = db.insert("Usuario",null,valores);

        //fechar os objetos
        db.close();
        helper.close();

        return rowID;
    }

    public boolean update(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("password",usuario.getPassword());
        valores.put("nome",usuario.getNome());
        valores.put("sexo", String.valueOf(usuario.getSexo()));

        //abrir o banco de dados para escrita
        db = helper.getWritableDatabase();
        db.update("usuario",valores,"username = ?", new String[]{usuario.getUsername().toString()});
        db.close();
        helper.close();
        return true;

    }
    public Integer delete(Usuario usuario){
        db = helper.getWritableDatabase();
        Integer ROWID = db.delete("usuario","username = ? ",new String[]{usuario.getUsername().toString()});
        db.delete("tarefa","username = ? ",new String[]{usuario.getUsername().toString()});
        db.close();
        helper.close();
        return ROWID;
    }
    public Usuario getDados(String username){
        Usuario usuario;
        usuario= null;
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();

        String whereClause = "username = ? ";
        String[] whereArgs = new String[]{
                username
        };
        //Obter os dados da tabela order by username
        Cursor cursor = db.query("usuario",null,whereClause,whereArgs,null,null,null);

        //Inserir dados do cursos na lista instanciada anteriormente
        if(cursor.moveToFirst()){
            usuario = new Usuario();
            do {

                usuario.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                usuario.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                usuario.setSexo(cursor.getString(cursor.getColumnIndex("sexo")).charAt(0));




            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        helper.close();

        return usuario;

    }
    public List<Usuario> selecionarTodos(){
        List<Usuario> lista = new ArrayList<Usuario>();
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();

        //Obter os dados da tabela order by username
        Cursor cursor = db.query("Usuario",null,null,null,null,null,"username");

        //Inserir dados do cursos na lista instanciada anteriormente
        if(cursor.moveToFirst()){
            do {
                Usuario Usuario = new Usuario();
                Usuario.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                Usuario.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                Usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                Usuario.setSexo(cursor.getString(cursor.getColumnIndex("sexo")).charAt(0));


                lista.add(Usuario);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        helper.close();

        return lista;

    }

    public boolean validar(String username, String password){
            boolean userOK = false;
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();
        String whereClause = "username = ? AND password= ?";
        String[] whereArgs = new String[]{
                username,
                password
        };
        //Obter os dados da tabela order by username
        Cursor cursor = db.query("Usuario",null,whereClause,whereArgs,null,null,null);



        if(cursor.moveToFirst()){
                userOK = true;
                Usuario usuario = new Usuario();
               usuario.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                usuario.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                usuario.setSexo(cursor.getString(cursor.getColumnIndex("sexo")).charAt(0));


        }
        cursor.close();
        db.close();
        helper.close();

        return userOK;

    }
}
