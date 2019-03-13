package hu.elte.projekteszkozok.rssreader.database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import hu.elte.projekteszkozok.rssreader.database.entity.Article;
import hu.elte.projekteszkozok.rssreader.database.entity.Site;

//Created by Zsolt Bakos on 2019.03.13

public class RssRepository {

    private RssDao rssDao;
    private List<Site> allSite;
    private List<Article> allArticle;

    RssRepository(Application application) {
        RssDatabase db = RssDatabase.getDatabase(application);
        rssDao = db.rssDao();
        allSite = rssDao.getAllSite();
    }

    private static class insertSiteAsyncTask extends AsyncTask<Site, Void, Void> {
        private RssDao asyncRssDao;

        insertSiteAsyncTask(RssDao dao) {
            asyncRssDao = dao;
        }

        @Override
        protected Void doInBackground(final Site... params) {
            synchronized (this) {
                asyncRssDao.insertSite(params[0]);
            }
            return null;
        }
    }

    private static class insertArticleAsyncTask extends AsyncTask<Article, Void, Void> {
        private RssDao asyncRssDao;

        insertArticleAsyncTask(RssDao dao) {
            asyncRssDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            synchronized (this) {
                asyncRssDao.insertArticle(params[0]);
            }
            return null;
        }
    }
}
