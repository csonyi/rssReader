package hu.elte.projekteszkozok.rssreader.recyclerview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.elte.projekteszkozok.rssreader.R;
import hu.elte.projekteszkozok.rssreader.ReadActivity;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Article;

public class RssFeedAdapter extends RecyclerView.Adapter<RssFeedViewHolder> {
    private List<Article> mArticleList = new ArrayList<>();
    private Context mContext;

    public void add(int position, Article article) {
        mArticleList.add(position, article);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mArticleList.remove(position);
        notifyItemRemoved(position);
    }

    public RssFeedAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<Article> articleList) {
        mArticleList = articleList;
    }

    @Override
    public RssFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        return new RssFeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RssFeedViewHolder holder, final int position) {
        final Article article = mArticleList.get(position);
        holder.title.setText(article.getTitle());
        holder.desc.setText(article.getDesc());
        holder.pubDate.setText(article.getPubDate());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReadActivity.class);
                intent.putExtra("EXTRA_TITLE", article.getTitle());
                intent.putExtra("EXTRA_PUB_DATE", article.getPubDate());
                intent.putExtra("EXTRA_DESC", article.getDesc());
                intent.putExtra("EXTRA_LINK", article.getLink());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}
