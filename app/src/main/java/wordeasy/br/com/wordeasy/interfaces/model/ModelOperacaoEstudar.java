package wordeasy.br.com.wordeasy.interfaces.model;

import android.content.Context;

import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface ModelOperacaoEstudar {
    void takePalavraEstudar(boolean takeAllPalavras,long userId, int opcaoCardOuNaoEstudar,int itensPorSessaoEstudo);
    Usuario getUsuario(Context context) throws Exception;
}
