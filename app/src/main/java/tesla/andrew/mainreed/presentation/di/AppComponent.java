package tesla.andrew.mainreed.presentation.di;

import javax.inject.Singleton;

import dagger.Component;
import tesla.andrew.mainreed.data.datasource.remote.RemoteDataSource;
import tesla.andrew.mainreed.presentation.screen.main.MainActivity;
import tesla.andrew.mainreed.presentation.screen.main.MainPresenter;
import tesla.andrew.mainreed.presentation.screen.subscribes.SubscribesActivity;
import tesla.andrew.mainreed.presentation.screen.subscribes.SubscribesPresenter;

/**
 * Created by TESLA on 23.07.2017.
 */

@Singleton
@Component(modules = {AppModule.class, PresenterModule.class, DataModule.class})
public interface AppComponent {
    void injectMainActivity(MainActivity activity);

    void injectSubscribesActivity(SubscribesActivity activity);

    void injectMainPresenter(MainPresenter mainPresenter);

    void injectSubscribesPresenter(SubscribesPresenter subscribesPresenter);

    void injectRemoteDatSource(RemoteDataSource remoteDataSource);
}
