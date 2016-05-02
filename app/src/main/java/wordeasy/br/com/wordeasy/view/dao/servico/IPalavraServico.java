package wordeasy.br.com.wordeasy.view.dao.servico;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;

public interface IPalavraServico {
    void create(Palavra palavra) throws Exception;
    String validaPalavra(Palavra palavra);
}
