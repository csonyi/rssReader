package hu.elte.projekteszkozok.rssreader.recyclerview;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import hu.elte.projekteszkozok.rssreader.R;
import hu.elte.projekteszkozok.rssreader.persistence.RssRepository;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Article;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Site;

public class RssFeedLoader extends AsyncTask<Void,Void,List<Article>> {
    private String mUrl;
    private Application mApplication;
    private List<Article> mArticleList;
    private RssRepository mRepository;
    private RssFeedAdapter mAdapter;

    public RssFeedLoader(Application application, String url, RssFeedAdapter adapter) {
        mApplication = application;
        mUrl = url;
        mRepository = new RssRepository(mApplication);
        mAdapter = adapter;
    }

    @Override
    protected List<Article> doInBackground(Void... voids) {
        if(isConnectedToInternet(mApplication)) {
            Site mSite;
            if (mRepository.getSiteByUrl(mUrl) != null) {
                mRepository.insertSite(new Site(mUrl));
            }

            Log.d("LOGGOLJÁÁÁ MÁÁ", "PLSSSS");
            mSite = mRepository.getSiteByUrl(mUrl);
            mArticleList = RssFeedProvider.parse(mUrl, mSite.getId());
            mRepository.deleteAllArticle();
            mRepository.insertMultipleArticle(mArticleList);
        } else {
            Log.d("RssReader", "No internet connection available.");
            if(mRepository.getAllArticle().size() != 0) {
                mArticleList = mRepository.getAllArticle();
                Toast.makeText(mApplication, R.string.no_internet_read_database, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mApplication, R.string.no_intenet_no_database, Toast.LENGTH_LONG).show();
            }
        }
        return mArticleList;
    }

    protected void onPostExecute(List<Article> articleList) {
        mAdapter.setList(articleList);
        mAdapter.notifyDataSetChanged();
    }

    private static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
