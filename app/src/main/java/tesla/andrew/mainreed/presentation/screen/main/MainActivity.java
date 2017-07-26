package tesla.andrew.mainreed.presentation.screen.main;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tesla.andrew.mainreed.R;
import tesla.andrew.mainreed.domain.entity.Article;
import tesla.andrew.mainreed.presentation.application.App;
import tesla.andrew.mainreed.presentation.screen.subscribes.SubscribesActivity;

public class MainActivity extends AppCompatActivity implements MainView {

    private List<Article> articles = new ArrayList<>();
    private ProgressDialog progressDialog;
    private MainFeedAdapter adapter;

    @Inject
    MainPresenter presenter;

    @BindView(R.id.mainFeed) RecyclerView recycler;

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        App.getAppComponent().injectMainActivity(this);
        presenter.setView(this);
        initMainFeed();

        presenter.getArticles();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.updateFeed();
            }
        });
    }

    private void initMainFeed() {
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recycler.setLayoutManager(llm);

        adapter = new MainFeedAdapter(this, articles);
        recycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_subscribes:
                SubscribesActivity.start(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateFeed(List<Article> articles) {
        adapter.swapData(articles);
        swipeRefresh.setRefreshing(false);
        hideProgressDialog();
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
        }
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void errorHandling() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        swipeRefresh.setRefreshing(false);
        hideProgressDialog();
    }
}
