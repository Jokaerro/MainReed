package tesla.andrew.mainreed.presentation.screen.subscribes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tesla.andrew.mainreed.R;
import tesla.andrew.mainreed.domain.entity.Subscribe;
import tesla.andrew.mainreed.presentation.application.App;

public class SubscribesActivity extends AppCompatActivity implements SubscribesView {
    private SubscribesAdapter adapter;
    private List<Subscribe> subscribes = new ArrayList<>();

    @Inject
    SubscribesPresenter presenter;

    @BindView(R.id.subscribes) RecyclerView recycler;

    public static void start(Context context) {
        Intent intent = new Intent(context, SubscribesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribes);

        ButterKnife.bind(this);
        App.getAppComponent().injectSubscribesActivity(this);

        presenter.setView(this);
        initRecycler();

        presenter.getSubscribes();
    }

    private void initRecycler() {
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recycler.setLayoutManager(llm);

        adapter = new SubscribesAdapter(this, subscribes, new SwitchCallback() {
            @Override
            public void onClicked(Subscribe item) {
                presenter.updateSubscribe(item);
            }
        });
        recycler.setAdapter(adapter);
    }

    @Override
    public void updateSubscribesList(List<Subscribe> subscribes) {
        adapter.swapData(subscribes);
    }

    @Override
    public void errorHandling() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    public interface SwitchCallback {
        void onClicked(Subscribe item);
    }
}
