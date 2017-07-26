package tesla.andrew.mainreed.presentation.di;

import com.squareup.picasso.Picasso;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TESLA on 23.07.2017.
 */

@Module
public class AppModule {
    private Context context;
    private Picasso picasso;

    public AppModule(Context context) {
        this.context = context;
        this.picasso = Picasso.with(context);
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    Picasso providePicasso() {
        return picasso;
    }
}
