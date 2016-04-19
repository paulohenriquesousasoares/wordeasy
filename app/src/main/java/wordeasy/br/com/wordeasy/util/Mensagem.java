package wordeasy.br.com.wordeasy.util;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Mensagem {

    public static Snackbar snackbar(String message, View view) {

        //View é o layout onde vai exibir a mensagem
        Snackbar snackbar = Snackbar
                .make(view,message, Snackbar.LENGTH_LONG);

        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        return ( snackbar );
    }

    public static Toast toast(Context context, String message) {
        return Toast.makeText(context,message,Toast.LENGTH_LONG);
    }




}
