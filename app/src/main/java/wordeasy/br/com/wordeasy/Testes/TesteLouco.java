package wordeasy.br.com.wordeasy.Testes;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

import wordeasy.br.com.wordeasy.R;

public class TesteLouco extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teste_louco);


        boolean alarmeAtivo = (PendingIntent.getBroadcast(this,0,new Intent("ALARME_DISPARADO"),PendingIntent.FLAG_NO_CREATE) == null) ;


        //if(alarmeAtivo) {

            Intent intent = new Intent("ALARME_DISPARADO");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            String[] hora = "02-1-00".split("-");

          //  calendar.add(Calendar.HOUR, 1);
            calendar.add(Calendar.MINUTE, Integer.parseInt( hora[1].toString()));
            calendar.add(Calendar.SECOND,Integer.parseInt( hora[2].toString()));
//
//             long inicio =  calendar.getTimeInMillis();
//
//            calendar.add(Calendar.YEAR,2016);
//            calendar.add(Calendar.DAY_OF_MONTH,07);
//            calendar.add(Calendar.MONTH,04);
//            calendar.add(Calendar.HOUR,02);
//            calendar.add(Calendar.MINUTE,05);
//            calendar.add(Calendar.SECOND,20);
//
//
//            long fim = inicio + calendar.getTimeInMillis() ;



            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            //Normal
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

            //Repeticao
            //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 60 * 60 * 24 , pendingIntent);//repete em 24 horas depois
//        }
//        else{
//            Log.i(Constantes.TAG,"alarme criado");
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Se for repetir o alarme nao precisa destruir
        Intent intent = new Intent("ALARME_DISPARADO");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
