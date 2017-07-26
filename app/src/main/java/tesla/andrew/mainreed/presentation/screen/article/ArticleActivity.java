package tesla.andrew.mainreed.presentation.screen.article;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tesla.andrew.mainreed.R;
import tesla.andrew.mainreed.domain.entity.Article;

/**
 * Created by TESLA on 23.07.2017.
 */

public class ArticleActivity extends AppCompatActivity {
    private static final String ARTICLE = "article";

    public static void start(Context context, Article article) {
        Intent intent = new Intent(context, ArticleActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(ARTICLE, article);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private Article currentArticle;

    @BindView(R.id.picture) ImageView picture;
    @BindView(R.id.article) TextView article;
    @BindView(R.id.published) TextView published;
    @BindView(R.id.source) TextView source;

    @OnClick(R.id.news)
    void newsClicked() {
        //TODO open url
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Bundle bundle = getIntent().getExtras();
        currentArticle = (Article) bundle.getSerializable(ARTICLE);

        ButterKnife.bind(this);

        article.setText(currentArticle.getDescription());
        published.setText(currentArticle.getPublishedAt());
        source.setText(currentArticle.getSubscribeKey());

        if(currentArticle.getUrlToImage() != null && !currentArticle.getUrlToImage().isEmpty()) {
            Picasso.with(this).load(currentArticle.getUrlToImage())
                    .error(R.drawable.missed_photo)
                    .placeholder(R.drawable.missed_photo)
                    .into(picture);
        } else {
            picture.setImageResource(R.drawable.missed_photo);
        }
    }
}
