package listview.com.example.aluno.nuli.projetonuli.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aluno on 11/11/2015.
 */
public class TarefaDAO {

    private DataBaseHelper helper;

    private SQLiteDatabase db;

    public TarefaDAO(Context context){
        helper = new DataBaseHelper(context);
    }

    public Tarefa getTarefa(Integer id) throws ParseException {
        Tarefa Tarefa = new Tarefa();
        Tarefa = null;
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();
        String whereClause = " id = ?";
        String[] whereArgs = new String[]{
                String.valueOf(id)
        };
        //Obter os dados da tabela
        Cursor cursor = db.query("tarefa", null, whereClause,whereArgs, null, null, null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String auxData;
        //Inserir dados do cursos na lista instanciada anteriormente
        if(cursor.moveToFirst()){
            Tarefa = new Tarefa();
            do {

                Tarefa.setId(cursor.getInt(cursor.getColumnIndex("id")));
                Tarefa.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                auxData = String.valueOf(cursor.getInt(cursor.getColumnIndex("dataLimite")));
                System.out.println(auxData + auxData.length());
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")){
                    if(auxData.length()==7){
                        auxData="0"+auxData;
                    }
                    auxData = auxData.subSequence(0,2).toString().concat("/") + auxData.subSequence(2,4).toString().concat("/") + auxData.subSequence(4,8).toString();
                    System.out.println(auxData + " teste");
                    Tarefa.setDataLimite(sdf.parse(auxData));
                }
                System.out.println("passou");
                auxData = "";
                auxData = String.valueOf(cursor.getLong(cursor.getColumnIndex("dataLembrete")));
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")) {
                    if(auxData.length()==7){
                        auxData="0"+auxData;
                    }
                    auxData = auxData.subSequence(0, 2).toString().concat("/") + auxData.subSequence(2, 4).toString().concat("/") + auxData.subSequence(4, 8).toString();
                    Tarefa.setDataLembrete(sdf.parse(auxData));
                }
                auxData = "";
                auxData = String.valueOf(cursor.getLong(cursor.getColumnIndex("dataRealizacao")));
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")) {
                    if(auxData.length()==7){
                        auxData="0"+auxData;
                    }
                    auxData = auxData.subSequence(0, 2).toString().concat("/") + auxData.subSequence(2, 4).toString().concat("/") + auxData.subSequence(4, 8).toString();
                    Tarefa.setDataRealizacao(sdf.parse(auxData));
                }
                auxData= "";
                Tarefa.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                Tarefa.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                Tarefa.setIdCategoria(cursor.getInt(cursor.getColumnIndex("idCategoria")));


            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        helper.close();

        return Tarefa;

    }

    public long inserir(Tarefa Tarefa){
        //criar um "container" dos valores para cada campo da tabela
        ContentValues valores = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        valores.put("descricao",Tarefa.getDescricao());
        valores.put("dataLimite", sdf.format(Tarefa.getDataLimite()).replace("/", ""));
        valores.put("dataLembrete",sdf.format(Tarefa.getDataLembrete()).replace("/", ""));
        Tarefa.setStatus("P");
        valores.put("status",Tarefa.getStatus());
        valores.put("username",Tarefa.getUsername());
        valores.put("idCategoria",String.valueOf(Tarefa.getIdCategoria()));



        //abrir o banco de dados para escrita
        db = helper.getWritableDatabase();
        //inserir o registro
        long rowID = db.insert("tarefa",null,valores);
        //fechar os objetos
        db.close();
        helper.close();

        return rowID;
    }
    public boolean update(Tarefa tarefa){
        //se a tarefa não foi realizada atualizar true senão return false
        if(this.checarTarefaRealizada(tarefa)) {
            ContentValues valores = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            valores.put("descricao",tarefa.getDescricao());
            valores.put("dataLimite", sdf.format(tarefa.getDataLimite()).replace("/","") );
            valores.put("dataLembrete", sdf.format(tarefa.getDataLembrete()).replace("/", ""));
            if(tarefa.getDataRealizacao()!=null)
            valores.put("dataRealizacao",sdf.format(tarefa.getDataRealizacao()).replace("/",""));
            valores.put("status",tarefa.getStatus());
            valores.put("username",tarefa.getUsername());
            valores.put("idCategoria",String.valueOf(tarefa.getIdCategoria()));

            //abrir o banco de dados para escrita
            db = helper.getWritableDatabase();
            db.update("tarefa", valores, "id = ?", new String[]{Integer.toString(tarefa.getId())});
            db.close();
            helper.close();
            return true;
        }else
            return false;

    }
    public Integer delete(Tarefa tarefa){
        db = helper.getWritableDatabase();
        Integer ROWID =  db.delete("tarefa","id = ? ",new String[]{Integer.toString(tarefa.getId())});
        db.close();
        helper.close();
        return ROWID;
    }

