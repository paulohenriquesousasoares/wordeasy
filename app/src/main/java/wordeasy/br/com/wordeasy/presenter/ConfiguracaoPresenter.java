package wordeasy.br.com.wordeasy.presenter;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;

import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoConfiguracao;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenterOperacaoConfiguracao;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenteOperacaoConfiguracao;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaConfiguracao;
import wordeasy.br.com.wordeasy.model.ConfiguracaoModel;
import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public class ConfiguracaoPresenter implements RetornoPresenteOperacaoConfiguracao, PresenterOperacaoConfiguracao{

    private WeakReference<ViewOperacaoRequisitaConfiguracao> mView;
    private ModelOperacaoConfiguracao mModel;

    public ConfiguracaoPresenter(ViewOperacaoRequisitaConfiguracao view) {
        mView = new WeakReference<ViewOperacaoRequisitaConfiguracao>(view);
        this.mModel = new ConfiguracaoModel(this);
    }


    @Override
    public void criaConfiguracao(Configuracao configuracao) {
        mModel.insereConfiguracao(configuracao);
    }

    @Override
    public void onConfiguracaoCriada(Configuracao configuracao) {
        mView.get().preencheCampoConfiguracao(configuracao);
        mView.get().showSnackBar("Configuração criada com sucesso");
    }


    @Override
    public void getConfiguracaoUsuarioAtual(Usuario usuario) {
        mModel.getConguracao(usuario);
    }

    @Override
    public Usuario getUsuairoAtual(Context context) {
        return mModel.getUsuarioAtual(context);
    }

    @Override
    public void onConfiguracaoUsuarioAtualRecuperada(Configuracao configuracao) {
        mView.get().preencheCampoConfiguracao(configuracao);
    }

    @Override
    public void onError(String msgError) {
        mView.get().showSnackBar(msgError);
    }

    @Override
    public void criarAlarme(Configuracao configuracao) {
        mView.get().criarAlarme(configuracao);
    }


}
