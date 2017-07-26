package tesla.andrew.mainreed.domain.entity;

import java.io.Serializable;

/**
 * Created by TESLA on 22.07.2017.
 */

public class Article implements Serializable {
    String author;
    String title;
    String description;
    String url;
    String urlToImage;
    String publishedAt;
    String subscribeKey;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSubscribeKey() {
        return subscribeKey;
    }

    public void setSubscribeKey(String subscribeKey) {
        this.subscribeKey = subscribeKey;
    }

    public Article(String author, String title, String description, String url, String urlToImage,
                   String publishedAt, String subscribeKey) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.subscribeKey = subscribeKey;
    }
}
