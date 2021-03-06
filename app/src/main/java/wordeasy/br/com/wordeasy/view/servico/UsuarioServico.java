package wordeasy.br.com.wordeasy.view.servico;

import wordeasy.br.com.wordeasy.view.dao.repositorio.UsuarioRepositorio;
import wordeasy.br.com.wordeasy.view.dao.servico.IUsuarioServico;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.util.Constantes;

public class UsuarioServico implements IUsuarioServico {

    private UsuarioRepositorio mUsuarioRepositorio;

    public UsuarioServico(){
        mUsuarioRepositorio = new UsuarioRepositorio();
    }


    @Override
    public Usuario autenticacao(String usuarioNome, String usuarioPassword) {
        return  null;
    }


    @Override
    public void create(Usuario usuario) throws  Exception{
        mUsuarioRepositorio.create(usuario);
    }

    @Override
    public String validaUsuario(Usuario usuario){

        if(! (usuario.getNome().trim().isEmpty() || usuario.getEmail().trim().isEmpty() || usuario.getSenha().trim().isEmpty() ) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(usuario.getEmail()).matches() == true) {
            return Constantes.OK;
        }
        else if(usuario.getNome().equals("") || usuario.getNome().equals(null)) {
            return  "nome";
        }
        else if(usuario.getEmail().equals("") ||
                usuario.getEmail().equals(null) ||
                android.util.Patterns.EMAIL_ADDRESS.matcher(usuario.getEmail()).matches() == false ) {
            return  "email";
        }
        else if(usuario.getSenha().equals("") || usuario.getSenha().equals(null)) {
            return  "senha";
        }
        return  null;
    }
}
