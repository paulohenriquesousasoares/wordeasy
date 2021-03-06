package wordeasy.br.com.wordeasy.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.view.dao.DatabaseHelper;
import wordeasy.br.com.wordeasy.view.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.view.dao.repositorio.UsuarioRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.ObjTeste;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.network.NetworkConnection;
import wordeasy.br.com.wordeasy.view.util.Mensagem;
import wordeasy.br.com.wordeasy.view.util.Utilitario;


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

//            PalavraRepositorio palavraRepositorio = new PalavraRepositorio(SplashActivity.this);
//            try {
//                palavraRepositorio.deleta();
//            } catch (Exception e) { }


        //verifica se ja tem algum usuario no shared preference

        if(  Utilitario.getSharedPreferenceUsuario(SplashActivity.this).getId() > 0  ){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            this.finish();
        }
    }

    @OnClick(R.id.edt_Entrar_login)
    public void logar() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
    }


    @OnClick(R.id.edt_cadastrar)
    public void cadastrarUsuario() {
        startActivity(new Intent(SplashActivity.this, CadastrarUsuarioActivity.class));
    }



}
