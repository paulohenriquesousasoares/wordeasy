package wordeasy.br.com.wordeasy.view.dao.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import wordeasy.br.com.wordeasy.view.dao.DatabaseHelper;
import wordeasy.br.com.wordeasy.view.dao.contrato.IUsuarioRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.util.Constantes;

public class UsuarioRepositorio  {

    private DatabaseHelper databaseHelper;

    public UsuarioRepositorio(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public UsuarioRepositorio(){}

    public long create(Usuario usuario) throws Exception{
        SQLiteDatabase db =  databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constantes.NOME, usuario.getNome());
        values.put(Constantes.EMAIL, usuario.getEmail());
        values.put(Constantes.SENHA, usuario.getSenha());
        long idInseerido =  db.insert(Constantes.TABLE_USUARIO, null, values);
        db.close();
        return idInseerido;
    }

    public Usuario getByUserEmailAndSenha(String usuarioEmail, String usuarioSenha) throws Exception{

        Usuario usuario = new Usuario();
        String selectQuery = "SELECT * FROM USUARIO WHERE EMAIL = '"+usuarioEmail+"' AND SENHA = '"+usuarioSenha+"'";

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        usuario.setId(c.getLong(c.getColumnIndex(Constantes.ID)));
        usuario.setNome((c.getString(c.getColumnIndex(Constantes.NOME))));
        usuario.setEmail(c.getString(c.getColumnIndex(Constantes.EMAIL)));
        usuario.setSenha(c.getString(c.getColumnIndex(Constantes.SENHA)));
        db.close();

        return usuario;
    }

    //    public List<Usuario> getAllUsuario() throws  Exception{
//
//        List<Usuario> todos = new ArrayList<Usuario>();
//        String selectQuery = "SELECT  * FROM USUARIO" ;
//
//        SQLiteDatabase db = databaseHelper.getWritableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c.moveToFirst()) {
//            do {
//                Usuario usuario = new Usuario();
//                usuario.setId(c.getLong(c.getColumnIndex("ID")));
//                usuario.setNome(c.getString(c.getColumnIndex("NOME")));
//                usuario.setEmail(c.getString(c.getColumnIndex("EMAIL")));
//                usuario.setSenha(c.getString(c.getColumnIndex("SENHA")));
//                todos.add(usuario);
//            } while (c.moveToNext());
//        }
//        db.close();
//        return todos;
//    }
}
