package tesla.andrew.mainreed.presentation.application;

import com.facebook.stetho.Stetho;

import android.app.Application;

import tesla.andrew.mainreed.BuildConfig;
import tesla.andrew.mainreed.presentation.di.AppComponent;
import tesla.andrew.mainreed.presentation.di.AppModule;
import tesla.andrew.mainreed.presentation.di.DaggerAppComponent;

/**
 * Created by TESLA on 23.07.2017.
 */

public class App extends Application {
    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
        if (BuildConfig.DEBUG) {
            stethoInit();
        }
    }

    private void initComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    private void stethoInit() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

    }
}
