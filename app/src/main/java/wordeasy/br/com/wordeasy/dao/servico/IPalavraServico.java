package wordeasy.br.com.wordeasy.dao.servico;
import wordeasy.br.com.wordeasy.dominio.Palavra;

public interface IPalavraServico {
    void create(Palavra palavra) throws Exception;
    String validaPalavra(Palavra palavra);
}
