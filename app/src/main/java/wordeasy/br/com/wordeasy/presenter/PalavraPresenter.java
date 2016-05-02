package wordeasy.br.com.wordeasy.presenter;

import android.content.Context;

import java.lang.ref.WeakReference;

import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoPalavra;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenteOperacaoPalavra;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenteOperacaoPalavra;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaPalavra;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaUsuario;
import wordeasy.br.com.wordeasy.model.PalavraModel;
import wordeasy.br.com.wordeasy.view.util.Constantes;

public class PalavraPresenter implements PresenteOperacaoPalavra, RetornoPresenteOperacaoPalavra {

    private WeakReference<ViewOperacaoRequisitaPalavra> mView;
    private ModelOperacaoPalavra mModel;
    private Context context;

    public PalavraPresenter(ViewOperacaoRequisitaPalavra view, Context context) {
        this.mView = new WeakReference<ViewOperacaoRequisitaPalavra>(view);
        this.mModel = new PalavraModel(this);
        this.context = context;
    }


    @Override
    public void onConfigurationChanged(ViewOperacaoRequisitaUsuario view) { }


    @Override
    public void novaPalavra(Palavra palavra, int tipoOperacao) {
        mModel.inserePalavra(palavra,tipoOperacao);
    }


    @Override
    public boolean alteraPalavra(Palavra palavra,boolean validarCampo) {
       return   mModel.alteraPalavra(palavra,validarCampo);
    }


    @Override
    public void onPalavraCadastrada(Palavra palavra) {
        mView.get().palavrasCadastradas(palavra);
    }


    @Override
    public void onPalavraAlterada(Palavra palavra) {
        mView.get().palavraAlterada(palavra);
    }

    @Override
    public void onError(String msgError, int operacao) {

        if(operacao ==  Constantes.ALTERANDO)
            mView.get().showToast(msgError);
        else
            mView.get().showSnackBar(msgError);

    }

    @Override
    public void onCampoNaoPreenchido(String result) {
        mView.get().campoNaoPreenchido(result);
    }
}
