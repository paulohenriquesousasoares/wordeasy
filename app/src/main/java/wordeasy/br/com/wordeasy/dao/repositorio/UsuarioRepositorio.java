package wordeasy.br.com.wordeasy.dao.repositorio;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import wordeasy.br.com.wordeasy.dao.contrato.IUsuarioRepositorio;
import wordeasy.br.com.wordeasy.dominio.Usuario;
import wordeasy.br.com.wordeasy.util.Utilitario;

public class UsuarioRepositorio  implements IUsuarioRepositorio  {

    private Realm realm;

    @Override
    public Usuario getByUserEmailAndSenha(String usuarioEmail, String usuarioSenha) throws Exception{

        realm = Realm.getDefaultInstance();

        Usuario usuario = new Usuario();

        RealmResults<Usuario> usuarios = realm.where(Usuario.class).equalTo("email", usuarioEmail).findAll();
        RealmResults<Usuario> usuario1 = realm.where(Usuario.class).equalTo("senha", usuarioSenha).findAll();

        if(usuarios.size() > 0 && usuario1.size() > 0 ) {
            if(usuarios.equals(usuario1)) {
                for(Usuario u : usuarios) {
                    usuario.setId(u.getId()) ;
                    usuario.setNome( u.getNome() );
                    usuario.setEmail( u.getEmail() );
                    usuario.setSenha( u.getSenha() );
                }
            }
        }
        realm.close();
        return usuario;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public RealmResults<Usuario> get() {
        return null;
    }

    @Override
    public Usuario getById(long id) {
        return null;
    }


    @Override
    public void create(Usuario usuario) throws  Exception{

        realm = Realm.getDefaultInstance();

        long id;
        if( usuario.getId() < 1 ) {
            RealmResults<Usuario> result = realm.where(Usuario.class).findAll();
            result.sort("id", Sort.DESCENDING);

            //Pega o ultimo id inserido
            id = result.size() == 0 ? 1 : result.get(0).getId() + 1;
            usuario.setId(id);
        }

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(usuario);
        realm.commitTransaction();

        realm.close();
    }


    @Override
    public void update(Usuario object) {

    }

}
