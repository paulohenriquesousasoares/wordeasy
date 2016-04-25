package wordeasy.br.com.wordeasy.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

import io.realm.Realm;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.activity.MainActivity;
import wordeasy.br.com.wordeasy.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.dominio.Configuracao;
import wordeasy.br.com.wordeasy.dominio.Palavra;
import wordeasy.br.com.wordeasy.dominio.Usuario;

public class Utilitario {

    private static Realm realm;

    public static void getColor(String indiceLetra, View view) {

        if(indiceLetra.equals("A"))
            view.setBackgroundResource(R.drawable.radius_a);
        else if(indiceLetra.equals("B"))
            view.setBackgroundResource(R.drawable.radius_b);
        else if(indiceLetra.equals("C"))
            view.setBackgroundResource(R.drawable.radius_c);
        else if(indiceLetra.equals("D"))
            view.setBackgroundResource(R.drawable.radius_d);
        else if(indiceLetra.equals("E"))
            view.setBackgroundResource(R.drawable.radius_e);
        else if(indiceLetra.equals("F"))
            view.setBackgroundResource(R.drawable.radius_f);
        else if(indiceLetra.equals("G"))
            view.setBackgroundResource(R.drawable.radius_g);
        else if(indiceLetra.equals("H"))
            view.setBackgroundResource(R.drawable.radius_h);
        else if(indiceLetra.equals("I"))
            view.setBackgroundResource(R.drawable.radius_i);
        else if(indiceLetra.equals("J"))
            view.setBackgroundResource(R.drawable.radius_j);
        else if(indiceLetra.equals("K"))
            view.setBackgroundResource(R.drawable.radius_k);
        else if(indiceLetra.equals("L"))
            view.setBackgroundResource(R.drawable.radius_l);
        else if(indiceLetra.equals("M"))
            view.setBackgroundResource(R.drawable.radius_m);
        else if(indiceLetra.equals("N"))
            view.setBackgroundResource(R.drawable.radius_n);
        else if(indiceLetra.equals("O"))
            view.setBackgroundResource(R.drawable.radius_o);
        else if(indiceLetra.equals("P"))
            view.setBackgroundResource(R.drawable.radius_p);
        else if(indiceLetra.equals("Q"))
            view.setBackgroundResource(R.drawable.radius_q);
        else if(indiceLetra.equals("R"))
            view.setBackgroundResource(R.drawable.radius_r);
        else if(indiceLetra.equals("S"))
            view.setBackgroundResource(R.drawable.radius_s);
        else if(indiceLetra.equals("T"))
            view.setBackgroundResource(R.drawable.radius_t);
        else if(indiceLetra.equals("U"))
            view.setBackgroundResource(R.drawable.radius_u);
        else if(indiceLetra.equals("V"))
            view.setBackgroundResource(R.drawable.radius_v);
        else if(indiceLetra.equals("W"))
            view.setBackgroundResource(R.drawable.radius_w);
        else if(indiceLetra.equals("X"))
            view.setBackgroundResource(R.drawable.radius_x);
        else if(indiceLetra.equals("Y"))
            view.setBackgroundResource(R.drawable.radius_y);
    }

    //Pega as palavras como teste
    public static int getPalavrasIniciais(Usuario user) {

        PalavraRepositorio palavraRepositorio = new PalavraRepositorio();
        int qtdPalavrasInseridas = 0;

        String[] ingles = new String[]{"Home","Car","Therefore","Wonderful","Against","Take","Smile"
                ,"Yourself","I","Gift","Document","Cross","Coffe","Anybody","Religion","Murder"
                ,"Stick","Engage","Beginning","Very"};

        String[] portugues = new String[]{"Casa","Carro","Portanto","Maravihoso","Contra","Pegar","Sorriso"
                ,"Voce mesmo","Eu","Presente","Documento","Atravessar","Café","Alguém","Religião","Assassinato"
                ,"Lançar","Envolver-se","Começo","Muito"};

        for (int index = 0; index < ingles.length; index++) {

                Palavra palavra = new Palavra();
                palavra.setPalavraEmIngles(ingles[index].toString());
                palavra.setPalavraEmPortugues(portugues[index].toString());
                palavra.setIndicePalavra(ingles[index].toString().substring(0, 1).toUpperCase());
                palavra.setFavorito(false);
                palavra.setUsuario(user);
                palavra.setQtdErros(0);
                palavra.setQtdAcertos(0);
                palavra.setQtdVezesEstudou(0);
                palavra.setCardPersonalizado(false);
                palavra.setNaoEstudar(false);

            try {
                palavraRepositorio.create(palavra);
                qtdPalavrasInseridas +=1;
            }
            catch (Exception e) {}
        }
        return qtdPalavrasInseridas;
    }


    //PREFERENCE DE Usuario
    public static void salvaInSharedPreferenceUsuario(Activity activity, Usuario usuario) {
        SharedPreferences settings =  activity.getSharedPreferences(Constantes.PREFS_LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("id", usuario.getId());
        editor.putString("nome", usuario.getNome());
        editor.putString("email", usuario.getEmail());
        editor.putString("senha", usuario.getSenha());
        editor.commit();
    }

    public static Usuario getSharedPreferenceUsuario(Activity activity) {

        SharedPreferences settings = activity.getSharedPreferences(Constantes.PREFS_LOGIN, 0);
        Usuario usuario = new Usuario();

        usuario.setId(settings.getLong("id", 0));
        usuario.setNome(settings.getString("nome", ""));
        usuario.setEmail(settings.getString("email", ""));
        usuario.setSenha(settings.getString("senha", ""));
        return  usuario;
    }

    public static void deletaSharedPreferenceUsuario(Activity activity) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(Constantes.PREFS_LOGIN, 0).edit();
        editor.clear();
        editor.commit();
    }

    public static void requestFocus(View view, Activity activity) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //ALARME
    public static void destroyAlarme(Activity activity) {
        Intent intent = new Intent("ALARME_DISPARADO");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(activity.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        Log.i("TAG", "alarme destruido");

    }

    public static void criaAlarme(Activity activity,Configuracao configuracao) {

        boolean alarmeAtivo = (PendingIntent.getBroadcast(activity, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null) ;
        destroyAlarme(activity);

        //if(alarmeAtivo) {
            Log.i("TAG", "criou alarme");

            Intent intent = new Intent("ALARME_DISPARADO");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            //calendar.add(Calendar.HOUR_OF_DAY, configuracao.getHora());
            calendar.add(Calendar.MINUTE, configuracao.getMinuto());
            calendar.add(Calendar.HOUR, configuracao.getHora());

            AlarmManager alarmManager = (AlarmManager)activity.getSystemService(activity.ALARM_SERVICE);

            //Normal
            //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

            //Repeticao
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 60 * 60 * 24 , pendingIntent);//repete em 24 horas depois
        }
   // }
}

