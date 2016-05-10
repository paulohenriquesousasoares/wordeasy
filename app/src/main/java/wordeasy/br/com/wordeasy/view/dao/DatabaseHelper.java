package wordeasy.br.com.wordeasy.view.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.util.Constantes;

public class DatabaseHelper extends SQLiteOpenHelper {

    public String USUAIRO_CREATE = "CREATE TABLE " + Constantes.TABLE_USUARIO + " ("+Constantes.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    Constantes.NOME  + " TEXT, " +
                                    Constantes.EMAIL+ " TEXT, " +
                                    Constantes.SENHA + " TEXT); ";

    public String PALAVRA_CREATE = "CREATE TABLE " + Constantes.TABLE_PALAVRA + " ("+Constantes.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Constantes.INGLES  + " TEXT, " +
            Constantes.PORTUGUES+ " TEXT, " +
            Constantes.INDICE + " TEXT, " +
            Constantes.USUARIO + " INTEGER, " +
            Constantes.ERROS + " INTEGER, " +
            Constantes.ACERTOS + " INTEGER, " +
            Constantes.QTD_ESTUDOU + " INTEGER, " +
            Constantes.CARD_PERSONZALIZADO + " INTEGER, " +
            Constantes.NAO_ESTUDAR + " INTEGER ); ";

    public DatabaseHelper(Context context) {
        super(context, Constantes.DATABASE_NOME, null, Constantes.DATABASE_VERSION);

        Log.i("TAG", "entrou na criação das tabelas DatabaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USUAIRO_CREATE);
        db.execSQL(PALAVRA_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constantes.TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Constantes.TABLE_PALAVRA);
        onCreate(db);
    }
}
