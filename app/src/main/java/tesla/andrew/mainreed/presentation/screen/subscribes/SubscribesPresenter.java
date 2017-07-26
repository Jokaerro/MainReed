package tesla.andrew.mainreed.presentation.screen.subscribes;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import tesla.andrew.mainreed.data.datasource.Repository;
import tesla.andrew.mainreed.domain.entity.Subscribe;
import tesla.andrew.mainreed.presentation.application.App;
import tesla.andrew.mainreed.presentation.screen.base.BasePresenter;

/**
 * Created by TESLA on 26.07.2017.
 */

public class SubscribesPresenter extends BasePresenter<SubscribesView> {
    @Inject
    public Repository repository;

    public SubscribesPresenter(){
        App.getAppComponent().injectSubscribesPresenter(this);
    }

    public void updateSubscribe(Subscribe subscribe) {
        repository.updateSubscribe(subscribe);
    }

    public void getSubscribes() {
        repository.getSubscribes(true)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Subscribe>>() {
                    @Override
                    public void accept(@NonNull List<Subscribe> subscribes) throws Exception {
                        view.updateSubscribesList(subscribes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        view.errorHandling();
                    }
                });
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
