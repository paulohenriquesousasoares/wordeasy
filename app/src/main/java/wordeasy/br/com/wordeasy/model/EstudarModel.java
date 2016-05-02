package wordeasy.br.com.wordeasy.model;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoEstudar;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenteOperacaoEstudar;
import wordeasy.br.com.wordeasy.view.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.util.Utilitario;

public class EstudarModel implements ModelOperacaoEstudar {

    private RetornoPresenteOperacaoEstudar mPresenteRetorno;
    private PalavraRepositorio palavraRepositorio;

    public EstudarModel(RetornoPresenteOperacaoEstudar mPresenteRetorno) {
        this.mPresenteRetorno = mPresenteRetorno;
        palavraRepositorio = new PalavraRepositorio();
    }


    /**
     *
     * @param takeAllPalavras true -  traz todas as palavras, incluindo as que estão no ja sei e no card personalizado
     *
     * @param userId  id usuario logado
     * @param opcaoCardOuNaoEstudar 0 - traz as palavras inseridas card personalizado ,
     *                              1 - traz as palavras inseridas ja sei
     * @param itensPorSessaoEstudo informar o total de palavras a serem estudadas, esta informação esta na comfiguração
     */
    @Override
    public void takePalavraEstudar(boolean takeAllPalavras, long userId, int opcaoCardOuNaoEstudar, int itensPorSessaoEstudo) {

        ArrayList<Palavra> palavraLista = new ArrayList<Palavra>();
        try {
            if(takeAllPalavras)
                palavraLista = palavraRepositorio.getAllPalavra(userId, opcaoCardOuNaoEstudar);
            else
                palavraLista = palavraRepositorio.get(itensPorSessaoEstudo, userId);

            mPresenteRetorno.onTakePalavraEstudar(palavraLista);

        } catch (Exception e) {
            mPresenteRetorno.onError(""+e);
        }

    }

    @Override
    public Usuario getUsuario(Context context) throws Exception {
        Usuario usuario = Utilitario.getSharedPreferenceUsuario((Activity) context);
        return  usuario;
    }
}
