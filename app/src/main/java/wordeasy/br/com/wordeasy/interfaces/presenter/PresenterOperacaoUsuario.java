package wordeasy.br.com.wordeasy.interfaces.presenter;

import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaUsuario;

public interface PresenterOperacaoUsuario {
    void onConfigurationChanged(ViewOperacaoRequisitaUsuario view);
    void novoUsuario(Usuario usuario);
}
