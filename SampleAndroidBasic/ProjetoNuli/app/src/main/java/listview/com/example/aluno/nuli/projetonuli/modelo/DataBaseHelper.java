package listview.com.example.aluno.nuli.projetonuli.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aluno on 11/11/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="projetonuli.db";
    private static final String TABLE_CATEGORIA="categoria";
    private static final String TABLE_TAREFA="tarefa";
    private static final String TABLE_USUARIO="usuario";
    private static final int DATABASE_VERSION = 5;


    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CATEGORIA + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIO + " (username TEXT PRIMARY KEY, password TEXT, nome TEXT, sexo TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TAREFA + " (id INTEGER PRIMARY KEY AUTOINCREMENT, descricao TEXT, dataLimite INTEGER, dataLembrete INTEGER, dataRealizacao INTEGER, status TEXT, username TEXT, idCategoria INTEGER, FOREIGN KEY(username) REFERENCES usuario(username) ON DELETE CASCADE, FOREIGN KEY(idCategoria) REFERENCES categoria(id) ON DELETE RESTRICT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //executado quando a versão do banco de dados é alterada
            sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CATEGORIA + ";");
            sqLiteDatabase.execSQL("DROP TABLE " + TABLE_TAREFA + ";");
            sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USUARIO + ";");
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CATEGORIA + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIO + " (username TEXT PRIMARY KEY, password TEXT, nome TEXT, sexo TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TAREFA + " (id INTEGER PRIMARY KEY AUTOINCREMENT, descricao TEXT, dataLimite INTEGER, dataLembrete INTEGER, dataRealizacao INTEGER, status TEXT, username TEXT, idCategoria INTEGER, FOREIGN KEY(username) REFERENCES usuario(username) ON DELETE CASCADE, FOREIGN KEY(idCategoria) REFERENCES categoria(id) ON DELETE RESTRICT);");



    }
}
