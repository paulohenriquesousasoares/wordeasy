package wordeasy.br.com.wordeasy.interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;


public  interface ImenuDrawer {
    void header(Activity activity,Bundle savedInstanceState,String userNome,String userEmail);
    Drawer.Result drawerMenu(Activity activity, Toolbar toolbar, Bundle savedInstanceState, int positionInitial);
    void navigationItem(Activity activity,Drawer.Result navigationDrawerLeft);
}
