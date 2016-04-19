package wordeasy.br.com.wordeasy.dao.contrato;

import io.realm.RealmResults;
import wordeasy.br.com.wordeasy.dominio.Configuracao;

public interface IConfiguracaoRepositorio extends IRepositorioBase<Configuracao> {
    RealmResults<Configuracao> get();
    void delete(long id);

}
