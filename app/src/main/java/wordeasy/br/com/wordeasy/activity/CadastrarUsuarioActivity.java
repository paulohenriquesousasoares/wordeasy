package wordeasy.br.com.wordeasy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.dao.repositorio.ConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.dominio.Configuracao;
import wordeasy.br.com.wordeasy.dominio.Usuario;
import wordeasy.br.com.wordeasy.servico.UsuarioServico;
import wordeasy.br.com.wordeasy.util.Mensagem;
import wordeasy.br.com.wordeasy.util.Utilitario;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)  Toolbar mToolbar;
    @Bind(R.id.input_layout_name_usuario) TextInputLayout inputName;
    @Bind(R.id.input_layout_email) TextInputLayout inputEmail;
    @Bind(R.id.input_layout_senha_actCadastrar) TextInputLayout inputSenha;
    @Bind(R.id.edt_nome_actCadastrar)  EditText nome;
    @Bind(R.id.edtEmail_actCadastrar)  EditText email;
    @Bind(R.id.edtSenha)  EditText senha;

    private Usuario usuario;
    private UsuarioServico usuarioServico;
    private ConfiguracaoRepositorio  configuracaoRepositorio;
    private  Realm realm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_usuario);
        ButterKnife.bind(this);
        initView();

        //boolean estaAtualizando = false;
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

    private Configuracao getConfiguracao() {
        Configuracao config = null;
        configuracaoRepositorio = new ConfiguracaoRepositorio();
        try {
            config = configuracaoRepositorio.getConfiguracao(Utilitario.getSharedPreferenceUsuario(CadastrarUsuarioActivity.this).getId());
        } catch (Exception e) {
            Mensagem.toast(CadastrarUsuarioActivity.this, "Error ao recuperar as configurações." + e);
        }
        return  config;
    }


    /*
    ===================================================================================================================
            METODOS
    ===================================================================================================================
     */

    void  initView() {

        realm = Realm.getDefaultInstance();
        usuario = new Usuario();
        usuarioServico = new UsuarioServico();

        mToolbar.setTitle(R.string.novoUsuario);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @OnClick(R.id.btn_gravar)
    public void cadastrarUsuario() {

        String label = "adicionando";

        if(usuario.getId() > 0) {
            label = "alterando";
        }

        usuario.setNome(  nome.getText().toString()  );
        usuario.setEmail( email.getText().toString() );
        usuario.setSenha( senha.getText().toString() );

        String result =  usuarioServico.validaUsuario(usuario);
        if(result.equals("ok")) {

            try{
                usuarioServico.create(usuario);
                clearCampos();
               // Utilitario.requestFocus(nome, CadastrarUsuarioActivity.this);
                Mensagem.snackbar("Usuario salvo com sucesso.",findViewById(R.id.cadastrarUsuario)).show();

                if(label.equals("alterando")) {
                     Utilitario.deletaSharedPreferenceUsuario(CadastrarUsuarioActivity.this);

                    //avisa para activity que chamou esta que os dados foram alterados
                     Bundle bundle = new Bundle();
                     bundle.putString("result", label);

                     Intent it = new Intent();
                     it.putExtras(bundle);
                     setResult(RESULT_OK, it);

                } else if (label.equals("adicionando")) {
                    startActivity(new Intent(CadastrarUsuarioActivity.this, MainActivity.class));

                    Configuracao config = getConfiguracao();


                    //se menor que -1 significa que ainda nao cadastrou nenhuma configuracao
                    if(config.getUsuarioId() < 1) {
                        Configuracao configuracao = new Configuracao();

                        configuracao.setItensPorSessaoRevisao(5);
                        configuracao.setItensPorSessaoEstudo(5);
                        configuracao.setHora(13);
                        configuracao.setMinuto(00);
                        configuracao.setUsuarioId(usuario.getId());
                        configuracao.setAtivo(true);
                        configuracaoRepositorio.create(configuracao);
                    }

                }

                Utilitario.salvaInSharedPreferenceUsuario(CadastrarUsuarioActivity.this, usuario);
                this.finish();

            }catch (Exception e) {
                Mensagem.toast(CadastrarUsuarioActivity.this,"Error ao persistir os dados "+ e.toString()).show();
            }
        }
        else {
            auxiliarValidaCampo(result);
        }
    }

    public void auxiliarValidaCampo(String result) {
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

    private void clearCampos() {
        nome.setText("");
        email.setText("");
        senha.setText("");
        inputName.setErrorEnabled(false);
        inputEmail.setErrorEnabled(false);
        inputSenha.setErrorEnabled(false);
    }

    /*
    ===================================================================================================================
            LISTENER
    ===================================================================================================================
     */

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
