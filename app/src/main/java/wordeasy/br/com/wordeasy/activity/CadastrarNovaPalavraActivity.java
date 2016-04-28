package wordeasy.br.com.wordeasy.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    @Bind(R.id.scrollViewCadastrar) ScrollView scrol_cadastrar;
    @Bind(R.id.scrollViewAlterar) ScrollView scrol_alterar;
    @Bind(R.id.txtPalavraInglesEditar) TextView txtInglesAlterar;
    @Bind(R.id.txtPalavraPortuguesEditar) TextView txtPortuguesAlterar;

    private PalavraServico palavraServico;
    private PalavraRepositorio palavraRepositorio;
    private  ArrayList<Palavra> palavrasSalvas;
    private Palavra palavra;

    private String palavraInglesAux;
    private String palavraPortuguesAux;
    private  boolean cadastrou = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_palavra);
        ButterKnife.bind(this);
        initView();

        if(getIntent().getExtras() != null) {
            palavra = (Palavra) getIntent().getSerializableExtra(Palavra.ID);
            if(palavra != null) {

                txtInglesAlterar.setText(palavra.getPalavraEmIngles());
                txtPortuguesAlterar.setText(palavra.getPalavraEmPortugues());
                scrol_cadastrar.setVisibility(View.GONE);
                scrol_alterar.setVisibility(View.VISIBLE);

                palavraPortuguesAux = palavra.getPalavraEmPortugues();
                palavraInglesAux = palavra.getPalavraEmIngles();
            }
        }
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
        palavra = new Palavra();
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

    private boolean gravaOuAltera(String palavraValidar) {

        boolean retorno = false;

        Usuario user =  Utilitario.getSharedPreferenceUsuario(CadastrarNovaPalavraActivity.this);
        palavra.setPalavraEmIngles(palavraInglesAux.toUpperCase());
        palavra.setPalavraEmPortugues(palavraPortuguesAux.toUpperCase());
        palavra.setFavorito(false);
        palavra.setUsuario(user);


        String eNovaPalavra = palavraServico.getByNome(user.getId(), palavraValidar.toLowerCase());

        if(eNovaPalavra.equals("ok")) {

            String result = palavraServico.validaPalavra(palavra);

            if (result.equals("ok")) {
                try {

                    palavra.setIndicePalavra(palavraInglesAux.substring(0, 1));
                    palavraServico.create(palavra);
                    clearCampos();
                    palavrasSalvas.add(palavra);
                    retorno = true;

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

        return  retorno;
    }

    private void closeTodasTelas() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Palavra.ID, "paulo");
        setResult(3, returnIntent);
        CadastrarNovaPalavraActivity.this.finish();
    }



     /*===============================================================================================================
                LISTENER
    * =============================================================================================================*/


    @OnClick(R.id.btn_gravar)
    public  void GravarPalavraListener() {

        palavraInglesAux = edt_palavra_ingles.getText().toString();
        palavraPortuguesAux = edt_palavra_traducao_um.getText().toString();

        gravaOuAltera(palavraInglesAux);

//        Usuario user =  Utilitario.getSharedPreferenceUsuario(CadastrarNovaPalavraActivity.this);
//        palavra.setPalavraEmIngles(edt_palavra_ingles.getText().toString());
//        palavra.setPalavraEmPortugues(edt_palavra_traducao_um.getText().toString());
//        palavra.setFavorito(false);
//        palavra.setUsuario(user);
//
//        String eNovaPalavra =  palavraServico.getByNome(user.getId(), edt_palavra_ingles.getText().toString().toLowerCase());
//
//        if(eNovaPalavra.equals("ok")) {
//
//            String result = palavraServico.validaPalavra(palavra);
//
//            if (result.equals("ok")) {
//                try {
//                    palavra.setIndicePalavra(edt_palavra_ingles.getText().toString().substring(0, 1));
//                    palavraServico.create(palavra);
//                    clearCampos();
//                    palavrasSalvas.add(palavra);
//                } catch (Exception e) {
//                    Mensagem.toast(CadastrarNovaPalavraActivity.this, "Error ao persistir os dados " + e.toString()).show();
//                }
//            } else {
//                auxiliarValidaCampo(result);
//            }
//        }
//        else {
//            if(eNovaPalavra.equals("existe"))
//                Mensagem.snackbar("Palavra já existe",findViewById(R.id.coordinator)).show();
//            else
//                Mensagem.snackbar(eNovaPalavra,findViewById(R.id.coordinator)).show();
//        }
    }


    @OnClick(R.id.imgPalavraPortuguesEditar)
    public void editarPortugues() {

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

                if(!(editar_palavra.getText().toString().equals("") || editar_palavra.getText().toString().equals(null)) ) {

                    palavraPortuguesAux = editar_palavra.getText().toString();
                    gravaOuAltera(palavraPortuguesAux);
                    txtPortuguesAlterar.setText(palavraPortuguesAux);
                    dialog.dismiss();
                    cadastrou = false;
                }
                else {
                    Mensagem.toast(CadastrarNovaPalavraActivity.this,"Campo não pode ser vazio.").show();
                }
            }
        });
    }


    @OnClick(R.id.imgPalavraInglesEditar)
    public void editarIngles() {

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

                if(!(editar_palavra.getText().toString().equals("") || editar_palavra.getText().toString().equals(null)) ) {

                      palavraInglesAux = editar_palavra.getText().toString();

                    boolean alterouCoSucesso =  gravaOuAltera(editar_palavra.getText().toString());

                    if (alterouCoSucesso) {
                        palavraInglesAux = editar_palavra.getText().toString();
                        txtInglesAlterar.setText(palavraInglesAux);
                        cadastrou = false;
                    }
                    dialog.dismiss();
                }
                else {
                    Mensagem.toast(CadastrarNovaPalavraActivity.this,"Campo não pode ser vazio.").show();
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
}
