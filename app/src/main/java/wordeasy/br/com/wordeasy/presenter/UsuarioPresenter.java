package wordeasy.br.com.wordeasy.presenter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.lang.ref.WeakReference;
import wordeasy.br.com.wordeasy.view.activity.MainActivity;
import wordeasy.br.com.wordeasy.view.dao.repositorio.ConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoUsuario;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenterOperacaoUsuario;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenteOperacaoUsuario;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaUsuario;
import wordeasy.br.com.wordeasy.model.UsuarioModel;
import wordeasy.br.com.wordeasy.view.util.Utilitario;

public class UsuarioPresenter implements  RetornoPresenteOperacaoUsuario, PresenterOperacaoUsuario {

    private WeakReference<ViewOperacaoRequisitaUsuario> mView;
    private ModelOperacaoUsuario mModel;
    private ConfiguracaoRepositorio configuracaoRepositorio;
    private Context context;


    public UsuarioPresenter(ViewOperacaoRequisitaUsuario view, Context context) {
        this.mView = new WeakReference<ViewOperacaoRequisitaUsuario>(view);
        this.context = context;
        this.mModel = new UsuarioModel(this, context);
        configuracaoRepositorio = new ConfiguracaoRepositorio();
    }

    @Override
    public void onConfigurationChanged(ViewOperacaoRequisitaUsuario view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void novoUsuario(final Usuario usuario) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Aguarde...");
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean alterando = usuario.getId() > 0 ? true : false;
                mModel.insereUsuario(usuario,alterando);
                dialog.dismiss();
            }
        },1000);
    }

    @Override
    public void onCadastrado(Usuario usuario,boolean estaAlterando) {

        if(estaAlterando) {
            Utilitario.deletaSharedPreferenceUsuario((Activity) context);

            //avisa para activity que chamou esta que os dados foram alterados
             Bundle bundle = new Bundle();
             bundle.putBoolean(Usuario.ID, estaAlterando);

             Intent it = new Intent();
             it.putExtras(bundle);
             ((Activity) context).setResult(((Activity) context).RESULT_OK, it);
        }
        else {
            context.startActivity(new Intent(context, MainActivity.class));
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

                try {
                    configuracaoRepositorio.create(configuracao);
                } catch (Exception e) {
                    mView.get().showSnackBar(""+e);
                }
            }
        }
          Utilitario.salvaInSharedPreferenceUsuario((Activity) context, usuario);
         ((Activity) context).finish();
    }

    @Override
    public void onError(String errorMsg) {
        mView.get().showSnackBar(errorMsg);
    }

    @Override
    public void onCampoNaoPreenchido(String result) {
        mView.get().campoNaoPreenchido(result);
    }


    private Configuracao getConfiguracao() {
        Configuracao config = null;
        configuracaoRepositorio = new ConfiguracaoRepositorio();
        try {
            config = configuracaoRepositorio.
            getConfiguracao(Utilitario.getSharedPreferenceUsuario((Activity) context).getId());
        } catch (Exception e) {
            mView.get().showSnackBar(""+e);
        }
        return  config;
    }
}
