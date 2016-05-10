package wordeasy.br.com.wordeasy.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.view.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.view.dao.repositorio.UsuarioRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.util.Mensagem;
import wordeasy.br.com.wordeasy.view.util.Utilitario;

public class DownloadPalavra extends AppCompatActivity {

    private Toolbar mtoolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dowload);
        inicializarViews();
    }


    public void baixarBasia(View view) {

//        String[] ingles =    {"hi","he","she","how",",because","you","are","is"};
//        String[] portugues = {"oi","ele","ela","como",",por que","voce","são,estão","ser,estar"};
//
//        Usuario usuarioLogado = Utilitario.getSharedPreferenceUsuario(DownloadPalavra.this);
//        PalavraRepositorio palavraRepositorio = new PalavraRepositorio();
//
//        for (int i=0;i<ingles.length;i++) {
//            Palavra palavra =  new Palavra();
//           // palavra.setUsuario();
//            palavra.setPalavraEmIngles(ingles[i].toString());
//            palavra.setPalavraEmPortugues(portugues[i].toString());
//
//            try {
//                palavraRepositorio.create(palavra);
//            } catch (Exception e) {
//                Mensagem.toast(this,""+e).show();
//            }
//        }

//        Intent intent = new Intent(DownloadPalavra.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        this.finish();

    }

    private void inicializarViews(){
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        mtoolbar.setTitle(R.string.download);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }
}
