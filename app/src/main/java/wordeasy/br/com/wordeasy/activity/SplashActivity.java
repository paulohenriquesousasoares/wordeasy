package wordeasy.br.com.wordeasy.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.dao.repositorio.UsuarioRepositorio;
import wordeasy.br.com.wordeasy.dominio.Configuracao;
import wordeasy.br.com.wordeasy.dominio.Usuario;
import wordeasy.br.com.wordeasy.util.Constantes;
import wordeasy.br.com.wordeasy.util.Utilitario;

/**
 * Created by paulo on 28/12/2015.
 */
public class SplashActivity extends AppCompatActivity {


    @Bind(R.id.edt_Entrar_login) TextView btnLogar;
    @Bind(R.id.edt_cadastrar) TextView btnCadastrar;
    @Bind(R.id.txt_titulo_login) TextView txtTituloLogin;
    @Bind(R.id.txt_sub_titulo_login) TextView txtSubTituloLogin;

    private final int SPLASH_DISPLAY_LENGTH = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ButterKnife.bind(this);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Typeface tp = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
                    txtTituloLogin.setTypeface(tp);
                    txtSubTituloLogin.setTypeface(tp);

                    //Typeface tp2 = Typeface.createFromAsset(getAssets(), "fonts/OpenSans.ttf");
                    //btnLogar.setTypeface(tp2);
                    //btnCadastrar.setTypeface(tp2);

                    txtTituloLogin.setVisibility(View.VISIBLE);
                    txtSubTituloLogin.setVisibility(View.VISIBLE);

                    YoYo.with(Techniques.BounceIn).duration(3000).playOn(txtTituloLogin);
                    YoYo.with(Techniques.BounceIn).duration(2500).playOn(txtSubTituloLogin);

                    btnLogar.setVisibility(View.VISIBLE);
                    btnCadastrar.setVisibility(View.VISIBLE);

                    YoYo.with(Techniques.FadeIn).duration(3300).playOn(btnLogar);
                    YoYo.with(Techniques.FadeIn).duration(3300).playOn(btnCadastrar);

                }
            }, SPLASH_DISPLAY_LENGTH);


        //verifica se ja tem algum usuario no shared preference
        if(  Utilitario.getSharedPreferenceUsuario(SplashActivity.this).getId() > 0  ){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            this.finish();
        }


        Realm realm = Realm.getDefaultInstance();
        RealmResults<Usuario> results = realm.where(Usuario.class).findAll();

        for(Usuario u : results) {
            Log.i("TAG","ID = " + u.getId() + " NOME = " + u.getNome() +  " EMAIL = " + u.getEmail() + " SENHA = " + u.getSenha());
        }

    }

    @OnClick(R.id.edt_Entrar_login)
    public void logar() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
    }


    @OnClick(R.id.edt_cadastrar)
    public void cadastrarUsuario() {
        startActivity(new Intent(SplashActivity.this,CadastrarUsuarioActivity.class));
    }
}
