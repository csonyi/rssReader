package hu.elte.projekteszkozok.rssreader;

import java.util.List;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class RssFeedAdapter extends RecyclerView.Adapter<RssFeedAdapter.ViewHolder> {
    private List<ArticleDataModel> mValues;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView desc;
        public TextView pubDate;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            title =  v.findViewById(R.id.title);
            desc =  v.findViewById(R.id.desc);
            pubDate = v.findViewById(R.id.pubDate);
        }
    }

    public void add(int position, ArticleDataModel item) {
        mValues.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    public RssFeedAdapter(List<ArticleDataModel> dataset) {
        mValues = dataset;
    }

    @Override
    public RssFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ArticleDataModel data = mValues.get(position);
        holder.title.setText(data.getTitle());
        holder.desc.setText(data.getDesc());
        holder.pubDate.setText(data.getPubDate());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
