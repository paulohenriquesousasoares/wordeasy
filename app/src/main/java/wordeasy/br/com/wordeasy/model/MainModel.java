package wordeasy.br.com.wordeasy.model;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;

import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoMain;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenteOpercaoMain;
import wordeasy.br.com.wordeasy.view.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.util.MenuDrawer;

public class MainModel implements ModelOperacaoMain{

    private RetornoPresenteOpercaoMain mPresenteRetorno;
    private PalavraRepositorio palavraRepositorio;
    private ArrayList<Palavra> palavras;

    public MainModel(RetornoPresenteOpercaoMain mPresenteRetorno, Context context) {
        this.mPresenteRetorno = mPresenteRetorno;
        palavraRepositorio = new PalavraRepositorio(context);
        palavras = new ArrayList<Palavra>();
    }

    /**
     *
     * @param userId Id usuario
     * @param opcaoBuscado  0 =
     *                      1 =
     *                      3 =  pega todas as palavras cadastradas na base de dados
     */

    @Override
    public void getAllPalavras(long userId, int opcaoBuscado) {

        try {
            palavras  = palavraRepositorio.getAllPalavra(userId, opcaoBuscado);
            mPresenteRetorno.ongGetPalavras(palavras,opcaoBuscado);
        } catch (Exception e) {
            mPresenteRetorno.onError(""+e);
        }
    }

    @Override
    public void criaMenu(Activity context, Bundle saveInstanceState, String nameUsuario, String email,Toolbar mtoolbar,Drawer.Result navigationDrawerLeft) {

        MenuDrawer draweMenu = new MenuDrawer();
        draweMenu.header(context, saveInstanceState, nameUsuario, email);

        navigationDrawerLeft = draweMenu.drawerMenu(context, mtoolbar, saveInstanceState,-1);
        draweMenu.navigationItem(context, navigationDrawerLeft);

        mPresenteRetorno.onMenuDrawerCriado(navigationDrawerLeft);


    }
}
