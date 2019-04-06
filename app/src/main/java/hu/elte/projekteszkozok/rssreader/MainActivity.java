package hu.elte.projekteszkozok.rssreader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;

import java.util.Date;
import java.util.List;

import hu.elte.projekteszkozok.rssreader.persistence.RssRepository;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Article;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Site;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "https://hvg.hu/rss";

    private static RecyclerView mRecyclerView;
    private static RecyclerView.Adapter<RssFeedAdapter.ViewHolder> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new GetFeedAsync().execute();

        //remove element by swiping
        /*ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView mRecyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        input.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        */
    }
    
    private static class GetFeedAsync extends AsyncTask<Void, Void, List<ArticleDataModel>> {
        @Override
        protected List<ArticleDataModel> doInBackground(Void... voids) {
            List<ArticleDataModel> data;
            data = RssFeedProvider.parse(URL);
            return data;
        }

        @Override
        protected void onPostExecute(List<ArticleDataModel> result) {
            mAdapter = new RssFeedAdapter(result);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
