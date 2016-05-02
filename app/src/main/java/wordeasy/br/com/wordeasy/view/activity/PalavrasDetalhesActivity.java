package wordeasy.br.com.wordeasy.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.util.Utilitario;

public class PalavrasDetalhesActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.txtPalavraEmIngles_detalhes_palavra) TextView palavraEmIngles;
    @Bind(R.id.txtPalavraTraducao_detalhes_palavra) TextView palavraEmPortugues;
    @Bind(R.id.container_radius_act_detalhes) RelativeLayout containerRelative;
    @Bind(R.id.txtletra_act_detalhes) TextView txtLetra;
    @Bind(R.id.txtPalavraVezesError_detalhes_palavra) TextView txtVezesErrou;
    @Bind(R.id.txtPalavraVezesAcertou_detalhes_palavra) TextView txtVezesAcertou;
    @Bind(R.id.txtPalavraVezesPraticou_detalhes_palavra) TextView txtVezesEstudou;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //TRANSITION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementExitTransition(new ChangeBounds());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_palavra);
        ButterKnife.bind(this);
        initView();

        if(getIntent().getExtras() != null) {

            Palavra palavra = (Palavra) getIntent().getExtras().getSerializable(Palavra.ID);

            if (palavra != null ) {
                palavraEmIngles.setText(palavra.getPalavraEmIngles());
                palavraEmPortugues.setText(palavra.getPalavraEmPortugues());
                txtLetra.setText(palavra.getIndicePalavra());
                txtVezesAcertou.setText("Vezes em que acertou: " + palavra.getQtdAcertos());
                txtVezesErrou.setText("Vezes em que errou: " + palavra.getQtdErros());
                txtVezesEstudou.setText("Vezes em que estudou: " + palavra.getQtdVezesEstudou());
                Utilitario.getColor(palavra.getIndicePalavra(),containerRelative);
            }
        }
    }

    void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
