package wordeasy.br.com.wordeasy.interfaces.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

public interface PresenteOperacaoMain {
    void getAllPalavras(long userId, int opcaoPesquisa);
    void criaMenu(Activity context, Bundle saveInstanceState, String nameUsuario, String email,Toolbar mtoolbar,Drawer.Result navigationDrawerLeft);
}
