package tesla.andrew.mainreed.data.datasource.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tesla.andrew.mainreed.data.entity.NewsResponse;

/**
 * Created by TESLA on 22.07.2017.
 */

public interface RestApi {
    @GET("v1/articles")
    Observable<NewsResponse> getArticles(@Query("source") String source,
                                         @Query("sortBy") String sortBy,
                                         @Query("apiKey") String apiKey);
}
