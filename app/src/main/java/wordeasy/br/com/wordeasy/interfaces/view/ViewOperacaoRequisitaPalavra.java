package wordeasy.br.com.wordeasy.interfaces.view;

import wordeasy.br.com.wordeasy.view.dominio.Palavra;

public interface ViewOperacaoRequisitaPalavra extends ViewOperacaoRequisita {
    void campoNaoPreenchido(String result);
    void palavrasCadastradas(Palavra palavra);
    void palavraAlterada(Palavra palavra);
}