    public boolean checarTarefaRealizada(Tarefa tarefa){
        boolean tarefaRealizada = false; //tarefa já foi realizada
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();
        String whereClause = " (dataRealizacao = 0 OR dataRealizacao IS NULL) AND id= ? ";
        String[] whereArgs = new String[]{
                String.valueOf(tarefa.getId())
        };
        //Obter os dados da tabela order by username
        Cursor cursor = db.query("tarefa",null,whereClause,whereArgs,null,null,null);

        //Inserir dados do cursos na lista instanciada anteriormente

        if(cursor.moveToFirst()){
            tarefaRealizada = true;
            //tarefa não foi realizada

        }
        cursor.close();
        db.close();
        helper.close();

        return tarefaRealizada;

    }

    public List<Tarefa> selecionarTodos(String username) throws ParseException {
        List<Tarefa> lista = new ArrayList<Tarefa>();
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();
        String whereClause = " username = ? AND (dataRealizacao = 0 OR dataRealizacao IS NULL)";
        String[] whereArgs = new String[]{
                username
        };
        //Obter os dados da tabela
        Cursor cursor = db.query("tarefa", null, whereClause,whereArgs, null, null, "dataLembrete");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String auxData;
        //Inserir dados do cursos na lista instanciada anteriormente
        if(cursor.moveToFirst()){
            do {
                Tarefa Tarefa = new Tarefa();
                Tarefa.setId(cursor.getInt(cursor.getColumnIndex("id")));
                Tarefa.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                auxData = String.valueOf(cursor.getInt(cursor.getColumnIndex("dataLimite")));
                System.out.println(auxData + auxData.length());
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")){
                        if(auxData.length()==7){
                            auxData="0"+auxData;
                        }
                    auxData = auxData.subSequence(0,2).toString().concat("/") + auxData.subSequence(2,4).toString().concat("/") + auxData.subSequence(4,8).toString();
                    System.out.println(auxData + " teste");
                    Tarefa.setDataLimite(sdf.parse(auxData));
                }
                System.out.println("passou");
                auxData = "";
                auxData = String.valueOf(cursor.getLong(cursor.getColumnIndex("dataLembrete")));
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")) {
                    if(auxData.length()==7){
                        auxData="0"+auxData;
                    }
                    auxData = auxData.subSequence(0, 2).toString().concat("/") + auxData.subSequence(2, 4).toString().concat("/") + auxData.subSequence(4, 8).toString();
                    Tarefa.setDataLembrete(sdf.parse(auxData));
                }
                auxData = "";
                auxData = String.valueOf(cursor.getLong(cursor.getColumnIndex("dataRealizacao")));
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")) {
                    if(auxData.length()==7){
                        auxData="0"+auxData;
                    }
                    auxData = auxData.subSequence(0, 2).toString().concat("/") + auxData.subSequence(2, 4).toString().concat("/") + auxData.subSequence(4, 8).toString();
                    Tarefa.setDataRealizacao(sdf.parse(auxData));
                }
                auxData= "";
                Tarefa.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                Tarefa.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                Tarefa.setIdCategoria(cursor.getInt(cursor.getColumnIndex("idCategoria")));
                lista.add(Tarefa);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        helper.close();

