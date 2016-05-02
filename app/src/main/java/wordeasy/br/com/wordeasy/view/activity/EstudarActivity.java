package wordeasy.br.com.wordeasy.view.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
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
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenteOperacaoEstudar;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaEstudar;
import wordeasy.br.com.wordeasy.presenter.EstudarPresente;
import wordeasy.br.com.wordeasy.view.dao.repositorio.ConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.util.Mensagem;

public class EstudarActivity extends AppCompatActivity implements ViewOperacaoRequisitaEstudar {


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
    //private PalavraRepositorio palavraRepositorio;

    private PresenteOperacaoEstudar mPresente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estudar);
        ButterKnife.bind(this);
        inicializarViewsTela();
    }


    //metodos

    @Override
    public void inicializarViewsTela() {
        palavraLista = new ArrayList<Palavra>();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.estudando);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flight_right_out);
        setLeftIn =   (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.flight_left_in);

        //inicializa o presente
        try {
            mPresente = new EstudarPresente(this);
        } catch (Exception e) {
            Mensagem.toast(EstudarActivity.this, "" + e);
        }

        Usuario usuario =  getUsuarioAtual(EstudarActivity.this);
        if(usuario.getId() > 0) {

            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().getString(Palavra.ID).equals(Palavra.ID))
                    getPalavrasEstudar(true, usuario);
            } else
                getPalavrasEstudar(false, usuario);
        }
    }

    private void mensagemContinuarEstudando() {

        AlertDialog.Builder alert = new AlertDialog.Builder(EstudarActivity.this);
        alert.setTitle("Aguardando");
        alert.setMessage("Continuar estudando estes card ?");

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

    private void getPalavrasEstudar(boolean takeAllPalavras, Usuario usuario) {

        //vai buscar as palavras para estuar que não estão no 'Ja sei'
        int heCardPersonalziado = 0;

        ConfiguracaoRepositorio configuracaoRepositorio = new ConfiguracaoRepositorio();
        try {

            Configuracao configuracao =  configuracaoRepositorio.getConfiguracao(usuario.getId());
            mPresente.getPalavrasEstudar(takeAllPalavras,usuario.getId(),heCardPersonalziado,configuracao.getItensPorSessaoEstudo());

        } catch (Exception e) {
            Mensagem.toast(EstudarActivity.this,""+e).show();
        }
    }


    //listener

    public void onclick_virarCard(View view) {

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

    @Override
    public void showToast(String message) {}

    @Override
    public void showSnackBar(String message) {
        Mensagem.snackbar(message,findViewById(R.id.act_estudar)).show();
    }

    @Override
    public Usuario getUsuarioAtual(Context context) {
        try {
            return mPresente.getUsuarioAtual(context);
        } catch (Exception e) {
            Mensagem.snackbar("error ao buscar o usuario: " + e, findViewById(R.id.act_estudar)).show();
        }
        return  null;
    }

    @Override
    public void preenchePalavraListaEstudar(ArrayList<Palavra> palavras) {
        palavraLista = palavras;
        palavraListaPosition = 0;

        if(palavraLista.size() > 0) {
            txtIngles.setText(palavraLista.get(palavraListaPosition).getPalavraEmIngles());
            txtTraducao.setText(palavraLista.get(palavraListaPosition).getPalavraEmPortugues());
        }
    }
}







