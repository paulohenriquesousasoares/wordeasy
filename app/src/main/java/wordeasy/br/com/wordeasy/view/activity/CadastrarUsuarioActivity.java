package wordeasy.br.com.wordeasy.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenterOperacaoUsuario;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaUsuario;
import wordeasy.br.com.wordeasy.presenter.UsuarioPresenter;
import wordeasy.br.com.wordeasy.view.util.Utilitario;

public class CadastrarUsuarioActivity extends AppCompatActivity implements ViewOperacaoRequisitaUsuario {

    @Bind(R.id.toolbar)  Toolbar mToolbar;
    @Bind(R.id.input_layout_name_usuario) TextInputLayout inputName;
    @Bind(R.id.input_layout_email) TextInputLayout inputEmail;
    @Bind(R.id.input_layout_senha_actCadastrar) TextInputLayout inputSenha;
    @Bind(R.id.edt_nome_actCadastrar)  EditText nome;
    @Bind(R.id.edtEmail_actCadastrar)  EditText email;
    @Bind(R.id.edtSenha)  EditText senha;

    private Usuario usuario;
    private PresenterOperacaoUsuario mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_usuario);
        ButterKnife.bind(this);
        inicializarViewsTela();

        if( getIntent().getExtras() != null) {
            if( getIntent().getExtras().getBoolean(Usuario.ID)) {
                mToolbar.setTitle(R.string.atualizarDados);
                usuario = Utilitario.getSharedPreferenceUsuario(CadastrarUsuarioActivity.this);
                nome.setText(usuario.getNome());
                email.setText(usuario.getEmail());
                senha.setText(usuario.getSenha());
            }
        }
    }

    //listener
    public void onclick_salvarUsuario(View view) {
        usuario.setNome(  nome.getText().toString()  );
        usuario.setEmail( email.getText().toString() );
        usuario.setSenha( senha.getText().toString() );

        mPresenter.novoUsuario(usuario);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(String message) { }

    @Override
    public void showSnackBar(String message) {}

    @Override
    public void inicializarViewsTela() {

        usuario = new Usuario();

        mToolbar.setTitle(R.string.novoUsuario);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {
            mPresenter = new UsuarioPresenter(this,CadastrarUsuarioActivity.this);
        } catch (Exception e){}
    }

    @Override
    public void campoNaoPreenchido(String result) {

        if(result.equals("nome")) {
            inputName.setError(getString(R.string.err_msg_nome));
            Utilitario.requestFocus(nome, CadastrarUsuarioActivity.this);
        }  else {
            inputName.setErrorEnabled(false);
        }

        if(result.equals("email")) {
            inputEmail.setError(getString(R.string.err_msg_email));
            Utilitario.requestFocus(email, CadastrarUsuarioActivity.this);
        }else {
            inputEmail.setErrorEnabled(false);
        }
        if(result.equals("senha")) {
            inputSenha.setError(getString(R.string.err_msg_senha));
            Utilitario.requestFocus(senha, CadastrarUsuarioActivity.this);
        } else {
            inputSenha.setErrorEnabled(false);
        }
    }
}
