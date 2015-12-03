package com.example.aluno.nuli.empresavencetex.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aluno on 28/10/2015.
 */
public class ClienteDAO {

    private DatabaseHelper helper;

    private SQLiteDatabase db;

    public ClienteDAO(Context context){
        helper = new DatabaseHelper(context);
    }
    public long inserir(Cliente cliente){
        //criar um "container" dos valores para cada campo da tabela
        ContentValues valores = new ContentValues();
        valores.put("cnpj",cliente.getCnpj());
        valores.put("nomeFantasia",cliente.getNomeFantasia());
        valores.put("telefone",cliente.getTelefone());

        //abrir o banco de dados para escrita
        db = helper.getWritableDatabase();
        //inserir o registro
        long rowID = db.insert("clientes",null,valores);
        //fechar os objetos
        db.close();
        helper.close();

        return rowID;
    }

    public List<Cliente> selecionarTodos(){
        List<Cliente> lista = new ArrayList<Cliente>();
        //Abrir o banco de dadospara leitura
        db = helper.getReadableDatabase();

        //Obter os dados da tabela
        Cursor cursor = db.query("clientes",null,null,null,null,null,"nomeFantasia");

        //Inserir dados do cursos na lista instanciada anteriormente
        if(cursor.moveToFirst()){
            do {
                Cliente cliente = new Cliente();
                cliente.setCnpj(cursor.getLong(cursor.getColumnIndex("cnpj")));
                cliente.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nomeFantasia")));
                cliente.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
                lista.add(cliente);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        helper.close();

        return lista;

    }
}
