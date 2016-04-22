package wordeasy.br.com.wordeasy.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

public class EstudarActivity extends AppCompatActivity {


    @Bind(R.id.rl_card_front) View CardFront;
    @Bind(R.id.rl_card_back)  View CardBack;
    @Bind(R.id.toolbar)       Toolbar mToolbar;
    @Bind(R.id.txt_palavra_ingles) TextView txtIngles;
    @Bind(R.id.txt_primeira_traducao) TextView txtTraducao;
    @Bind(R.id.btnFlip) TextView btnVirarCard;

    private AnimatorSet setRightOut;
    private AnimatorSet setLeftIn;
    private boolean isBackVisible = false;
    private ArrayList<Palavra> palavraLista;
    private int palavraListaPosition = -1;
    private PalavraRepositorio palavraRepositorio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estudar);
        ButterKnife.bind(this);
        initView();
        getPalavrasEstudar();
    }


    /*=======================================================================================================
            METODOS
    * =====================================================================================================*/
    void initView() {

        palavraRepositorio = new PalavraRepositorio();
        palavraLista = new ArrayList<Palavra>();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.estudando);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flight_right_out);
        setLeftIn =   (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.flight_left_in);
    }

    private void mensagemContinuarEstudando() {

        AlertDialog.Builder alert = new AlertDialog.Builder(EstudarActivity.this);
        alert.setTitle("Parabéns todas as palavras foram estudadas.");
        alert.setMessage("Gostaria de estudar estes card novamente ?");

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                palavraListaPosition = 0;
                txtTraducao.setVisibility(View.INVISIBLE);
                YoYo.with(Techniques.SlideInRight).duration(1000).playOn(txtIngles);
                setRightOut.setTarget(CardBack);
                setLeftIn.setTarget(CardFront);
                setRightOut.start();
                setLeftIn.start();
                isBackVisible = false;
                btnVirarCard.setText(R.string.virar_card);
                txtIngles.setText(palavraLista.get(palavraListaPosition).getPalavraEmIngles());
                txtTraducao.setText(palavraLista.get(palavraListaPosition).getPalavraEmPortugues());
            }
        });
        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    EstudarActivity.this.finish();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void getPalavrasEstudar() {

        ConfiguracaoRepositorio configuracaoRepositorio = new ConfiguracaoRepositorio();
        Configuracao  config = null;
        try {
            config = configuracaoRepositorio.getConfiguracao(Utilitario.getSharedPreferenceUsuario(EstudarActivity.this).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        palavraLista = palavraRepositorio.get(config.getItensPorSessaoEstudo());
        palavraListaPosition = 0;

        if(palavraLista.size() > -1) {
            txtIngles.setText(palavraLista.get(palavraListaPosition).getPalavraEmIngles());
            txtTraducao.setText(palavraLista.get(palavraListaPosition).getPalavraEmPortugues());
        }
    }


    /*=======================================================================================================
           LISTENER
    * =====================================================================================================*/

    @OnClick(R.id.btnFlip)
    public void FlipCard() {

        String descricaoBotao = btnVirarCard.getText().toString();
        int totalItensEstudar = palavraLista.size();

        if (palavraListaPosition < totalItensEstudar) {

            if (!descricaoBotao.toLowerCase().equals("Próxima".toLowerCase())) {

                txtTraducao.setVisibility(View.VISIBLE);
                if (!isBackVisible) {
                    setRightOut.setTarget(CardFront);
                    setLeftIn.setTarget(CardBack);
                    setRightOut.start();
                    setLeftIn.start();
                    isBackVisible = true;
                    palavraListaPosition++;
                    btnVirarCard.setText(R.string.proxima);

                }
            } else {

                txtTraducao.setVisibility(View.INVISIBLE);
                YoYo.with(Techniques.SlideInRight).duration(1000).playOn(txtIngles);
                setRightOut.setTarget(CardBack);
                setLeftIn.setTarget(CardFront);
                setRightOut.start();
                setLeftIn.start();
                isBackVisible = false;
                btnVirarCard.setText(R.string.virar_card);
                txtIngles.setText(palavraLista.get(palavraListaPosition).getPalavraEmIngles());
                txtTraducao.setText(palavraLista.get(palavraListaPosition).getPalavraEmPortugues());
            }
        }
        else {
            mensagemContinuarEstudando();


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder alert =   Mensagem.alertDialog(EstudarActivity.this, "Fechar", "Deseja realmente sair ?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert =   Mensagem.alertDialog(EstudarActivity.this, "Fechar", "Deseja realmente sair ?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            finish();
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
}







