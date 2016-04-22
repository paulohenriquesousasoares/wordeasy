package wordeasy.br.com.wordeasy.dao.repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

            String indice = p.getIndicePalavra().toUpperCase() != null ? p.getIndicePalavra().toUpperCase() : "ND";

            palavra.setIndicePalavra(indice);
            palavra.setFavorito(p.isFavorito());
            palavra.setUsuario(p.getUsuario());
            palavra.setQtdAcertos(p.getQtdAcertos());
            palavra.setQtdErros(p.getQtdErros());
            palavra.setQtdVezesEstudou(p.getQtdVezesEstudou());

            palavraLista.add(palavra);
        }
        realm.close();
        return palavraLista;
    }

    public ArrayList<Palavra> get(int qtdPalavras) {

        realm =  Realm.getDefaultInstance();
        ArrayList<Palavra> palavraLista = new ArrayList<Palavra>();
        RealmResults<Palavra> resultPalavra = realm.where(Palavra.class).findAll();

        int count = resultPalavra.size();
        Random gerador = new Random();
        List<Integer> indicesjaAdicionadosAoEstudo = new ArrayList<Integer>();

        for (int i=0; i<qtdPalavras;i++) {
            int position =  gerador.nextInt(count);

            while (true) {
                if(!indicesjaAdicionadosAoEstudo.contains(position)) {
                    indicesjaAdicionadosAoEstudo.add(position);
                    break;
                }
                position = gerador.nextInt(count);
            }


            Palavra palavra = new Palavra();
            palavra.setId(resultPalavra.get(position).getId());
            palavra.setPalavraEmIngles(resultPalavra.get(position).getPalavraEmIngles());
            palavra.setPalavraEmPortugues(resultPalavra.get(position).getPalavraEmPortugues());
            palavra.setIndicePalavra(resultPalavra.get(position).getIndicePalavra().toUpperCase());
            palavra.setFavorito(resultPalavra.get(position).isFavorito());
            palavra.setUsuario(resultPalavra.get(position).getUsuario());

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
    public Palavra getById(long id) throws Exception{

        realm = Realm.getDefaultInstance();

        Palavra palavra = new Palavra();

        RealmResults<Palavra> results = realm.where(Palavra.class).equalTo("id", id).findAll();

        for(Palavra u : results) {
            palavra.setId(u.getId()) ;
            palavra.setPalavraEmIngles(u.getPalavraEmIngles());
            palavra.setPalavraEmPortugues(u.getPalavraEmPortugues());
            palavra.setIndicePalavra(u.getIndicePalavra());
            palavra.setFavorito(u.isFavorito());
            palavra.setUsuario(palavra.getUsuario());
            palavra.setQtdErros(u.getQtdErros());
            palavra.setQtdAcertos(u.getQtdAcertos());
            palavra.setQtdVezesEstudou(u.getQtdVezesEstudou());
        }

        realm.close();
        return palavra;
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
    public void update(Palavra object) { }
}
