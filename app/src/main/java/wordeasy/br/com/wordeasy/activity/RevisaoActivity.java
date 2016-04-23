package wordeasy.br.com.wordeasy.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.dao.repositorio.ConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.dominio.Configuracao;
import wordeasy.br.com.wordeasy.dominio.Palavra;
import wordeasy.br.com.wordeasy.util.Mensagem;
import wordeasy.br.com.wordeasy.util.Utilitario;

public class RevisaoActivity extends AppCompatActivity{

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.revisaoModo1_actRevisao)RelativeLayout revisaoModo1;
    @Bind(R.id.revisaoModo2_actRevisao)RelativeLayout revisaoModo2;
    @Bind(R.id.txtPalavraInglesActRevisao)TextView txtPalavraInglesModo1;
    @Bind(R.id.txtPalavraInglesActRevisaoModo2)TextView txtPalavraInglesModo2;
    @Bind(R.id.edtTraduzir_actRevisao)EditText edtValorDigitado;

    private PalavraRepositorio palavraRepositorio;
    private ArrayList<Palavra> palavrasList;
    int positionLista = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revisao);
        ButterKnife.bind(this);
        initView();
        getListaPalavraRevisao();
    }

    /*=======================================================================================================
          METODOS
  * =====================================================================================================*/
    void initView() {
        palavraRepositorio = new PalavraRepositorio();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.revisao_label);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void getListaPalavraRevisao() {
        ConfiguracaoRepositorio configuracaoRepositorio = new ConfiguracaoRepositorio();
        Configuracao config = null;
        try {
            long userId = Utilitario.getSharedPreferenceUsuario(RevisaoActivity.this).getId();

            config = configuracaoRepositorio.getConfiguracao(userId);
            palavrasList = palavraRepositorio.get(config.getItensPorSessaoRevisao(), userId);
            atualizaTextViewPalavraEmIngles();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void atualizaTextViewPalavraEmIngles() {
        if(positionLista < palavrasList.size()) {
            txtPalavraInglesModo1.setText(palavrasList.get(positionLista).getPalavraEmIngles());
        }
    }

    private Palavra getPalavraById(long id) {
        Palavra p = new Palavra();
        try {
            p =  palavraRepositorio.getById(id);
        } catch (Exception e) {
           Mensagem.toast(RevisaoActivity.this,""+e).show();
        }
        return p;
    }

    private void atualizaPalavra(Palavra palavra) {
        try {
            palavraRepositorio.create(palavra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


     /*=======================================================================================================
           LISTENER
    * =====================================================================================================*/

    @OnClick(R.id.btnVerificarResposat_actRevisao)
    public void verificarResposta() {

        if(palavrasList.size() > -1) {

            long id = palavrasList.get(positionLista).getId();
            final Palavra p = getPalavraById(id);

            String palavraResposta = edtValorDigitado.getText().toString();
            String palavraCorreta = p.getPalavraEmPortugues();

            if (palavraResposta.toLowerCase().equals(palavraCorreta.toLowerCase())) {
                try {
                    p.setQtdAcertos(p.getQtdAcertos() + 1);
                    atualizaPalavra(p);
                } catch (Exception e) {
                    Mensagem.toast(RevisaoActivity.this, "Error ao salvar esta palavras , " + e);
                }
            } else {

                p.setQtdErros(p.getQtdErros() + 1);
                atualizaPalavra(p);

                edtValorDigitado.setHintTextColor(getResources().getColor(R.color.vermelho));
                edtValorDigitado.setTextColor(getResources().getColor(R.color.vermelho));
                YoYo.with(Techniques.Shake).duration(800).playOn(edtValorDigitado);
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(positionLista == palavrasList.size() - 1){
                        AlertDialog.Builder alert =   Mensagem.alertDialog(RevisaoActivity.this, "", "Parabéns todas as palavras estudas com sucesso. ")
                                .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    }
                    else {
                        positionLista++;
                        YoYo.with(Techniques.SlideInRight).duration(500).playOn(revisaoModo1);
                        edtValorDigitado.setText("");
                        edtValorDigitado.setHintTextColor(getResources().getColor(R.color.cinza));
                        edtValorDigitado.setTextColor(getResources().getColor(R.color.black_de));

                        atualizaTextViewPalavraEmIngles();
                        p.setQtdVezesEstudou(p.getQtdVezesEstudou() + 1);
                        atualizaPalavra(p);
                    }
                }
            }, 1000);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