        return lista;

    }
    public List<Tarefa> selecionarTodasRealizadas(String username) throws ParseException {
        List<Tarefa> lista = new ArrayList<Tarefa>();
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();
        String whereClause = " username = ? AND (dataRealizacao != 0 OR dataRealizacao IS NOT NULL)";
        String[] whereArgs = new String[]{
                username
        };
        //Obter os dados da tabela
        Cursor cursor = db.query("tarefa", null, whereClause,whereArgs, null, null, "dataLembrete");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String auxData;
        //Inserir dados do cursos na lista instanciada anteriormente
        if(cursor.moveToFirst()){
            do {
                Tarefa Tarefa = new Tarefa();
                Tarefa.setId(cursor.getInt(cursor.getColumnIndex("id")));
                Tarefa.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                auxData = String.valueOf(cursor.getInt(cursor.getColumnIndex("dataLimite")));
                System.out.println(auxData + auxData.length());
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")){
                    if(auxData.length()==7){
                        auxData="0"+auxData;
                    }
                    auxData = auxData.subSequence(0,2).toString().concat("/") + auxData.subSequence(2,4).toString().concat("/") + auxData.subSequence(4,8).toString();
                    System.out.println(auxData + " teste");
                    Tarefa.setDataLimite(sdf.parse(auxData));
                }
                System.out.println("passou");
                auxData = "";
                auxData = String.valueOf(cursor.getLong(cursor.getColumnIndex("dataLembrete")));
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")) {
                    if(auxData.length()==7){
                        auxData="0"+auxData;
                    }
                    auxData = auxData.subSequence(0, 2).toString().concat("/") + auxData.subSequence(2, 4).toString().concat("/") + auxData.subSequence(4, 8).toString();
                    Tarefa.setDataLembrete(sdf.parse(auxData));
                }
                auxData = "";
                auxData = String.valueOf(cursor.getLong(cursor.getColumnIndex("dataRealizacao")));
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")) {
                    if(auxData.length()==7){
                        auxData="0"+auxData;
                    }
                    auxData = auxData.subSequence(0, 2).toString().concat("/") + auxData.subSequence(2, 4).toString().concat("/") + auxData.subSequence(4, 8).toString();
                    Tarefa.setDataRealizacao(sdf.parse(auxData));
                }
                auxData= "";
                Tarefa.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                Tarefa.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                Tarefa.setIdCategoria(cursor.getInt(cursor.getColumnIndex("idCategoria")));
                lista.add(Tarefa);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        helper.close();

        return lista;

    }
    public List<Tarefa> selecionarTodos() throws ParseException {
        List<Tarefa> lista = new ArrayList<Tarefa>();
        //Abrir o banco de dados para leitura
        db = helper.getReadableDatabase();

        //Obter os dados da tabela
        Cursor cursor = db.query("tarefa", null, null,null, null, null, "dataLembrete");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String auxData;
        //Inserir dados do cursos na lista instanciada anteriormente
        if(cursor.moveToFirst()){
            do {
                Tarefa Tarefa = new Tarefa();
                Tarefa.setId(cursor.getInt(cursor.getColumnIndex("id")));
                Tarefa.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                auxData = String.valueOf(cursor.getInt(cursor.getColumnIndex("dataLimite")));
                System.out.println(auxData + auxData.length());
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")){
                    if(auxData.length()==7){
                        auxData="0"+auxData;
                    }
                    auxData = auxData.subSequence(0,2).toString().concat("/") + auxData.subSequence(2,4).toString().concat("/") + auxData.subSequence(4,8).toString();
                    System.out.println(auxData + " teste");
                    Tarefa.setDataLimite(sdf.parse(auxData));
                }
                System.out.println("passou");
                auxData = "";
                auxData = String.valueOf(cursor.getLong(cursor.getColumnIndex("dataLembrete")));
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")) {
                    if(auxData.length()==7){
                        auxData="0"+auxData;
                    }
                    auxData = auxData.subSequence(0, 2).toString().concat("/") + auxData.subSequence(2, 4).toString().concat("/") + auxData.subSequence(4, 8).toString();
                    Tarefa.setDataLembrete(sdf.parse(auxData));
                }
                auxData = "";
                auxData = String.valueOf(cursor.getLong(cursor.getColumnIndex("dataRealizacao")));
                if(!auxData.equals("")&&!auxData.equals(null)&&!auxData.isEmpty()&&!auxData.equals("0")) {
                    if(auxData.length()==7){
                        auxData="0"+auxData;
                    }
                    auxData = auxData.subSequence(0, 2).toString().concat("/") + auxData.subSequence(2, 4).toString().concat("/") + auxData.subSequence(4, 8).toString();
                    Tarefa.setDataRealizacao(sdf.parse(auxData));
                }
                auxData= "";
                Tarefa.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                Tarefa.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                Tarefa.setIdCategoria(cursor.getInt(cursor.getColumnIndex("idCategoria")));
                lista.add(Tarefa);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        helper.close();

        return lista;

    }

}
