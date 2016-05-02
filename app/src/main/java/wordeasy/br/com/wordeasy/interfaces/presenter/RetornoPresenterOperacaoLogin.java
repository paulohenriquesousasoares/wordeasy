package wordeasy.br.com.wordeasy.interfaces.presenter;

import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface RetornoPresenterOperacaoLogin {
    void onLogado(Usuario usuario);
    void onError(String errorMsg);
}

