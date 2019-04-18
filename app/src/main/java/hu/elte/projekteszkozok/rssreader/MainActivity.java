package hu.elte.projekteszkozok.rssreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import hu.elte.projekteszkozok.rssreader.persistence.RssRepository;
import hu.elte.projekteszkozok.rssreader.persistence.db.RssDatabase;
import hu.elte.projekteszkozok.rssreader.recyclerview.RssFeedAdapter;
import hu.elte.projekteszkozok.rssreader.recyclerview.RssFeedLoader;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "https://hvg.hu/rss";
    private RssFeedAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RssFeedAdapter(getApplicationContext());
        new RssFeedLoader(getApplication(), URL, mAdapter, RssDatabase.getDatabase(getBaseContext()).rssDao()).execute();
        mRecyclerView.setAdapter(mAdapter);
    }

}
