package wordeasy.br.com.wordeasy.interfaces.presenter;

import java.util.ArrayList;

import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface RetornoPresenteOperacaoEstudar {
    void onTakePalavraEstudar(ArrayList<Palavra> palavras);
    void onError(String msgError);

}
