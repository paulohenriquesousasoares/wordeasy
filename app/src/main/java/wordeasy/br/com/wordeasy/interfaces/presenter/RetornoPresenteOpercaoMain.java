package wordeasy.br.com.wordeasy.interfaces.presenter;

import android.os.Bundle;

import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;

import wordeasy.br.com.wordeasy.view.dominio.Palavra;

public interface RetornoPresenteOpercaoMain {
    void ongGetPalavras(ArrayList<Palavra> palavras,int qualOpcaoCarregou);
    void onError(String msgError);
    void onMenuDrawerCriado(Drawer.Result navigationDrawerLeft);
}
