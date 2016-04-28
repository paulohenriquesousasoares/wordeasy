package wordeasy.br.com.wordeasy.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
import me.drakeet.materialdialog.MaterialDialog;
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


       if( getIntent().getExtras() != null) {
           if(getIntent().getExtras().get(Palavra.ID).equals(Palavra.ID))
               getListaPalavraRevisao(true);
       }
        else
           getListaPalavraRevisao(false);
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

    private void getListaPalavraRevisao(boolean takeCardPersonalizado) {
        ConfiguracaoRepositorio configuracaoRepositorio = new ConfiguracaoRepositorio();
        Configuracao config = null;
        try {
            long userId = Utilitario.getSharedPreferenceUsuario(RevisaoActivity.this).getId();

            config = configuracaoRepositorio.getConfiguracao(userId);

            if (config.getItensPorSessaoRevisao() > 4) {

                if(takeCardPersonalizado)
                    palavrasList = palavraRepositorio.getAllCardPersonalizado(userId,0);
                else
                    palavrasList = palavraRepositorio.get(config.getItensPorSessaoRevisao(), userId);

                atualizaTextViewPalavraEmIngles();
            }

            }catch(Exception e){
                Mensagem.toast(RevisaoActivity.this,""+e).show();
            }
    }

    private void getAllCardPersonalizado() {
        ConfiguracaoRepositorio configuracaoRepositorio = new ConfiguracaoRepositorio();
        Configuracao config = null;
        try {
            long userId = Utilitario.getSharedPreferenceUsuario(RevisaoActivity.this).getId();
            palavrasList = palavraRepositorio.getAllCardPersonalizado(userId,0);
            atualizaTextViewPalavraEmIngles();

        } catch (Exception e) {
            Mensagem.toast(RevisaoActivity.this,""+e).show();
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

            String[] palavraCorreta = p.getPalavraEmPortugues().split(",");
            String palavraResposta = edtValorDigitado.getText().toString();

            boolean jaVerificouPalavra =false;
            int volta = 0;
            for (int i = 0; i <palavraCorreta.length; i++) {

                volta =  volta + 1;
                if (palavraResposta.toLowerCase().trim().equals(palavraCorreta[i].toLowerCase().trim())) {
                    try {
                        p.setQtdAcertos(p.getQtdAcertos() + 1);
                        atualizaPalavra(p);
                        jaVerificouPalavra =true;
                        break;

                    } catch (Exception e) {
                        Mensagem.toast(RevisaoActivity.this, "Error ao salvar esta palavras , " + e);
                    }
                }

                else if(volta == palavraCorreta.length || palavraCorreta.length ==1) {

                    p.setQtdErros(p.getQtdErros() + 1);
                    atualizaPalavra(p);

                    edtValorDigitado.setHintTextColor(getResources().getColor(R.color.vermelho));
                    edtValorDigitado.setTextColor(getResources().getColor(R.color.vermelho));
                    YoYo.with(Techniques.Shake).duration(800).playOn(edtValorDigitado);
                    jaVerificouPalavra = true;
                    break;
                }
            }


            if(jaVerificouPalavra) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (positionLista == palavrasList.size() - 1) {
                            final MaterialDialog dialog = Mensagem.materialDialogAviso(RevisaoActivity.this, "Informação",
                                    "Parabéns palavras revisada com sucesso.");
                            dialog.setPositiveButton("Sair", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    RevisaoActivity.this.finish();
                                }
                            });
                            dialog.show();
                        } else {
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
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
