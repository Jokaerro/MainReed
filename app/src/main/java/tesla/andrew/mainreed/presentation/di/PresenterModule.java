package tesla.andrew.mainreed.presentation.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tesla.andrew.mainreed.presentation.screen.main.MainPresenter;
import tesla.andrew.mainreed.presentation.screen.subscribes.SubscribesPresenter;

/**
 * Created by TESLA on 23.07.2017.
 */

@Module
public class PresenterModule {
    @Provides
    @Singleton
    MainPresenter provideMainPresenter() {
        return new MainPresenter();
    }

    @Provides
    @Singleton
    SubscribesPresenter provideSubscribePresenter() {
        return new SubscribesPresenter();
    }
}
