package wordeasy.br.com.wordeasy.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import wordeasy.br.com.wordeasy.dominio.Palavra;
import wordeasy.br.com.wordeasy.util.Constantes;
import wordeasy.br.com.wordeasy.util.Mensagem;
import wordeasy.br.com.wordeasy.util.Utilitario;

/**
 * Created by paulo on 28/12/2015.
 */
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estudar);

        ButterKnife.bind(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.estudando);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flight_right_out);
        setLeftIn =   (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.flight_left_in);

        palavraLista = Utilitario.getDataSet();
        palavraListaPosition = palavraListaPosition+1;

        txtIngles.setText(palavraLista.get(palavraListaPosition).getPalavraEmIngles());
        txtTraducao.setText(palavraLista.get(palavraListaPosition).getPalavraEmPortugues());
    }

    @OnClick(R.id.btnFlip)
    public void FlipCard() {

        String descricaoBotao = btnVirarCard.getText().toString();
        int totalItensEstudar = palavraLista.size();

        Log.i(Constantes.TAG, "Position = " + palavraListaPosition);

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
    }

    @OnClick(R.id.txt_palavra_tap)
    public void jaSei() {
        Mensagem.snackbar("Ja sei",findViewById(R.id.act_estudar)).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}







