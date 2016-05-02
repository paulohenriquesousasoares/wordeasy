package wordeasy.br.com.wordeasy.presenter;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoEstudar;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenteOperacaoEstudar;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenteOperacaoEstudar;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaEstudar;
import wordeasy.br.com.wordeasy.model.EstudarModel;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public class EstudarPresente implements RetornoPresenteOperacaoEstudar, PresenteOperacaoEstudar {

    private WeakReference<ViewOperacaoRequisitaEstudar> mView;
    private ModelOperacaoEstudar mModel;

    public EstudarPresente(ViewOperacaoRequisitaEstudar view) {
        this.mView = new WeakReference<ViewOperacaoRequisitaEstudar>(view);
        this.mModel = new EstudarModel(this);
    }

    /**
     *
     * @param takeAllPalavras true -  traz todas as palavras, incluindo as que estão no ja sei e no card personalizado
     *
     * @param userId  id usuario logado
     * @param opcaoCardOuNaoEstudar 0 - traz as palavras inseridas card personalizado ,
     *                              1 - traz as palavras inseridas ja se
     * @param itensPorSessaoEstudo informar o total de palavras a serem estudadas, esta informação esta na comfiguração
     */
    @Override
    public void getPalavrasEstudar(boolean takeAllPalavras,long userId, int opcaoCardOuNaoEstudar,int itensPorSessaoEstudo) {
        mModel.takePalavraEstudar(takeAllPalavras,userId,opcaoCardOuNaoEstudar,itensPorSessaoEstudo);
    }

    @Override
    public Usuario getUsuarioAtual(Context context) throws Exception{
        return mModel.getUsuario(context);
    }

    @Override
    public void onTakePalavraEstudar(ArrayList<Palavra> palavras) {
        mView.get().preenchePalavraListaEstudar(palavras);
    }


    @Override
    public void onError(String msgError) {
        mView.get().showSnackBar(msgError);
    }
}
