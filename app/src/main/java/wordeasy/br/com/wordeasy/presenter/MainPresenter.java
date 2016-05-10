package wordeasy.br.com.wordeasy.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoMain;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenteOperacaoMain;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenteOpercaoMain;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaMain;
import wordeasy.br.com.wordeasy.model.MainModel;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.util.Constantes;
import wordeasy.br.com.wordeasy.view.util.MenuDrawer;
import wordeasy.br.com.wordeasy.view.util.Utilitario;

public class MainPresenter implements RetornoPresenteOpercaoMain, PresenteOperacaoMain {

    private WeakReference<ViewOperacaoRequisitaMain> mView;
    private ModelOperacaoMain mModel;

    public MainPresenter(ViewOperacaoRequisitaMain mView, Context context) {
        this.mView = new WeakReference<ViewOperacaoRequisitaMain>(mView);
        this.mModel = new MainModel(this, context);
    }

    @Override
    public void getAllPalavras(long userId, int opcaoPesquisa) {
       mModel.getAllPalavras(userId, opcaoPesquisa);
    }

    @Override
    public void criaMenu(Activity context, Bundle saveInstanceState, String nameUsuario, String email,Toolbar mtoolbar,Drawer.Result navigationDrawerLeft) {
        mModel.criaMenu( context, saveInstanceState, nameUsuario,  email, mtoolbar, navigationDrawerLeft);
    }

    /**
     *
     * @param palavras retorna as palavras que foram buscadas na base
     * @param qualOpcaoCarregou todas do card personalizado
     *                          todas do ja sei
     *                          todas em geral
     */
    @Override
    public void ongGetPalavras(ArrayList<Palavra> palavras, int qualOpcaoCarregou) {
        mView.get().preencheListaWithAllPalavras(palavras, qualOpcaoCarregou);
    }

    @Override
    public void onError(String msgError) {
        mView.get().showSnackBar(msgError);
    }

    /**
     * @param navigationDrawerLeft  este retorno onde fica o listener de clique para abrir e fechar o meu
     */
    @Override
    public void onMenuDrawerCriado(Drawer.Result navigationDrawerLeft) {
        mView.get().aplicaListenerClickMenu(navigationDrawerLeft);
    }
}
