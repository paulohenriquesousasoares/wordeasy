package wordeasy.br.com.wordeasy.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenteOperacaoLogin;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisita;
import wordeasy.br.com.wordeasy.presenter.LoginPresenter;
import wordeasy.br.com.wordeasy.view.util.Mensagem;


public class LoginActivity extends AppCompatActivity implements ViewOperacaoRequisita {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.input_email)   TextView email;
    @Bind(R.id.input_senha)   TextView senha;

    private PresenteOperacaoLogin mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        inicializarViewsTela();
        startMVP();
    }

    //metodos

    private void startMVP() {
        try {
            inicializePresenter(this);
        } catch (Exception e) {
        }
    }

    private void inicializePresenter(ViewOperacaoRequisita view) throws Exception {
        mPresenter = new LoginPresenter(view, LoginActivity.this);
    }


    //listener

    public void onclick_realizarLogin(View v) {
        String _email = email.getText().toString();
        String _senha = senha.getText().toString();
        mPresenter.realizarLogin(_email, _senha);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    @Override
    public void showToast(String message) {
        Mensagem.toast(LoginActivity.this, message);
    }

    @Override
    public void showSnackBar(String message) {
        Mensagem.snackbar(message, findViewById(R.id.coordinator)).show();
    }

    @Override
    public void inicializarViewsTela() {
        mToolbar.setTitle(R.string.autenticar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }

}
