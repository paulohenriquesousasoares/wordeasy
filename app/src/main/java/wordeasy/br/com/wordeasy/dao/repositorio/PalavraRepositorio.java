package wordeasy.br.com.wordeasy.dao.repositorio;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import wordeasy.br.com.wordeasy.dao.contrato.IPalavraRepositorio;
import wordeasy.br.com.wordeasy.dominio.Configuracao;
import wordeasy.br.com.wordeasy.dominio.Palavra;
import wordeasy.br.com.wordeasy.dominio.Usuario;

public class PalavraRepositorio implements IPalavraRepositorio {

    private Realm realm;

    @Override
    public ArrayList<Palavra> get() throws Exception{

        ArrayList<Palavra> palavraLista = new ArrayList<Palavra>();

        realm =  Realm.getDefaultInstance();
        RealmResults<Palavra> resultPalavra = realm.where(Palavra.class).findAll();


        for(Palavra p : resultPalavra) {
            Palavra palavra = new Palavra();
            palavra.setId(p.getId());
            palavra.setPalavraEmIngles(p.getPalavraEmIngles());
            palavra.setPalavraEmPortugues(p.getPalavraEmPortugues());
            palavra.setIndicePalavra(p.getIndicePalavra().toUpperCase());
            palavra.setFavorito(p.isFavorito());
            palavra.setUsuario(p.getUsuario());

            palavraLista.add(palavra);
        }
        realm.close();
        return palavraLista;
    }

    @Override
    public void delete(long id) throws Exception{
        realm = Realm.getDefaultInstance();

        RealmResults<Palavra> results = realm.where(Palavra.class)
                .equalTo("PalavraId",id)
                .findAll();

        realm.beginTransaction();

        Palavra palavra = results.get(0);
        palavra.removeFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public Palavra getById(long id) {
        return null;
    }

    @Override
    public void create(Palavra palavra) throws Exception {

        realm = Realm.getDefaultInstance();

        long id;
        if( palavra.getId() < 1 ) {
            RealmResults<Palavra> result = realm.where(Palavra.class).findAll();
            result.sort("id", Sort.DESCENDING);

            //Pega o ultimo id inserido
            id = result.size() == 0 ? 1 : result.get(0).getId() + 1;
            palavra.setId(id);
        }

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(palavra);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void update(Palavra object) {

    }
}
