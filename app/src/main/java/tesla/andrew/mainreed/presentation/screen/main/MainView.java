package tesla.andrew.mainreed.presentation.screen.main;

import java.util.List;

import tesla.andrew.mainreed.domain.entity.Article;

/**
 * Created by TESLA on 24.07.2017.
 */

public interface MainView {
    void updateFeed(List<Article> articles);

    void showProgressDialog();

    void hideProgressDialog();

    void errorHandling();
}
