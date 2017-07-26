package tesla.andrew.mainreed.data.datasource;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import tesla.andrew.mainreed.domain.entity.Article;
import tesla.andrew.mainreed.domain.entity.Subscribe;

/**
 * Created by TESLA on 22.07.2017.
 */

public class Repository implements DataSource {
    private final DataSource remoteDataSource;
    private final DataSource localDataSource;

    @Inject
    public Repository(DataSource remoteDataSource, DataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Observable<List<Subscribe>> getSubscribes(boolean all) {
        return localDataSource.getSubscribes(all);
    }

    @Override
    public Observable<List<Article>> getArticles(List<Subscribe> subscribes) {
        Observable<List<Subscribe>> observable;
        if (subscribes!=null && !subscribes.isEmpty()){
            observable = Observable.just(subscribes);
        }else {
            observable = localDataSource.getSubscribes(false);
        }
        return observable.flatMap(new Function<List<Subscribe>, ObservableSource<List<Article>>>() {
            @Override
            public ObservableSource<List<Article>> apply(@io.reactivex.annotations.NonNull List<Subscribe> subscribes) throws Exception {
                Log.d("getSubscribes", String.valueOf(subscribes.size()));
                return getArticleNoSubscribeLoad(subscribes);
            }
        });
    }

    @Override
    public Observable<List<Article>> updateFeed() {
        Observable<List<Subscribe>> observable = localDataSource.getSubscribes(false);
        return observable.flatMap(new Function<List<Subscribe>, ObservableSource<List<Article>>>() {
            @Override
            public ObservableSource<List<Article>> apply(@io.reactivex.annotations.NonNull List<Subscribe> subscribes) throws Exception {
                Log.d("getSubscribes", String.valueOf(subscribes.size()));
                return getRemoteAndSave(subscribes);
            }
        });
    }

    @Override
    public void updateSubscribe(@NonNull Subscribe subscribe) {
        localDataSource.updateSubscribe(subscribe);
    }

    @Override
    public void updateArticle(@NonNull Article article) {
        localDataSource.updateArticle(article);
    }

    private Observable<List<Article>> getArticleNoSubscribeLoad(List<Subscribe> subscribes){
        return localDataSource.getArticles(subscribes)
                .flatMap(new Function<List<Article>, ObservableSource<List<Article>>>() {
                    @Override
                    public ObservableSource<List<Article>> apply(List<Article> articles) throws Exception {
                        if (articles == null || articles.isEmpty()){
                            return getRemoteAndSave(subscribes);
                        }
                        return Observable.just(articles);
                    }
                });
    }

    private Observable<List<Article>> getRemoteAndSave(List<Subscribe> subscribes){
        return remoteDataSource.getArticles(subscribes).doOnNext(new Consumer<List<Article>>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull List<Article> articles) throws Exception {
                for (Article article : articles){
                    localDataSource.updateArticle(article);
                }
            }
        });
    }
}
