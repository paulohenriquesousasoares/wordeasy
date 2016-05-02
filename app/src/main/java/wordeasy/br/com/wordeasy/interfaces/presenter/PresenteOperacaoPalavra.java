package wordeasy.br.com.wordeasy.interfaces.presenter;

import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaUsuario;

public interface PresenteOperacaoPalavra {
    void onConfigurationChanged(ViewOperacaoRequisitaUsuario view);
    void novaPalavra(Palavra palavra, int tipoOperacao);
    boolean alteraPalavra(Palavra palavra,boolean validarCampo);

}
