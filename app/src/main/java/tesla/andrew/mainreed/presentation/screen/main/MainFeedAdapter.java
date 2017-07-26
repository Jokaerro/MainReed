package tesla.andrew.mainreed.presentation.screen.main;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import tesla.andrew.mainreed.R;
import tesla.andrew.mainreed.domain.entity.Article;
import tesla.andrew.mainreed.presentation.screen.article.ArticleActivity;

/**
 * Created by TESLA on 24.07.2017.
 */

public class MainFeedAdapter extends RecyclerView.Adapter<MainFeedAdapter.ViewHolder> {

    private static List<Article> data;
    Context context;

    public MainFeedAdapter(Context context, List<Article> data) {
        this.context = context;
        if (data != null)
            this.data = new ArrayList<>(data);
        else this.data = new ArrayList<>();
    }

    public void swapData(List<Article> data){
        if (data != null) {
            for (int i = 0; i < data.size(); i++)
                if (data.get(i) == null)
                    data.remove(i);
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.feed_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(data.get(position) != null) {
            holder.title.setText(data.get(position).getTitle());
            holder.source.setText(data.get(position).getSubscribeKey());

            String string = data.get(position).getPublishedAt();
            if(string != null && !string.isEmpty()) {
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Date date = dateFormat.parse(string);
                    DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    String dateStr = formatter.format(date);

                    holder.published.setText(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if(data.get(position).getUrlToImage() != null && !data.get(position).getUrlToImage().isEmpty()) {
                Picasso.with(context).load(data.get(position).getUrlToImage())
                        .error(R.drawable.missed_photo)
                        .placeholder(R.drawable.missed_photo)
                        .centerCrop()
                        .resize(200, 200)
                        .into(holder.picture);
            } else {
                holder.picture.setImageResource(R.drawable.missed_photo);
            }
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArticleActivity.start(context, data.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title) TextView title;
        @BindView(R.id.source) TextView source;
        @BindView(R.id.published) TextView published;
        @BindView(R.id.picture) ImageView picture;
        @BindView(R.id.newsCard) CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
