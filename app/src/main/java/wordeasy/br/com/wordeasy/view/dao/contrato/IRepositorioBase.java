package wordeasy.br.com.wordeasy.view.dao.contrato;

public interface IRepositorioBase<T> {
    T getById(long id) throws Exception;
    void create(T object) throws Exception;
    void update(T object);
}
