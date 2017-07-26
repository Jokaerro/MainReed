package tesla.andrew.mainreed.data.datasource;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import tesla.andrew.mainreed.domain.entity.Article;
import tesla.andrew.mainreed.domain.entity.Subscribe;

/**
 * Created by TESLA on 22.07.2017.
 */

public interface DataSource {
    Observable<List<Subscribe>> getSubscribes(boolean all);

    Observable<List<Article>> getArticles(List<Subscribe> subscribes);

    Observable<List<Article>> updateFeed();

    void updateSubscribe(@NonNull Subscribe subscribe);

    void updateArticle(@NonNull Article article);
}
