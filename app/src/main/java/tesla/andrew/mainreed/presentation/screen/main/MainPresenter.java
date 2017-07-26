package tesla.andrew.mainreed.presentation.screen.main;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tesla.andrew.mainreed.data.datasource.Repository;
import tesla.andrew.mainreed.domain.entity.Article;
import tesla.andrew.mainreed.presentation.application.App;
import tesla.andrew.mainreed.presentation.screen.base.BasePresenter;

/**
 * Created by TESLA on 23.07.2017.
 */

public class MainPresenter extends BasePresenter<MainView> {

    @Inject
    public Repository repository;

    public MainPresenter(){
        App.getAppComponent().injectMainPresenter(this);
    }

    public void getArticles() {
        view.showProgressDialog();
        repository.getArticles(null)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Article>>() {
                    @Override
                    public void accept(@NonNull List<Article> articles) throws Exception {
                        view.updateFeed(articles);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        view.errorHandling();
                    }
                });
    }

    public void updateFeed() {
        view.showProgressDialog();
        repository.updateFeed()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Article>>() {
                    @Override
                    public void accept(@NonNull List<Article> articles) throws Exception {
                        view.updateFeed(articles);
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
