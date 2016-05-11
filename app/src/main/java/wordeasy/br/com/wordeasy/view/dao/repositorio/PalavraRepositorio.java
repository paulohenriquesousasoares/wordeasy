package wordeasy.br.com.wordeasy.view.dao.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import wordeasy.br.com.wordeasy.view.dao.DatabaseHelper;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.util.Constantes;

public class PalavraRepositorio  {


    private DatabaseHelper databaseHelper;

    public PalavraRepositorio(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public ArrayList<Palavra> get(int qtdPalavras, long userId) throws  Exception{

        ArrayList<Palavra> palavraLista = new ArrayList<Palavra>();
        ArrayList<Palavra> palavraListaEstudar = new ArrayList<Palavra>();
        Palavra palavra;


        String selectQuery = "SELECT  * FROM " + Constantes.TABLE_PALAVRA + " WHERE USUARIO = " + userId + " AND "
                            + Constantes.NAO_ESTUDAR + "=" + Constantes.FALSE ;

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Obtem todas as palavras
        if (c.moveToFirst()) {
            do {
                palavra = new Palavra();
                palavra.setId(c.getLong(c.getColumnIndex(Constantes.ID)));
                palavra.setPalavraEmIngles(c.getString(c.getColumnIndex(Constantes.INGLES)));
                palavra.setPalavraEmPortugues(c.getString(c.getColumnIndex(Constantes.PORTUGUES)));
                palavra.setIndicePalavra(c.getString(c.getColumnIndex(Constantes.INDICE)));
                palavra.setUsuarioId(c.getLong(c.getColumnIndex(Constantes.USUARIO)));
                palavra.setQtdErros(c.getInt(c.getColumnIndex(Constantes.ERROS)));
                palavra.setQtdAcertos(c.getInt(c.getColumnIndex(Constantes.ACERTOS)));
                palavra.setQtdVezesEstudou(c.getInt(c.getColumnIndex(Constantes.QTD_ESTUDOU)));
                palavra.setCardPersonalizado(c.getInt(c.getColumnIndex(Constantes.CARD_PERSONZALIZADO)));
                palavra.setNaoEstudar(c.getInt(c.getColumnIndex(Constantes.NAO_ESTUDAR)));

                palavraLista.add(palavra);
            } while (c.moveToNext());
        }
        db.close();


        //todas as palavras size
        int count = palavraLista.size();

        Random gerador = new Random();

        List<Integer> indicesjaAdicionadosAoEstudo = new ArrayList<Integer>();

        for (int i=0; i< qtdPalavras;i++) {
            int position =  gerador.nextInt(count);

            while (true) {
                if(!indicesjaAdicionadosAoEstudo.contains(position)) {
                    indicesjaAdicionadosAoEstudo.add(position);
                    break;
                }
                position = gerador.nextInt(count);
            }

            palavra = new Palavra();
            palavra.setId(palavraLista.get(position).getId());
            palavra.setPalavraEmIngles(palavraLista.get(position).getPalavraEmIngles());
            palavra.setPalavraEmPortugues(palavraLista.get(position).getPalavraEmPortugues());
            palavra.setIndicePalavra(palavraLista.get(position).getIndicePalavra().toUpperCase());
            palavra.setUsuarioId(palavraLista.get(position).getUsuarioId());
            palavra.setQtdErros(palavraLista.get(position).getQtdErros());
            palavra.setQtdAcertos(palavraLista.get(position).getQtdAcertos());
            palavra.setQtdVezesEstudou(palavraLista.get(position).getQtdVezesEstudou());
            palavra.setCardPersonalizado(palavraLista.get(position).isCardPersonalizado());
            palavra.setNaoEstudar(palavraLista.get(position).isNaoEstudar());
            palavraListaEstudar.add(palavra);
        }

        return palavraListaEstudar;
    }

    public void create(Palavra palavra) throws Exception {

        SQLiteDatabase db =  databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constantes.INGLES, palavra.getPalavraEmIngles());
        values.put(Constantes.PORTUGUES, palavra.getPalavraEmPortugues());
        values.put(Constantes.INDICE, palavra.getIndicePalavra());
        values.put(Constantes.USUARIO,palavra.getUsuarioId() );
        values.put(Constantes.ERROS, palavra.getQtdErros());
        values.put(Constantes.ACERTOS, palavra.getQtdAcertos());
        values.put(Constantes.QTD_ESTUDOU, palavra.getQtdVezesEstudou());
        values.put(Constantes.CARD_PERSONZALIZADO, palavra.isCardPersonalizado());
        values.put(Constantes.NAO_ESTUDAR, palavra.isNaoEstudar());
        long idInserido =  db.insert(Constantes.TABLE_PALAVRA, null, values);
        db.close();
    }

