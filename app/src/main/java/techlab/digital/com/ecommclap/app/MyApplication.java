package techlab.digital.com.ecommclap.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import techlab.digital.com.ecommclap.BuildConfig;
import timber.log.Timber;

public class MyApplication extends Application {


    @Override
    public void onCreate() {

        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

}