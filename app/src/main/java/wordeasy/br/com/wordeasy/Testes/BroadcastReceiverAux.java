package wordeasy.br.com.wordeasy.Testes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import wordeasy.br.com.wordeasy.activity.MainActivity;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.activity.SplashActivity;
import wordeasy.br.com.wordeasy.util.Constantes;

public class BroadcastReceiverAux extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(Constantes.TAG, "-->Alarme");
        gerarNotificacao(context, new Intent(context, SplashActivity.class),"Nova mensagem","Hora de praticar :)","Reforçe sua memória com apenas 5 minutos por dia de estudo.");
    }


    public void gerarNotificacao(Context context,Intent intent, CharSequence ticker, CharSequence titulo, CharSequence descricao) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context,0,intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(titulo);
        builder.setContentText(descricao);
        builder.setSmallIcon(R.drawable.ic_create_white_24dp);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_create_white_24dp));
        builder.setContentIntent(p);

        Notification n = builder.build();
        n.vibrate = new long[]{150,300,150,600};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.drawable.ic_favorite_black_24dp,n);

        try {

            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque  = RingtoneManager.getRingtone(context,som);
            toque.play();
        }
        catch (Exception e) {}
    }
}
