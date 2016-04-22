package wordeasy.br.com.wordeasy.dao.contrato;

import java.util.List;

public interface IRepositorioBase<T> {
    T getById(long id) throws Exception;
    void create(T object) throws Exception;
    void update(T object);
}
