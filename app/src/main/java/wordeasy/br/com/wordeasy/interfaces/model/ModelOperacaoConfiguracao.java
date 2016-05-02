package wordeasy.br.com.wordeasy.interfaces.model;


import android.app.Activity;
import android.content.Context;

import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface ModelOperacaoConfiguracao {
    void insereConfiguracao(Configuracao configuracao);
    void getConguracao(Usuario usuario);
    Usuario getUsuarioAtual(Context context);
}
