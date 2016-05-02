package wordeasy.br.com.wordeasy.interfaces.view;

import android.content.Context;

import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface ViewOperacaoRequisitaConfiguracao extends ViewOperacaoRequisita {
    void verificaSeUserTemConfiguracao(Usuario usuario);
    void criarAlarme(Configuracao configuracao);
    void preencheCampoConfiguracao(Configuracao configuracao);
    void getUsuario(Context context);
}
