package wordeasy.br.com.wordeasy.interfaces.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;

import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public interface ViewOperacaoRequisitaMain extends ViewOperacaoRequisita {
    void criaDrawerMenu(Bundle savedInstanceState);
    void aplicaListenerClickMenu(Drawer.Result navigationDrawerLeft);
    void preencheListaWithAllPalavras(ArrayList<Palavra> palavras,int qualOpcaoCarregou);
    void trocaLabelToolbar(String titulo);
    String alteraObjetoClicado(Palavra palavra, String textoClicado, int positionOpcaoClicado);
}
