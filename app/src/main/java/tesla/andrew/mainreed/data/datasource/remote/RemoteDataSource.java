package tesla.andrew.mainreed.data.datasource.remote;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import tesla.andrew.mainreed.data.datasource.DataSource;
import tesla.andrew.mainreed.data.datasource.local.LocalDataSource;
import tesla.andrew.mainreed.domain.entity.Article;
import tesla.andrew.mainreed.data.entity.NewsResponse;
import tesla.andrew.mainreed.domain.entity.Subscribe;
import tesla.andrew.mainreed.presentation.application.App;
import tesla.andrew.mainreed.presentation.application.Config;

/**
 * Created by TESLA on 22.07.2017.
 */
@Singleton
public class RemoteDataSource implements DataSource {
    @Inject
    public RestApi restApi;

    public RemoteDataSource() {
        App.getAppComponent().injectRemoteDatSource(this);
    }

    @Override
    public Observable<List<Article>> getArticles(List<Subscribe> subscribes) {
        if (subscribes == null || subscribes.isEmpty()) {
            return Observable.just(new ArrayList<>());
        }
        return Observable.fromIterable(subscribes).concatMap(new Function<Subscribe, ObservableSource<List<Article>>>() {
            @Override
            public ObservableSource<List<Article>> apply(@io.reactivex.annotations.NonNull Subscribe subscribe) throws Exception {
                return getArticlesToSubscribe(subscribe);
            }
        }).toList().flatMapObservable(new Function<List<List<Article>>, ObservableSource<? extends List<Article>>>() {
            @Override
            public ObservableSource<? extends List<Article>> apply(@io.reactivex.annotations.NonNull List<List<Article>> lists) throws Exception {
                List<Article> articles = new ArrayList<Article>();
                for (List<Article> list : lists) {
                    articles.addAll(list);
                }
                return Observable.just(articles);
            }
        });
    }

    @Override
    public Observable<List<Article>> updateFeed() {
        return null;
    }

    private Observable<List<Article>> getArticlesToSubscribe(Subscribe subscribe) {
        return restApi
                .getArticles(subscribe.getSource(), Config.API_SORT, Config.API_KEY)
                .flatMap(new Function<NewsResponse, ObservableSource<List<Article>>>() {
                    @Override
                    public ObservableSource<List<Article>> apply(@io.reactivex.annotations.NonNull NewsResponse newsResponse) throws Exception {
                        return Observable.just(newsResponse.getArticles());
                    }
                }).doOnNext(new Consumer<List<Article>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<Article> articles) throws Exception {
                        for (Article article : articles) {
                            article.setSubscribeKey(subscribe.getSource());
                        }
                    }
                });
    }

    @Override
    public Observable<List<Subscribe>> getSubscribes(boolean all) {
        return null;
    }

    @Override
    public void updateSubscribe(@NonNull Subscribe subscribe) {
    }

    @Override
    public void updateArticle(@NonNull Article article) {
    }
}
