package wordeasy.br.com.wordeasy.interfaces.presenter;

import android.content.Context;

import wordeasy.br.com.wordeasy.view.dao.repositorio.UsuarioRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaUsuario;

public interface PresenterOperacaoUsuario {
    void onConfigurationChanged(ViewOperacaoRequisitaUsuario view);
    void novoUsuario(Usuario usuario);
}
