package wordeasy.br.com.wordeasy.interfaces.presenter;

import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface RetornoPresenteOperacaoUsuario {
    void onCadastrado(Usuario usuario,boolean estaAlterando);
    void onError(String errorMsg);
    void onCampoNaoPreenchido(String result);

}
