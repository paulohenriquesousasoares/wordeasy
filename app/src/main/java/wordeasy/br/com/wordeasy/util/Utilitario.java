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

    public static int getColor(String indiceLetra) {
        int color = 0;
        if(indiceLetra.equals("A"))
            color = -4660521 ;
        else if(indiceLetra.equals("B"))
            color = -3412965 ;
        else if(indiceLetra.equals("C"))
            color = -12940888 ;
        else if(indiceLetra.equals("D"))
            color = -3478580 ;
        else if(indiceLetra.equals("E"))
            color = -13154063 ;
        else if(indiceLetra.equals("F"))
            color = -14895558 ;
        else if(indiceLetra.equals("G"))
            color = -8562421 ;
        else if(indiceLetra.equals("H"))
            color = -1551638 ;
        else if(indiceLetra.equals("I"))
            color = -1551638 ;
        else if(indiceLetra.equals("J"))
            color = -11950552 ;
        else if(indiceLetra.equals("K"))
            color = -13144879 ;
        else if(indiceLetra.equals("L"))
            color = -10972431 ;
        else if(indiceLetra.equals("M"))
            color = -7000647 ;
        else if(indiceLetra.equals("N"))
            color = -7296035 ;
        else if(indiceLetra.equals("O"))
            color = -3702338 ;
        else if(indiceLetra.equals("P"))
            color = -2635926 ;
        else if(indiceLetra.equals("Q"))
            color = -10898198 ;
        else if(indiceLetra.equals("R"))
            color = -10930585 ;
        else if(indiceLetra.equals("S"))
            color = -4764745 ;
        else if(indiceLetra.equals("T"))
            color = -15084134 ;
        else if(indiceLetra.equals("U"))
            color = -12359560 ;
        else if(indiceLetra.equals("V"))
            color = -16492753 ;
        else if(indiceLetra.equals("W"))
            color = -772986 ;
        else if(indiceLetra.equals("X"))
            color = -124899 ;
        else if(indiceLetra.equals("Y"))
            color = -9619180 ;
        else if(indiceLetra.equals("Z"))
            color = -4905984 ;


        return color;

    }

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
    public static ArrayList<Palavra> getDataSet() {
        ArrayList results = new ArrayList<Palavra>();

        String[] ingles = new String[]{"Home","Car","Therefore","Wonderful","Against","Take","Smile"
                ,"Yourself","I","Gift","Document","Cross","Coffe","Anybody","Religion","Murder"
                ,"Stick","Engage","Beginning","Very","Do","Did"};

        String[] portugues = new String[]{"Casa","Carro","Portanto","Maravihoso","Contra","Pegar","Sorriso"
                ,"Voce mesmo","Eu","Presente","Documento","Atravessar","Café","Alguém","Religião","Assassinato"
                ,"Lançar","Envolver-se","Começo","Muito","Fazer","Fiz"};


//        for (int index = 0; index < ingles.length; index++) {
//
//            String palavraAtualIndice = ingles[index].substring(0,1);
//            Palavra obj = new Palavra(palavraAtualIndice.toUpperCase(), ingles[index], portugues[index], false);
//            results.add(index, obj);
//        }
        return results;
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

            long minutos = converteHoraEmMinuto(calendar);

            AlarmManager alarmManager = (AlarmManager)activity.getSystemService(activity.ALARM_SERVICE);

            //Normal
            //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

            //Repeticao
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, minutos,1000 * 60 * 60 * 24 , pendingIntent);//repete em 24 horas depois
        }
   // }

    public static long converteHoraEmMinuto(Calendar c) {

        Calendar calendar = Calendar.getInstance();
        long m =  calendar.getTimeInMillis() - c.getTimeInMillis();
        return m;
    }

}

