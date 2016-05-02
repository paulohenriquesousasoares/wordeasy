package wordeasy.br.com.wordeasy.interfaces.presenter;

import android.app.Activity;
import android.content.Context;

import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface PresenterOperacaoConfiguracao {
    void criaConfiguracao(Configuracao configuracao);
    void getConfiguracaoUsuarioAtual(Usuario usuario);
    Usuario getUsuairoAtual(Context context);


}
