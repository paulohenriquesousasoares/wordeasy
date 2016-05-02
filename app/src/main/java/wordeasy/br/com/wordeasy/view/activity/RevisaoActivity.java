package wordeasy.br.com.wordeasy.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenteOperacaoEstudar;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaEstudar;
import wordeasy.br.com.wordeasy.presenter.EstudarPresente;
import wordeasy.br.com.wordeasy.view.dao.repositorio.ConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.view.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.util.Mensagem;

public class RevisaoActivity extends AppCompatActivity implements ViewOperacaoRequisitaEstudar {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.revisaoModo1_actRevisao)RelativeLayout revisaoModo1;
    @Bind(R.id.txtPalavraInglesActRevisao)TextView txtPalavraInglesModo1;
    @Bind(R.id.edtTraduzir_actRevisao)EditText edtValorDigitado;

    private PalavraRepositorio palavraRepositorio;
    private ArrayList<Palavra> palavrasListaGlobal;
    int positionLista = 0;

    private PresenteOperacaoEstudar mPresente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revisao);
        ButterKnife.bind(this);
        inicializarViewsTela();

       Usuario usuario =  getUsuarioAtual(RevisaoActivity.this);

        if(usuario != null) {

            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get(Palavra.ID).equals(Palavra.ID))
                    getListaPalavraRevisao(true,usuario.getId());
            } else
                getListaPalavraRevisao(false,usuario.getId());
        }
    }

   //metodos

    @Override
    public void inicializarViewsTela() {
        palavraRepositorio = new PalavraRepositorio();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.revisao_label);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        try{
            mPresente   = new EstudarPresente(this);
        }
        catch (Exception e) {
            Mensagem.snackbar("" + e, findViewById(R.id.layoutRevisao)).show();
        }

    }

    private void getListaPalavraRevisao(boolean takeAllPalavras, long userId) {

        ConfiguracaoRepositorio configuracaoRepositorio = new ConfiguracaoRepositorio();
        int takeAllpalavraRevisao = 0;
        try {

            Configuracao configuracao = configuracaoRepositorio.getConfiguracao(userId);
            mPresente.getPalavrasEstudar(takeAllPalavras, userId, takeAllpalavraRevisao, configuracao.getItensPorSessaoRevisao());
        } catch (Exception e) {
            Mensagem.snackbar("" + e, findViewById(R.id.layoutRevisao)).show();
        }
    }

    private void atualizaTextViewPalavraEmIngles() {
        if(positionLista < palavrasListaGlobal.size()) {
            txtPalavraInglesModo1.setText(palavrasListaGlobal.get(positionLista).getPalavraEmIngles());
        }
    }

    private void atualizaPalavra(Palavra palavra) {
        try {
            palavraRepositorio.create(palavra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //listener

    public void onclickVerificarResposta(View view) {

        if(palavrasListaGlobal.size() > -1) {

            final Palavra palavraAtualSendoRevisada = palavrasListaGlobal.get(positionLista);

            String[] palavraCorreta = palavraAtualSendoRevisada.getPalavraEmPortugues().split(",");
            String   palavraResposta = edtValorDigitado.getText().toString();

            boolean jaVerificouPalavra =false;
            int volta = 0;
            for (int i = 0; i <palavraCorreta.length; i++) {

                volta =  volta + 1;
                if (palavraResposta.toLowerCase().trim().equals(palavraCorreta[i].toLowerCase().trim())) {
                    try {
                        palavraAtualSendoRevisada.setQtdAcertos(palavraAtualSendoRevisada.getQtdAcertos() + 1);
                        atualizaPalavra(palavraAtualSendoRevisada);
                        jaVerificouPalavra =true;
                        break;

                    } catch (Exception e) {
                        Mensagem.toast(RevisaoActivity.this, "Error ao salvar esta palavras , " + e);
                    }
                }

                else if(volta == palavraCorreta.length || palavraCorreta.length ==1) {

                    palavraAtualSendoRevisada.setQtdErros(palavraAtualSendoRevisada.getQtdErros() + 1);
                    atualizaPalavra(palavraAtualSendoRevisada);

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

                        if (positionLista == palavrasListaGlobal.size() - 1) {
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
                            palavraAtualSendoRevisada.setQtdVezesEstudou(palavraAtualSendoRevisada.getQtdVezesEstudou() + 1);
                            atualizaPalavra(palavraAtualSendoRevisada);
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

    @Override
    public Usuario getUsuarioAtual(Context context) {
        try {
           return mPresente.getUsuarioAtual(context);
        } catch (Exception e) {
            Mensagem.snackbar(""+e,findViewById(R.id.layoutRevisao)).show();
        }
        return null;
    }

    @Override
    public void preenchePalavraListaEstudar(ArrayList<Palavra> palavras) {
        palavrasListaGlobal = palavras;
        atualizaTextViewPalavraEmIngles();
    }

    @Override
    public void showToast(String message) {
            Mensagem.toast(RevisaoActivity.this,message).show();
    }

    @Override
    public void showSnackBar(String message) {
        Mensagem.snackbar(message,findViewById(R.id.layoutRevisao)).show();
    }


}
