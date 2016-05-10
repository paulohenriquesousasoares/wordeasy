package wordeasy.br.com.wordeasy.view.dao.contrato;

import io.realm.RealmResults;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface IUsuarioRepositorio extends IRepositorioBase<Usuario> {

    Usuario getByUserEmailAndSenha(String usuarioEmail, String usuarioSenha) throws Exception;

    void delete(long id);
}
