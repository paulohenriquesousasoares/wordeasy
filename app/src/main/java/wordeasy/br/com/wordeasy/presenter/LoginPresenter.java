package wordeasy.br.com.wordeasy.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import java.lang.ref.WeakReference;
import wordeasy.br.com.wordeasy.view.activity.MainActivity;
import wordeasy.br.com.wordeasy.view.dao.repositorio.ConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoLogin;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenteOperacaoLogin;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenterOperacaoLogin;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisita;
import wordeasy.br.com.wordeasy.model.LoginModel;
import wordeasy.br.com.wordeasy.view.util.Utilitario;


public class LoginPresenter implements RetornoPresenterOperacaoLogin, PresenteOperacaoLogin {

    private WeakReference<ViewOperacaoRequisita> mView;
    private ModelOperacaoLogin mModel;
    private ConfiguracaoRepositorio configuracaoRepositorio;
    private Context context;


    public LoginPresenter(ViewOperacaoRequisita view, Context activity) {
        this.mView = new WeakReference<ViewOperacaoRequisita>(view);
        this.mModel = new LoginModel(this);
        configuracaoRepositorio = new ConfiguracaoRepositorio();
        this.context = activity;
    }

    @Override
    public void onConfigurationChanged(ViewOperacaoRequisita view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void realizarLogin(final String userName, final String password) {

        final ProgressDialog dialog =  new ProgressDialog(context);
        dialog.setMessage("Aguarde...");
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mModel.entrar(userName, password);
                dialog.dismiss();
            }
        },1000);
    }

    @Override
    public void onLogado(Usuario usuario) {

        try{

            if (usuario.getId() > 0) {
                Utilitario.salvaInSharedPreferenceUsuario((Activity) context, usuario);
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra(Usuario.ID, true);
                context.startActivity(intent);
                ((Activity) context).finish();

                Configuracao config =   getConfiguracao();

                //se menor que -1 significa que ainda nao cadastrou nenhuma configuracao
                if (config.getUsuarioId() < 1) {

                    Configuracao configuracao = new Configuracao();

                    configuracao.setItensPorSessaoRevisao(5);
                    configuracao.setItensPorSessaoEstudo(5);
                    configuracao.setHora(13);
                    configuracao.setMinuto(00);
                    configuracao.setUsuarioId(usuario.getId());
                    configuracao.setAtivo(true);
                    configuracaoRepositorio.create(configuracao);
                }
            } else {
                mView.get().showSnackBar("Usuário ou senha inválidos");
            }

        }catch (Exception e){
            mView.get().showToast("" + e);
        }
    }

    @Override
    public void onError(String errorMsg) {
        mView.get().showToast(errorMsg);
    }


    private Configuracao getConfiguracao() {
        Configuracao config = null;
        try {
            config =
            configuracaoRepositorio.getConfiguracao(Utilitario.getSharedPreferenceUsuario((Activity) context).getId());
        } catch (Exception e) {
            mView.get().showToast("Error ao recuperar as configurações. " + e);
        }
        return  config;
    }
}
