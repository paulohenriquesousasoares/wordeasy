package wordeasy.br.com.wordeasy.view.aplication;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import wordeasy.br.com.wordeasy.view.migration.MigrationData;

/**
 * Created by useradmin on 14/04/2016.
 */
public class CustomAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("word_easy.realm")
//                .deleteRealmIfMigrationNeeded()
                .schemaVersion(MigrationData.VERSION)
                .migration(new MigrationData())
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);


    }
}
