package tesla.andrew.mainreed.presentation.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tesla.andrew.mainreed.data.datasource.Repository;
import tesla.andrew.mainreed.data.datasource.local.LocalDataSource;
import tesla.andrew.mainreed.data.datasource.remote.RemoteDataSource;
import tesla.andrew.mainreed.data.datasource.remote.RestApi;
import tesla.andrew.mainreed.data.datasource.remote.RestApiCreator;
import tesla.andrew.mainreed.presentation.application.Config;

/**
 * Created by TESLA on 23.07.2017.
 */

@Module
public class DataModule {

    @Provides
    @Singleton
    RestApi provideRestApi() {
        return RestApiCreator.create(Config.API_BASE_URL);
    }

    @Provides
    @Singleton
    Repository provideRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        return new Repository(remoteDataSource, localDataSource);
    }

    @Provides
    @Singleton
    RemoteDataSource provideRemoteDataSource() {
        return new RemoteDataSource();
    }

    @Provides
    @Singleton
    LocalDataSource provideLocalDataSource(Context context) {
        return new LocalDataSource(context);
    }
}
