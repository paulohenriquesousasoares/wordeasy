package wordeasy.br.com.wordeasy.dao.servico;


import wordeasy.br.com.wordeasy.dominio.Usuario;

public interface IUsuarioServico {
    Usuario autenticacao(String usuarioNome, String usuarioPassword);
    void create(Usuario usuario) throws Exception;
    String validaUsuario(Usuario usuario);
}
