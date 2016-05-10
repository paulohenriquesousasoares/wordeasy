package wordeasy.br.com.wordeasy.view.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.Realm;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.view.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public class Utilitario {


    public static void getColor(String indiceLetra, View view) {

        GradientDrawable shape = (GradientDrawable) view.getBackground();

        if(indiceLetra.equals("A")) {
            shape.setStroke(1, view.getResources().getColor( R.color.verde) );
            shape.setColor(view.getResources().getColor(R.color.verde));

        }
        else if(indiceLetra.equals("B")) {
            shape.setStroke(1, view.getResources().getColor( R.color.azul_claro) );
            shape.setColor(view.getResources().getColor(R.color.azul_claro));
        }
        else if(indiceLetra.equals("C")) {
            shape.setStroke(1, view.getResources().getColor( R.color.siena) );
            shape.setColor(view.getResources().getColor(R.color.siena));
        }
        else if(indiceLetra.equals("D")) {
            shape.setStroke(1, view.getResources().getColor( R.color.pink) );
            shape.setColor(view.getResources().getColor(R.color.pink));
        }
        else if(indiceLetra.equals("E")) {
            shape.setStroke(1, view.getResources().getColor( R.color.azul_claro_bebe) );
            shape.setColor(view.getResources().getColor(R.color.azul_claro_bebe));
        }
        else if (indiceLetra.equals("F")) {
            shape.setStroke(1, view.getResources().getColor( R.color.stell_blue) );
            shape.setColor(view.getResources().getColor(R.color.stell_blue));
        }
        else if (indiceLetra.equals("G")) {
            shape.setStroke(1, view.getResources().getColor( R.color.mediumsean) );
            shape.setColor(view.getResources().getColor(R.color.mediumsean));
        }
        else if (indiceLetra.equals("H")) {
            shape.setStroke(1, view.getResources().getColor( R.color.darkcyan) );
            shape.setColor(view.getResources().getColor(R.color.darkcyan));
        }
        else if (indiceLetra.equals("I")) {
            shape.setStroke(1, view.getResources().getColor( R.color.saddlebrown) );
            shape.setColor(view.getResources().getColor(R.color.saddlebrown));
        }
        else if (indiceLetra.equals("J")) {
            shape.setStroke(1, view.getResources().getColor( R.color.salmon) );
            shape.setColor(view.getResources().getColor(R.color.salmon));
        }
        else if (indiceLetra.equals("K")) {
            shape.setStroke(1, view.getResources().getColor( R.color.burlywood) );
            shape.setColor(view.getResources().getColor(R.color.burlywood));
        }
        else if (indiceLetra.equals("L")) {
            shape.setStroke(1, view.getResources().getColor( R.color.lightgreen) );
            shape.setColor(view.getResources().getColor(R.color.lightgreen));
        }
        else if (indiceLetra.equals("M")) {
            shape.setStroke(1, view.getResources().getColor( R.color.lightPurple) );
            shape.setColor(view.getResources().getColor(R.color.lightPurple));
        }
        else if (indiceLetra.equals("N")) {
            shape.setStroke(1, view.getResources().getColor( R.color.forestgreen) );
            shape.setColor(view.getResources().getColor(R.color.forestgreen));
        }
        else if (indiceLetra.equals("O")) {
            shape.setStroke(1, view.getResources().getColor( R.color.weat) );
            shape.setColor(view.getResources().getColor(R.color.weat));

        }
        else if (indiceLetra.equals("P")) {
             shape.setStroke(1, view.getResources().getColor( R.color.thistle) );
            shape.setColor(view.getResources().getColor(R.color.thistle));
        }
        else if (indiceLetra.equals("Q")) {
            shape.setStroke(1, view.getResources().getColor( R.color.palertuquose) );
            shape.setColor(view.getResources().getColor(R.color.palertuquose));
        }
        else if (indiceLetra.equals("R")) {
             shape.setStroke(1, view.getResources().getColor( R.color.blanchedalmond) );
            shape.setColor(view.getResources().getColor(R.color.blanchedalmond));

        }
        else if (indiceLetra.equals("S")) {
            shape.setStroke(1, view.getResources().getColor( R.color.darkkhaki) );
            shape.setColor(view.getResources().getColor(R.color.darkkhaki));
        }
        else if (indiceLetra.equals("T")) {
            shape.setStroke(1, view.getResources().getColor( R.color.darkseagreen) );
            shape.setColor(view.getResources().getColor(R.color.darkseagreen));
        }
        else if (indiceLetra.equals("U")) {
           shape.setStroke(1, view.getResources().getColor( R.color.darkslategrey) );
            shape.setColor(view.getResources().getColor(R.color.darkslategrey));
        }
        else if (indiceLetra.equals("V")) {
            shape.setStroke(1, view.getResources().getColor( R.color.firebrick) );
            shape.setColor(view.getResources().getColor(R.color.firebrick));
        }
        else if (indiceLetra.equals("W")) {
            shape.setStroke(1, view.getResources().getColor( R.color.lightpink) );
            shape.setColor(view.getResources().getColor(R.color.lightpink));
        }
        else if (indiceLetra.equals("XXX")) {
            shape.setStroke(1, view.getResources().getColor( R.color.cinza) );
            shape.setColor(view.getResources().getColor(R.color.cinza));

        }
        else if (indiceLetra.equals("Y")) {
            shape.setStroke(1, view.getResources().getColor( R.color.colorPrimary) );
            shape.setColor(view.getResources().getColor(R.color.colorPrimary));
        }
        else if(indiceLetra.equals("Z")) {
            shape.setStroke(1, view.getResources().getColor( R.color.betagreen) );
            shape.setColor(view.getResources().getColor(R.color.betagreen));
        }
    }


    //PREFERENCE DE Usuario
    public static void salvaInSharedPreferenceUsuario(Activity activity, Usuario usuario) {
        SharedPreferences settings =  activity.getSharedPreferences(Constantes.PREFS_LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("ID", usuario.getId());
        editor.putString("NOME", usuario.getNome());
        editor.putString("EMAIL", usuario.getEmail());
        editor.putString("SENHA", usuario.getSenha());
        editor.commit();
    }

    public static Usuario getSharedPreferenceUsuario(Activity activity) {

        SharedPreferences settings = activity.getSharedPreferences(Constantes.PREFS_LOGIN, 0);
        Usuario usuario = new Usuario();

        usuario.setId(settings.getLong("ID", 0));
        usuario.setNome(settings.getString("NOME", ""));
        usuario.setEmail(settings.getString("EMAIL", ""));
        usuario.setSenha(settings.getString("SENHA", ""));
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
            Intent intent = new Intent("ALARME_DISPARADO");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //calendar.setTimeInMillis(System.currentTimeMillis());

            calendar.add(Calendar.HOUR_OF_DAY, configuracao.getHora());
            calendar.add(Calendar.MINUTE, configuracao.getMinuto());
           // calendar.add(Calendar.HOUR, configuracao.getHora());

            AlarmManager alarmManager = (AlarmManager)activity.getSystemService(activity.ALARM_SERVICE);

            //Normal
            //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

               //Repeticao 1000 * 60 * 60 * 24
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 60 * 60 * 24 , pendingIntent);//repete em 24 horas depois
        }
   // }
}

