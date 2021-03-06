package wordeasy.br.com.wordeasy.view.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.interfaces.ImenuDrawer;

public class MenuDrawer implements ImenuDrawer {

    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;

    @Override
    public void header(final Activity activity, Bundle savedInstanceState,String userNome,String userEmail) {
        headerNavigationLeft = new AccountHeader()
                .withActivity(activity)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(false)
                .withHeaderBackground(R.drawable.logo_menu_drawer)
                .withCurrentProfileHiddenInList(false)
                .withProfileImagesVisible(false)
                .addProfiles(new ProfileDrawerItem().withName(userNome).withEmail(userEmail)).withSelectionListEnabled(false)
//                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
//                    @Override
//                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
//                        Mensagem.toast(activity,"Em breve").show();
//                        //headerNavigationLeft.setBackgroundRes(R.drawable.vyron);
//                        return false;
//                    }
//                })
                .build();
    }

    @Override
    public Drawer.Result drawerMenu(Activity activity, Toolbar toolbar, Bundle savedInstanceState, int positionInitial) {
        navigationDrawerLeft = new Drawer()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(positionInitial)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerNavigationLeft)
                    /*.withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                        @Override
                        public boolean onNavigationClickListener(View view) {
                            return false;
                        }
                    })*/
                        //.withOnDrawerItemClickListener(null)
                        //.withOnDrawerItemLongClickListener(null)
                .build();
        return  navigationDrawerLeft;
    }

    @Override
    public void navigationItem(Activity activity, Drawer.Result navigationDrawerLeft) {
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Configurações").withIcon(activity.getResources().getDrawable(R.drawable.ic_settings_applications_black_24dp)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Meus dados").withIcon(activity.getResources().getDrawable(R.drawable.ic_perm_contact_calendar_black_24dp)));
        //navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Baixar estudo"));
        //navigationDrawerLeft.addItem(new DividerDrawerItem());
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Encerrar sessão").withIcon(activity.getResources().getDrawable(R.drawable.ic_exit_to_app_black_24dp)));
    }
}
