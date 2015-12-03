package com.example.aluno.nuli.empresavencetex.modelo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aluno on 28/10/2015.
 */

//Uma classe para gerenciar a criação do banco de dados e sua versão
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="vencetext.db";
    private static final String TABLE_NAME="clientes";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (cnpj INTEGER PRIMARY KEY, nomeFantasia TEXT, telefone TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //executado quando a versão do banco de dados é alterada

    }
}
