package wordeasy.br.com.wordeasy.view.dao.servico;


import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface IUsuarioServico {
    Usuario autenticacao(String usuarioNome, String usuarioPassword);
    void create(Usuario usuario) throws Exception;
    String validaUsuario(Usuario usuario);
}
