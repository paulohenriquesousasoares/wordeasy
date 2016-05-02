package wordeasy.br.com.wordeasy.interfaces.model;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;

public interface ModelOperacaoPalavra {
    void inserePalavra(Palavra Palavra,int tipoOperacao);
    boolean alteraPalavra(Palavra palavra,boolean validarCampo);
}
