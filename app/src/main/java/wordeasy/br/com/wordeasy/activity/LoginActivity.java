package wordeasy.br.com.wordeasy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.dao.repositorio.ConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.dao.repositorio.UsuarioRepositorio;
import wordeasy.br.com.wordeasy.dominio.Configuracao;
import wordeasy.br.com.wordeasy.dominio.Usuario;
import wordeasy.br.com.wordeasy.util.Mensagem;
import wordeasy.br.com.wordeasy.util.Utilitario;


public class LoginActivity extends AppCompatActivity{

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.input_email)  TextView email;
    @Bind(R.id.input_senha) TextView senha;

    private ConfiguracaoRepositorio configuracaoRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        initView();
    }

    /*  ===================================================================================================================
           METODOS
        ===================================================================================================================
    */

    void initView()  {

        mToolbar.setTitle(R.string.autenticar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        configuracaoRepositorio = new ConfiguracaoRepositorio();
    }


    @OnClick(R.id.btn_logar)
    public void Entrar() {

        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();

        String _email = email.getText().toString();
        String _senha = senha.getText().toString();

        try {

            Usuario user =  usuarioRepositorio.getByUserEmailAndSenha(_email, _senha);
            if(user.getId() > 0) {
                Utilitario.salvaInSharedPreferenceUsuario(LoginActivity.this, user);

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra(Usuario.ID,true);
                startActivity(intent);
                this.finish();

                Configuracao config = getConfiguracao();
                //se menor que -1 significa que ainda nao cadastrou nenhuma configuracao
                if(config.getUsuarioId() < 1) {
                    Configuracao configuracao = new Configuracao();

                    configuracao.setItensPorSessaoRevisao(5);
                    configuracao.setItensPorSessaoEstudo(5);
                    configuracao.setHora(13);
                    configuracao.setMinuto(00);
                    configuracao.setUsuarioId(user.getId());
                    configuracao.setAtivo( true );
                    configuracaoRepositorio.create(configuracao);
                }
            }
            else {
                Mensagem.snackbar("Usuário ou senha inválidos", findViewById(R.id.coordinator)).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Configuracao getConfiguracao() {
        Configuracao config = null;
        try {
            config = configuracaoRepositorio.getConfiguracao(Utilitario.getSharedPreferenceUsuario(LoginActivity.this).getId());
        } catch (Exception e) {
            Mensagem.toast(LoginActivity.this, "Error ao recuperar as configurações." + e);
        }
        return  config;
    }

     /*
    ===================================================================================================================
            LISTENER
    ===================================================================================================================
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }
}