    public void alteraPalavra(Palavra palavra) throws Exception {
        SQLiteDatabase db =  databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constantes.INGLES, palavra.getPalavraEmIngles());
        values.put(Constantes.PORTUGUES, palavra.getPalavraEmPortugues());
        values.put(Constantes.INDICE, palavra.getIndicePalavra());
        values.put(Constantes.USUARIO,palavra.getUsuarioId() );
        values.put(Constantes.ERROS, palavra.getQtdErros());
        values.put(Constantes.ACERTOS, palavra.getQtdAcertos());
        values.put(Constantes.QTD_ESTUDOU, palavra.getQtdVezesEstudou());
        values.put(Constantes.CARD_PERSONZALIZADO, palavra.isCardPersonalizado());
        values.put(Constantes.NAO_ESTUDAR, palavra.isNaoEstudar());

        db.update(Constantes.TABLE_PALAVRA, values, Constantes.ID + " = ?", new String[]{String.valueOf(palavra.getId())});
    }

    public long removerCardPersonalizado() throws Exception {
        SQLiteDatabase db =  databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //0 - siginica que nao esta no card personalizado
        values.put(Constantes.CARD_PERSONZALIZADO, 0);
        return db.update(Constantes.TABLE_PALAVRA, values,null,null);
    }

    public void deleta() throws Exception{

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(Constantes.TABLE_PALAVRA,null,null);
        db.close();
    }

    public ArrayList<Palavra> getById(long userId) throws Exception{

        ArrayList<Palavra> palavraLista = new ArrayList<Palavra>();
        String selectQuery = "SELECT  * FROM " + Constantes.TABLE_PALAVRA + " WHERE USUARIO = " + userId ;

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Palavra palavra = new Palavra();
                palavra.setId(c.getLong(c.getColumnIndex(Constantes.ID)));
                palavra.setPalavraEmIngles(c.getString(c.getColumnIndex(Constantes.INGLES)));
                palavra.setPalavraEmPortugues(c.getString(c.getColumnIndex(Constantes.PORTUGUES)));
                palavra.setIndicePalavra(c.getString(c.getColumnIndex(Constantes.INDICE)));
                palavra.setUsuarioId(c.getLong(c.getColumnIndex(Constantes.USUARIO)));
                palavra.setQtdErros(c.getInt(c.getColumnIndex(Constantes.ERROS)));
                palavra.setQtdAcertos(c.getInt(c.getColumnIndex(Constantes.ACERTOS)));
                palavra.setQtdVezesEstudou(c.getInt(c.getColumnIndex(Constantes.QTD_ESTUDOU)));
                palavra.setCardPersonalizado(c.getInt(c.getColumnIndex(Constantes.CARD_PERSONZALIZADO)));
                palavra.setNaoEstudar(c.getInt(c.getColumnIndex(Constantes.NAO_ESTUDAR)));

                palavraLista.add(palavra);
            } while (c.moveToNext());
        }
        db.close();
        return palavraLista;
    }

    public Palavra getByIdSingle(long palavraId) throws Exception{

        Palavra palavra = new Palavra();
        String selectQuery = "SELECT  * FROM " + Constantes.TABLE_PALAVRA + "  WHERE " + Constantes.ID + "  = " + palavraId ;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();

            palavra.setId(c.getLong(c.getColumnIndex(Constantes.ID)));
            palavra.setPalavraEmIngles(c.getString(c.getColumnIndex(Constantes.INGLES)));
            palavra.setPalavraEmPortugues(c.getString(c.getColumnIndex(Constantes.PORTUGUES)));
            palavra.setIndicePalavra(c.getString(c.getColumnIndex(Constantes.INDICE)));
            palavra.setUsuarioId(c.getLong(c.getColumnIndex(Constantes.USUARIO)));
            palavra.setQtdErros(c.getInt(c.getColumnIndex(Constantes.ERROS)));
            palavra.setQtdAcertos(c.getInt(c.getColumnIndex(Constantes.ACERTOS)));
            palavra.setQtdVezesEstudou(c.getInt(c.getColumnIndex(Constantes.QTD_ESTUDOU)));
            palavra.setCardPersonalizado(c.getInt(c.getColumnIndex(Constantes.CARD_PERSONZALIZADO)));
            palavra.setNaoEstudar(c.getInt(c.getColumnIndex(Constantes.NAO_ESTUDAR)));
        }
        db.close();
        return palavra;
    }

    //opcao 0 = get all cardpersonalizado
    //opcao 1 = get all nao estudar
    //opcao 3 = pega todas as palavras
    public ArrayList<Palavra> getAllPalavra(long userId, int opcao) throws Exception{

        ArrayList<Palavra> palavraLista = new ArrayList<Palavra>();

        String selectQuery ="";

        if(opcao == 0) {
            selectQuery =  "SELECT  * FROM " + Constantes.TABLE_PALAVRA + "  WHERE USUARIO = " + userId + " AND "
                    + Constantes.CARD_PERSONZALIZADO  + " = " + Constantes.TRUE + " AND "
                    + Constantes.NAO_ESTUDAR + "= " + Constantes.FALSE + " order by  " + Constantes.INGLES + " asc";

        }
        else if(opcao == 1) {

            selectQuery =  "SELECT  * FROM " + Constantes.TABLE_PALAVRA + " WHERE USUARIO = " + userId + " AND "
                    + Constantes.NAO_ESTUDAR  +" = " + Constantes.TRUE + " order by  " + Constantes.INGLES + " asc";

        }
        else if(opcao == 3){

            selectQuery =  "SELECT  * FROM " + Constantes.TABLE_PALAVRA + " WHERE USUARIO = " + userId + " AND "
                    + Constantes.NAO_ESTUDAR  +" = " + Constantes.FALSE + " order by  " + Constantes.INGLES + " asc";


        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Palavra palavra = new Palavra();
                palavra.setId(c.getLong(c.getColumnIndex(Constantes.ID)));
                palavra.setPalavraEmIngles(c.getString(c.getColumnIndex(Constantes.INGLES)));
                palavra.setPalavraEmPortugues(c.getString(c.getColumnIndex(Constantes.PORTUGUES)));
                palavra.setIndicePalavra(c.getString(c.getColumnIndex(Constantes.INDICE)));
                palavra.setUsuarioId(c.getLong(c.getColumnIndex(Constantes.USUARIO)));
                palavra.setQtdErros(c.getInt(c.getColumnIndex(Constantes.ERROS)));
                palavra.setQtdAcertos(c.getInt(c.getColumnIndex(Constantes.ACERTOS)));
                palavra.setQtdVezesEstudou(c.getInt(c.getColumnIndex(Constantes.QTD_ESTUDOU)));
                palavra.setCardPersonalizado(c.getInt(c.getColumnIndex(Constantes.CARD_PERSONZALIZADO)));
                palavra.setNaoEstudar(c.getInt(c.getColumnIndex(Constantes.NAO_ESTUDAR)));

                palavraLista.add(palavra);
            } while (c.moveToNext());
        }
        db.close();
        return palavraLista;
    }

    public boolean getByName(long userId,String palavraToVerificar) throws Exception{

        boolean retorno = true;


        String selectQuery = "SELECT  * FROM " + Constantes.TABLE_PALAVRA + " WHERE USUARIO = " + userId +
                             " AND " + Constantes.INGLES + "= '"+palavraToVerificar.toLowerCase()+"'" ;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

           if( c.moveToFirst() ) {

               Palavra palavra = new Palavra();
               long id = c.getLong(c.getColumnIndex(Constantes.ID));
               String indice = c.getString(c.getColumnIndex(Constantes.INDICE));

               if (id > 0)
                   palavra.setId(id);

               palavra.setPalavraEmIngles(c.getString(c.getColumnIndex(Constantes.INGLES)));
               palavra.setPalavraEmPortugues(c.getString(c.getColumnIndex(Constantes.PORTUGUES)));

               if (indice != null)
                   palavra.setIndicePalavra(indice);
               else
                   palavra.setIndicePalavra("ND");

               palavra.setUsuarioId(c.getLong(c.getColumnIndex(Constantes.USUARIO)));
               palavra.setQtdErros(c.getInt(c.getColumnIndex(Constantes.ERROS)));
               palavra.setQtdAcertos(c.getInt(c.getColumnIndex(Constantes.ACERTOS)));
               palavra.setQtdVezesEstudou(c.getInt(c.getColumnIndex(Constantes.QTD_ESTUDOU)));
               palavra.setCardPersonalizado(c.getInt(c.getColumnIndex(Constantes.CARD_PERSONZALIZADO)));
               palavra.setNaoEstudar(c.getInt(c.getColumnIndex(Constantes.NAO_ESTUDAR)));
               retorno = false;
           }
        }
        db.close();
        return  retorno;
    }




}
