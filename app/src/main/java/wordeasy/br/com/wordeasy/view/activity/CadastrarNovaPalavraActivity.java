package wordeasy.br.com.wordeasy.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenteOperacaoPalavra;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaPalavra;
import wordeasy.br.com.wordeasy.presenter.PalavraPresenter;
import wordeasy.br.com.wordeasy.view.util.Constantes;
import wordeasy.br.com.wordeasy.view.util.Mensagem;
import wordeasy.br.com.wordeasy.view.util.Utilitario;

public class CadastrarNovaPalavraActivity extends AppCompatActivity implements ViewOperacaoRequisitaPalavra{


    @Bind(R.id.toolbar)  Toolbar mtoolbar;
    @Bind(R.id.input_name_palavra_ingles) EditText edt_palavra_ingles;
    @Bind(R.id.input_traducao_um)         EditText edt_palavra_traducao_um;
    @Bind(R.id.input_layout_name_palavra_ingles) TextInputLayout inputLayoutName;
    @Bind(R.id.input_layout_traducao_um)         TextInputLayout inputLayoutTraducaoUm;
    @Bind(R.id.scrollViewCadastrar) ScrollView scrol_cadastrar;
    @Bind(R.id.scrollViewAlterar) ScrollView scrol_alterar;
    @Bind(R.id.txtPalavraInglesEditar) TextView txtInglesAlterar;
    @Bind(R.id.txtPalavraPortuguesEditar) TextView txtPortuguesAlterar;

    private  ArrayList<Palavra> palavrasSalvas;
    private Palavra palavra;
    private  boolean cadastrou = true;
    private PresenteOperacaoPalavra mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_palavra);
        ButterKnife.bind(this);
        inicializarViewsTela();

        if(getIntent().getExtras() != null) {
            palavra = (Palavra) getIntent().getSerializableExtra(Palavra.ID);
            if(palavra != null) {

                txtInglesAlterar.setText(palavra.getPalavraEmIngles());
                txtPortuguesAlterar.setText(palavra.getPalavraEmPortugues());
                scrol_cadastrar.setVisibility(View.GONE);
                scrol_alterar.setVisibility(View.VISIBLE);
            }
        }
    }


    //metodos

    void clearCampos(){
        edt_palavra_traducao_um.setText("");
        edt_palavra_ingles.setText("");
        edt_palavra_ingles.requestFocus();
    }


    //listener
    public void onclick_gravarPalavra(View view) {

        Palavra p = new Palavra();

        Usuario user  =  Utilitario.getSharedPreferenceUsuario(CadastrarNovaPalavraActivity.this);
        p.setPalavraEmPortugues( edt_palavra_traducao_um.getText().toString().toUpperCase());
        p.setPalavraEmIngles( edt_palavra_ingles.getText().toString().toUpperCase());
        p.setUsuario(user);
        mPresenter.novaPalavra(p,Constantes.CADASTRANDO);
    }

    public void onclick_alterarPortugues(View v) {

        LayoutInflater layoutInflater = (LayoutInflater)CadastrarNovaPalavraActivity.this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.palavra_alterar, null);

        final EditText editar_palavra = (EditText) view.findViewById(R.id.edt_editar);
        Button btnAlterar = (Button) view.findViewById(R.id.btn_alterar);

        editar_palavra.setText(palavra.getPalavraEmPortugues().toString());

        final AlertDialog.Builder  alert = new AlertDialog.Builder(CadastrarNovaPalavraActivity.this);

        alert.setView(view);
        final AlertDialog dialog  = alert.create();
        dialog.show();

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                palavra.setPalavraEmPortugues(editar_palavra.getText().toString().toUpperCase());
                boolean alterou =  mPresenter.alteraPalavra(palavra,false);

                if(alterou) {
                    txtPortuguesAlterar.setText(editar_palavra.getText().toString());
                    cadastrou = false;
                    dialog.dismiss();
                }
            }
        });
    }

    public void onclick_alterarIngles(View v) {

        LayoutInflater layoutInflater = (LayoutInflater)CadastrarNovaPalavraActivity.this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.palavra_alterar, null);

        final EditText editar_palavra = (EditText) view.findViewById(R.id.edt_editar);
        Button btnAlterar = (Button) view.findViewById(R.id.btn_alterar);

        editar_palavra.setText(palavra.getPalavraEmIngles());

        final AlertDialog.Builder  alert = new AlertDialog.Builder(CadastrarNovaPalavraActivity.this);

        alert.setView(view);
        final AlertDialog dialog  = alert.create();
        dialog.show();

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                palavra.setPalavraEmIngles(editar_palavra.getText().toString().toUpperCase());
                boolean alterou =  mPresenter.alteraPalavra(palavra,true);

                if(alterou) {
                    txtInglesAlterar.setText(editar_palavra.getText().toString());
                    cadastrou = false;
                    dialog.dismiss();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra(Palavra.ID, palavrasSalvas);
        setResult(2, returnIntent);
        this.finish();
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();

        if(cadastrou) {
            returnIntent.putExtra(Palavra.ID, palavrasSalvas);
            setResult(2, returnIntent);
            this.finish();
        }
        else if(cadastrou  == false) {
            returnIntent.putExtra(Palavra.ID, palavra );
            setResult(3, returnIntent);
            this.finish();
        }
    }

    @Override
    public void campoNaoPreenchido(String result) {

        if(result.equals("ingles")) {
            inputLayoutName.setError(getString(R.string.err_msg_name_palavra_ingles));
            Utilitario.requestFocus(edt_palavra_ingles, CadastrarNovaPalavraActivity.this);
        }  else {
            inputLayoutName.setErrorEnabled(false);
        }

       if(result.equals("portugues")) {
            inputLayoutTraducaoUm.setError(getString(R.string.err_msg_palavra_traducao_um));
            Utilitario.requestFocus(edt_palavra_traducao_um, CadastrarNovaPalavraActivity.this);
        }else {
            inputLayoutTraducaoUm.setErrorEnabled(false);
        }
    }

    @Override
    public void palavrasCadastradas(Palavra palavra) {
        palavrasSalvas.add(palavra);
        clearCampos();
    }

    @Override
    public void palavraAlterada(Palavra palavra) {
        this.palavra = palavra;
    }

    @Override
    public void showToast(String message) {
        Mensagem.toast(CadastrarNovaPalavraActivity.this,message).show();
    }

    @Override
    public void showSnackBar(String message) {
        Mensagem.snackbar(message,findViewById(R.id.coordinator)).show();
    }

    @Override
    public void inicializarViewsTela() {
        mtoolbar.setTitle(R.string.new_word);
        setSupportActionBar(mtoolbar);
        mtoolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        palavra = new Palavra();
        palavrasSalvas = new ArrayList<Palavra>();

        try {
            mPresenter = new PalavraPresenter(this, CadastrarNovaPalavraActivity.this);
        } catch (Exception e) {
            Mensagem.toast(CadastrarNovaPalavraActivity.this,"Não foi possivel carregar o \'Presenter\'").show();
        }
    }
}
