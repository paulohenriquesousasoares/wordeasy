package wordeasy.br.com.wordeasy.interfaces.view;

import android.content.Context;

import java.util.ArrayList;

import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface ViewOperacaoRequisitaEstudar extends ViewOperacaoRequisita {
    Usuario getUsuarioAtual(Context context);
    void preenchePalavraListaEstudar(ArrayList<Palavra> palavras);
}
