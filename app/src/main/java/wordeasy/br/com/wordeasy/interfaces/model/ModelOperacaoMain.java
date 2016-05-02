package wordeasy.br.com.wordeasy.interfaces.model;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

public interface ModelOperacaoMain {
    void getAllPalavras(long userId, int opcaoBuscado);

    void criaMenu(Activity context, Bundle saveInstanceState, String nameUsuario, String email,Toolbar mtoolbar,Drawer.Result navigationDrawerLeft);
}
