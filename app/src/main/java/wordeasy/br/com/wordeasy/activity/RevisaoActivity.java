package wordeasy.br.com.wordeasy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wordeasy.br.com.wordeasy.R;
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
    @Bind(R.id.btnOpcao1)Button btnOpcao1;
    @Bind(R.id.btnOpcao2)Button btnOpcao2;
    @Bind(R.id.btnOpcao3)Button btnOpcao3;
    @Bind(R.id.btnOpcao4)Button btnOpcao4;
    @Bind(R.id.btnOpcao5)Button btnOpcao5;
    @Bind(R.id.btnOpcao6)Button btnOpcao6;
    int positionLista = 0;

    private ArrayList<Palavra> palavrasList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revisao);
        ButterKnife.bind(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.revisao_label);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        palavrasList = Utilitario.getDataSet();
        geraEscolheTelaRevisao();
        atualizaPalavras();
    }

    @OnClick(R.id.btnVerificarResposat_actRevisao)
    public void verificarResposta() {

       String palavraResposta =   edtValorDigitado.getText().toString();
       String palavraCorreta = palavrasList.get(positionLista).getPalavraEmPortugues();

        if(palavraResposta.toLowerCase().equals(palavraCorreta.toLowerCase())) {
            Mensagem.toast(this, "correta").show();
        }
        else {
            edtValorDigitado.setHintTextColor(getResources().getColor( R.color.vermelho) );
            edtValorDigitado.setTextColor(getResources().getColor( R.color.vermelho) );
            YoYo.with(Techniques.Shake).duration(800).playOn(edtValorDigitado);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                positionLista = positionLista + 1;
                YoYo.with(Techniques.SlideInRight).duration(500).playOn(revisaoModo1);
                edtValorDigitado.setText("");
                edtValorDigitado.setHintTextColor(getResources().getColor(R.color.cinza));
                edtValorDigitado.setTextColor(getResources().getColor(R.color.black_de));
                atualizaPalavras();
            }
        }, 1000);

    }

    public void geraEscolheTelaRevisao() {

        Random gerador = new Random();
        int gerado =  0;//gerador.nextInt(2);

        if(gerado == 0) {
            revisaoModo1.setVisibility(View.VISIBLE);
            revisaoModo2.setVisibility(View.GONE);
        }
        else if(gerado == 1) {
            revisaoModo1.setVisibility(View.GONE);
            revisaoModo2.setVisibility(View.VISIBLE);
        }
    }

    public void atualizaPalavras() {
        //seta as palavras em ingles
        txtPalavraInglesModo1.setText(palavrasList.get(positionLista).getPalavraEmIngles());
        txtPalavraInglesModo2.setText(palavrasList.get(positionLista).getPalavraEmIngles());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
