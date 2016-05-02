package wordeasy.br.com.wordeasy.interfaces.presenter;

import wordeasy.br.com.wordeasy.view.dominio.Palavra;

public interface RetornoPresenteOperacaoPalavra {
    void onPalavraCadastrada(Palavra palavra);
    void onPalavraAlterada(Palavra palavra);
    void onError(String msgError, int operacao);
    void onCampoNaoPreenchido(String result);
}
