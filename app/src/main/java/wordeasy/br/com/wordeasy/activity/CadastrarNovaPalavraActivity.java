package wordeasy.br.com.wordeasy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.dominio.Palavra;
import wordeasy.br.com.wordeasy.dominio.Usuario;
import wordeasy.br.com.wordeasy.servico.PalavraServico;
import wordeasy.br.com.wordeasy.util.Mensagem;
import wordeasy.br.com.wordeasy.util.Utilitario;

/**
 * Created by paulo on 27/12/2015.
 */
public class CadastrarNovaPalavraActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)  Toolbar mtoolbar;
    @Bind(R.id.input_name_palavra_ingles) EditText edt_palavra_ingles;
    @Bind(R.id.input_traducao_um)         EditText edt_palavra_traducao_um;
    @Bind(R.id.input_layout_name_palavra_ingles) TextInputLayout inputLayoutName;
    @Bind(R.id.input_layout_traducao_um)         TextInputLayout inputLayoutTraducaoUm;

    private PalavraServico palavraServico;
    private PalavraRepositorio palavraRepositorio;
    private  ArrayList<Palavra> palavrasSalvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_palavra);
        ButterKnife.bind(this);
        initView();
    }

    /*===============================================================================================================
                METODOS
    * =============================================================================================================*/

    void initView() {
        mtoolbar.setTitle(R.string.new_word);
        setSupportActionBar(mtoolbar);
        mtoolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        palavraRepositorio = new PalavraRepositorio();
        palavraServico = new PalavraServico();
        palavrasSalvas = new ArrayList<Palavra>();
    }

    public void auxiliarValidaCampo(String result) {

        if(result.equals("ingles")) {
            inputLayoutName.setError(getString(R.string.err_msg_nome));
            Utilitario.requestFocus(edt_palavra_ingles, CadastrarNovaPalavraActivity.this);
        }  else {
            inputLayoutName.setErrorEnabled(false);
        }

        if(result.equals("portugues")) {
            inputLayoutTraducaoUm.setError(getString(R.string.err_msg_email));
            Utilitario.requestFocus(edt_palavra_traducao_um, CadastrarNovaPalavraActivity.this);
        }else {
            inputLayoutTraducaoUm.setErrorEnabled(false);
        }
    }

    void clearCampos(){
        edt_palavra_traducao_um.setText("");
        edt_palavra_ingles.setText("");
        edt_palavra_ingles.requestFocus();
    }


     /*===============================================================================================================
                LISTENER
    * =============================================================================================================*/


    @OnClick(R.id.btn_gravar)
    public  void GravarPalavraListener() {

        Palavra palavra = new Palavra();
        Usuario user =  Utilitario.getSharedPreferenceUsuario(CadastrarNovaPalavraActivity.this);

        palavra.setPalavraEmIngles(edt_palavra_ingles.getText().toString());
        palavra.setPalavraEmPortugues(edt_palavra_traducao_um.getText().toString());
        palavra.setFavorito(false);
        palavra.setUsuario(user);

        String eNovaPalavra =  palavraServico.getByNome(user.getId(), edt_palavra_ingles.getText().toString().toLowerCase());

        if(eNovaPalavra.equals("ok")) {

            String result = palavraServico.validaPalavra(palavra);

            if (result.equals("ok")) {
                try {
                    palavra.setIndicePalavra(edt_palavra_ingles.getText().toString().substring(0, 1));
                    palavraServico.create(palavra);
                    clearCampos();
                    palavrasSalvas.add(palavra);
                } catch (Exception e) {
                    Mensagem.toast(CadastrarNovaPalavraActivity.this, "Error ao persistir os dados " + e.toString()).show();
                }
            } else {
                auxiliarValidaCampo(result);
            }
        }
        else {
            if(eNovaPalavra.equals("existe"))
                Mensagem.snackbar("Palavra já existe",findViewById(R.id.coordinator)).show();
            else
                Mensagem.snackbar(eNovaPalavra,findViewById(R.id.coordinator)).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Palavra.ID,palavrasSalvas);
        setResult(2, returnIntent);
        this.finish();
        return true;
    }
}
