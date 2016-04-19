package wordeasy.br.com.wordeasy.dao.contrato;

import java.util.List;

import io.realm.RealmResults;
import wordeasy.br.com.wordeasy.dominio.Usuario;

public interface IUsuarioRepositorio extends IRepositorioBase<Usuario> {

    Usuario getByUserEmailAndSenha(String usuarioEmail, String usuarioSenha) throws Exception;
    RealmResults<Usuario> get();
    void delete(long id);
}
