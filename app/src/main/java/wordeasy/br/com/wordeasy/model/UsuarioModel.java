package wordeasy.br.com.wordeasy.model;

import android.content.Context;

import wordeasy.br.com.wordeasy.view.dao.repositorio.ConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.view.dao.repositorio.UsuarioRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoUsuario;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenteOperacaoUsuario;
import wordeasy.br.com.wordeasy.view.servico.UsuarioServico;
import wordeasy.br.com.wordeasy.view.util.Constantes;

public class UsuarioModel implements ModelOperacaoUsuario {

    private RetornoPresenteOperacaoUsuario mPresente;
    private UsuarioServico usuarioServico;
    private UsuarioRepositorio usuarioRepositorio;


    public UsuarioModel(RetornoPresenteOperacaoUsuario mPresente, Context context) {
        this.mPresente = mPresente;
        usuarioServico = new UsuarioServico();
        usuarioRepositorio = new UsuarioRepositorio(context);
    }

    @Override
    public void insereUsuario(Usuario usuario,boolean estaAlterando) {

        try {

            String result =  usuarioServico.validaUsuario(usuario);

            if(Constantes.OK.equals(result)) {
                long idInserido =  usuarioRepositorio.create(usuario);
                usuario.setId(idInserido);
                mPresente.onCadastrado(usuario,estaAlterando);
            }
            else {
                mPresente.onCampoNaoPreenchido(result);
            }
        } catch (Exception e) {
            mPresente.onError(""+e);
        }
    }
}
