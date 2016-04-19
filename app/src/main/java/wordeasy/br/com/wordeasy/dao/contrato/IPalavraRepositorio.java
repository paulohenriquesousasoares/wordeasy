package wordeasy.br.com.wordeasy.dao.contrato;

import java.util.ArrayList;
import wordeasy.br.com.wordeasy.dominio.Palavra;

public interface IPalavraRepositorio  extends IRepositorioBase<Palavra>{
    ArrayList<Palavra> get() throws Exception;
    void delete(long id) throws Exception;
}
