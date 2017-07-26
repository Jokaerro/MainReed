package tesla.andrew.mainreed.presentation.screen.subscribes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tesla.andrew.mainreed.R;
import tesla.andrew.mainreed.domain.entity.Subscribe;
import tesla.andrew.mainreed.presentation.screen.subscribes.SubscribesActivity.SwitchCallback;

/**
 * Created by TESLA on 26.07.2017.
 */

public class SubscribesAdapter extends RecyclerView.Adapter<SubscribesAdapter.ViewHolder>  {

    private static List<Subscribe> data;
    Context context;
    SwitchCallback callBack;

    public SubscribesAdapter(Context context, List<Subscribe> data, SwitchCallback callBack) {
        this.context = context;
        this.callBack = callBack;
        if (data != null)
            this.data = new ArrayList<>(data);
        else this.data = new ArrayList<>();
    }

    public void swapData(List<Subscribe> data){
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
        final View view = LayoutInflater.from(context).inflate(R.layout.subscribe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(data.get(position) != null) {
            holder.title.setText(data.get(position).getCaption());
            holder.source.setText(data.get(position).getSource());
            holder.visible.setChecked(data.get(position).isVisible());
            holder.visible.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onClicked(data.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.caption)TextView title;
        @BindView(R.id.source) TextView source;
        @BindView(R.id.visible) Switch visible;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
