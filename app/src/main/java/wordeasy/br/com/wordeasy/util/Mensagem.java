package wordeasy.br.com.wordeasy.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import me.drakeet.materialdialog.MaterialDialog;
import wordeasy.br.com.wordeasy.R;

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

    public static MaterialDialog materialDialogAviso(Activity activity, String title, String msg) {

        final MaterialDialog mMaterialDialog = new MaterialDialog(activity);
        mMaterialDialog.setTitle(title);
        mMaterialDialog.setMessage(msg);
        mMaterialDialog.setPositiveButton("Entendi", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();

            }
        });
        return  mMaterialDialog;
    }

    public static Toast toast(Context context, String message) {
        return Toast.makeText(context,message,Toast.LENGTH_LONG);
    }

    public static AlertDialog.Builder alertDialog(Context context,String title, String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(msg);
        return alert;
    }

}
