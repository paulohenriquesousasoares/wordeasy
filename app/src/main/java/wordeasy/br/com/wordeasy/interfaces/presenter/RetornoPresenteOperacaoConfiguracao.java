package wordeasy.br.com.wordeasy.interfaces.presenter;


import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface RetornoPresenteOperacaoConfiguracao {
        void onConfiguracaoCriada(Configuracao configuracao);
        void onConfiguracaoUsuarioAtualRecuperada(Configuracao configuracao);
        void onError(String mshError);
        void criarAlarme(Configuracao configuracao);
}

