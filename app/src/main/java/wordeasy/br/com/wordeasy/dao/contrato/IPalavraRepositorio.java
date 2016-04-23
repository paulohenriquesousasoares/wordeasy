package wordeasy.br.com.wordeasy.dao.contrato;

import java.util.ArrayList;
import wordeasy.br.com.wordeasy.dominio.Palavra;

public interface IPalavraRepositorio  extends IRepositorioBase<Palavra>{
    ArrayList<Palavra> get(long userId) throws Exception;
    void delete(long id) throws Exception;
}
