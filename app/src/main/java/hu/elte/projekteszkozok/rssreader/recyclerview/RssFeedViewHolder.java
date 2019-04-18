package hu.elte.projekteszkozok.rssreader.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import hu.elte.projekteszkozok.rssreader.R;

public class RssFeedViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView desc;
    TextView pubDate;
    View layout;

    public RssFeedViewHolder(View v) {
        super(v);
        layout = v;
        title =  v.findViewById(R.id.title);
        desc =  v.findViewById(R.id.desc);
        pubDate = v.findViewById(R.id.pubDate);
    }
}
