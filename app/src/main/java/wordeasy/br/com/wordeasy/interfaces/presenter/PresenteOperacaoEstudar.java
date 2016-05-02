package wordeasy.br.com.wordeasy.interfaces.presenter;

import android.content.Context;

import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface PresenteOperacaoEstudar {
    void getPalavrasEstudar(boolean takeAllPalavras,long userId, int opcaoCardOuNaoEstudar,int itensPorSessaoEstudo) throws Exception;
    Usuario getUsuarioAtual(Context context) throws Exception;

}

