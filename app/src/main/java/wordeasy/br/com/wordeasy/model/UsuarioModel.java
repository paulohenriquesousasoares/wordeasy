package wordeasy.br.com.wordeasy.model;

import wordeasy.br.com.wordeasy.view.dao.repositorio.ConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoUsuario;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenteOperacaoUsuario;
import wordeasy.br.com.wordeasy.view.servico.UsuarioServico;
import wordeasy.br.com.wordeasy.view.util.Constantes;

public class UsuarioModel implements ModelOperacaoUsuario {

    private RetornoPresenteOperacaoUsuario mPresente;
    private UsuarioServico usuarioServico;

    public UsuarioModel(RetornoPresenteOperacaoUsuario mPresente) {
        this.mPresente = mPresente;
        usuarioServico = new UsuarioServico();
    }

    @Override
    public void insereUsuario(Usuario usuario,boolean estaAlterando) {

        try {

            String result =  usuarioServico.validaUsuario(usuario);

            if(Constantes.OK.equals(result)) {
                usuarioServico.create(usuario);
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
