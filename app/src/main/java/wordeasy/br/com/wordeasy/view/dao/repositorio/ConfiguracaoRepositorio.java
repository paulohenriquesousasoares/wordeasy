package wordeasy.br.com.wordeasy.view.dao.repositorio;

import io.realm.Realm;
import io.realm.RealmResults;
import wordeasy.br.com.wordeasy.view.dao.contrato.IConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Configuracao;

/**
 * Created by useradmin on 15/04/2016.
 */
public class ConfiguracaoRepositorio implements IConfiguracaoRepositorio {

    private Realm realm;

    @Override
    public RealmResults<Configuracao> get() {
        return null;
    }

    @Override
    public void delete(long id) {

        realm = Realm.getDefaultInstance();

        RealmResults<Configuracao> results = realm.where(Configuracao.class)
                .equalTo("id",id)
                .findAll();

        realm.beginTransaction();

        Configuracao configuracao = results.get(0);
        configuracao.removeFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public Configuracao getById(long id) {
        return null;
    }

    @Override
    public void create(Configuracao configuracao) throws Exception {

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(configuracao);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void update(Configuracao object) {}

    public Configuracao getConfiguracao(long usuarioId) throws Exception{

        realm = Realm.getDefaultInstance();
        Configuracao configuracao = new Configuracao();

        try{
            RealmResults<Configuracao> config = realm.where(Configuracao.class)
                    .equalTo("usuarioId", usuarioId)
                    .findAll();

            for(Configuracao c : config) {
                configuracao.setId(c.getId());
                configuracao.setItensPorSessaoEstudo(c.getItensPorSessaoEstudo());
                configuracao.setItensPorSessaoRevisao(c.getItensPorSessaoRevisao());
                configuracao.setHora(c.getHora());
                configuracao.setMinuto(c.getMinuto());
                configuracao.setUsuarioId(c.getUsuarioId());
                configuracao.setAtivo(c.isAtivo());
            }
        }
        catch (Exception e){}

        realm.close();
        return configuracao;
    }
}
