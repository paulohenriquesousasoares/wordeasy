package wordeasy.br.com.wordeasy.view.dao.contrato;

import io.realm.RealmResults;
import wordeasy.br.com.wordeasy.view.dominio.Configuracao;

public interface IConfiguracaoRepositorio extends IRepositorioBase<Configuracao> {
    RealmResults<Configuracao> get();
    void delete(long id);

}
