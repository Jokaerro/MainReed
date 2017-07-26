package tesla.andrew.mainreed.data.datasource.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import tesla.andrew.mainreed.data.datasource.DataSource;
import tesla.andrew.mainreed.domain.entity.Article;
import tesla.andrew.mainreed.domain.entity.Subscribe;

import tesla.andrew.mainreed.data.datasource.local.SubscribeContract.SubscribeEntry;
import tesla.andrew.mainreed.data.datasource.local.NewsContract.ArticleEntry;
import tesla.andrew.mainreed.util.ClosableUtils;

/**
 * Created by TESLA on 22.07.2017.
 */
@Singleton
public class LocalDataSource implements DataSource {
    private DbHelper dbHelper;

    @Inject
    public LocalDataSource(@NonNull Context context) {
        dbHelper = new DbHelper(context);
    }

    @Override
    public Observable<List<Subscribe>> getSubscribes(boolean all) {
        return  Observable.fromCallable(new Callable<List<Subscribe>>() {
            @Override
            public List<Subscribe> call() throws Exception {
                return getSubscribesSync(all);
            }
        });
    }

    private List<Subscribe> getSubscribesSync(boolean all) {
        List<Subscribe> subscribeList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {


            db = dbHelper.getReadableDatabase();

            String[] projection = {
                    SubscribeEntry._ID,
                    SubscribeEntry.COLUMN_CAPTION,
                    SubscribeEntry.COLUMN_SOURCE,
                    SubscribeEntry.COLUMN_VISIBLE
            };

            String selection = SubscribeEntry.COLUMN_VISIBLE + " = ?";
            String[] selectionArgs = {"1"};

            if (all) {
                c = db.query(SubscribeEntry.TABLE_NAME, projection, null, null, null, null, null);
            } else {
                c = db.query(SubscribeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            }

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    String itemId = c.getString(c.getColumnIndexOrThrow(SubscribeEntry._ID));
                    String caption = c.getString(c.getColumnIndexOrThrow(SubscribeEntry.COLUMN_CAPTION));
                    String source = c.getString(c.getColumnIndexOrThrow(SubscribeEntry.COLUMN_SOURCE));
                    boolean visible = c.getInt(c.getColumnIndexOrThrow(SubscribeEntry.COLUMN_VISIBLE)) == 1;
                    Subscribe subscribe = new Subscribe(itemId, caption, source, visible);
                    subscribeList.add(subscribe);
                }
            }

        } finally {
            ClosableUtils.close(c);
            ClosableUtils.close(db);
        }

        return subscribeList;
    }

    @Override
    public Observable<List<Article>> getArticles(final List<Subscribe> subscribes) {
        return Observable.fromCallable(new Callable<List<Article>>() {
            @Override
            public List<Article> call() throws Exception {
                return getArticlesSync(subscribes);
            }
        });
    }

    @Override
    public Observable<List<Article>> updateFeed() {
        return null;
    }

    private List<Article> getArticlesSync(List<Subscribe> subscribes) {
        List<Article> newsList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = dbHelper.getReadableDatabase();

            String[] projection = {
                    ArticleEntry._ID,
                    ArticleEntry.COLUMN_AUTHOR,
                    ArticleEntry.COLUMN_TITLE,
                    ArticleEntry.COLUMN_DESCRIPTION,
                    ArticleEntry.COLUMN_URL,
                    ArticleEntry.COLUMN_URL_TO_IMAGE,
                    ArticleEntry.COLUMN_PUBLISHED_AT,
                    ArticleEntry.COLUMN_SUBSCRIBE_KEY
            };

            String[] selectionArgs = new String[subscribes.size()];

            for (int i = 0; i < subscribes.size(); i++){
                selectionArgs[i] = subscribes.get(i).getSource();
            }

            String selection = ArticleEntry.COLUMN_SUBSCRIBE_KEY + " IN (";
            for (int i = 0; i < subscribes.size(); i++) {
                if (i != subscribes.size() - 1)
                    selection += " ?,";
                else
                    selection += " ?)";
            }

            String orderBy = ArticleEntry._ID + " DESC";

            c = db.query(ArticleEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    String itemId = c.getString(c.getColumnIndexOrThrow(SubscribeEntry._ID));
                    String author = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_AUTHOR));
                    String title = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_TITLE));
                    String description = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_DESCRIPTION));
                    String url = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_URL));
                    String urlToImage = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_URL_TO_IMAGE));
                    String publishedAt = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_PUBLISHED_AT));
                    String subscribeKey = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_SUBSCRIBE_KEY));
                    Article article = new Article(author, title, description, url, urlToImage, publishedAt, subscribeKey);
                    newsList.add(article);
                }
            }
        } finally {
            ClosableUtils.close(c);
            ClosableUtils.close(db);
        }
        return newsList;

    }

    @Override
    public void updateSubscribe(@NonNull Subscribe subscribe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        boolean pin = true;
        if (subscribe.isVisible())
            pin = false;

        ContentValues values = new ContentValues();
        values.put(SubscribeEntry.COLUMN_VISIBLE, pin);

        String selection = SubscribeEntry._ID + " LIKE ?";
        String[] selectionArgs = {subscribe.getItemId()};

        db.update(SubscribeEntry.TABLE_NAME, values, selection, selectionArgs);

        db.close();

    }

    @Override
    public void updateArticle(@NonNull Article article) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ArticleEntry.COLUMN_AUTHOR, article.getAuthor());
        values.put(ArticleEntry.COLUMN_TITLE, article.getTitle());
        values.put(ArticleEntry.COLUMN_DESCRIPTION, article.getDescription());
        values.put(ArticleEntry.COLUMN_URL, article.getUrl());
        values.put(ArticleEntry.COLUMN_URL_TO_IMAGE, article.getUrlToImage());
        values.put(ArticleEntry.COLUMN_PUBLISHED_AT, article.getPublishedAt());
        values.put(ArticleEntry.COLUMN_SUBSCRIBE_KEY, article.getSubscribeKey());

        db.insert(ArticleEntry.TABLE_NAME, null, values);

        db.close();
    }
}
