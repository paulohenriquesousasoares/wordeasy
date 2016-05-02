package wordeasy.br.com.wordeasy.interfaces.presenter;

import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisita;

public interface PresenteOperacaoLogin {
    void onConfigurationChanged(ViewOperacaoRequisita view);
    void realizarLogin(String userName, String password);
}
