package hu.elte.projekteszkozok.rssreader.persistence;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import hu.elte.projekteszkozok.rssreader.persistence.db.RssDatabase;
import hu.elte.projekteszkozok.rssreader.persistence.db.dao.RssDao;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Article;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Site;

//Created by Zsolt Bakos on 2019.03.13

public class RssRepository {

    private RssDao rssDao;
    private LiveData<List<Site>> allSite;
    private LiveData<List<Article>> allArticle;

    RssRepository(Application application) {
        RssDatabase db = RssDatabase.getDatabase(application);
        rssDao = db.rssDao();
        allSite = rssDao.getAllSite();
    }

    //Async Database tasks

    private static class insertSite extends AsyncTask<Site, Void, Void> {
        private RssDao asyncRssDao;

        insertSite(RssDao dao) {
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

    private static class insertArticle extends AsyncTask<Article, Void, Void> {
        private RssDao asyncRssDao;

        insertArticle(RssDao dao) {
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

    private static class updateSite extends AsyncTask<Site, Void, Void> {
        private RssDao asyncRssDao;

        updateSite(RssDao dao) {
            asyncRssDao = dao;
        }

        @Override
        protected Void doInBackground(final Site... params) {
            synchronized (this) {
                asyncRssDao.updateSite(params[0]);
            }
            return null;
        }
    }

    private static class updateArticle extends AsyncTask<Article, Void, Void> {
        private RssDao asyncRssDao;

        updateArticle(RssDao dao) {
            asyncRssDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            synchronized (this) {
                asyncRssDao.updateArticle(params[0]);
            }
            return null;
        }
    }

    private static class deleteSite extends AsyncTask<Site, Void, Void> {
        private RssDao asyncRssDao;

        deleteSite(RssDao dao) {
            asyncRssDao = dao;
        }

        @Override
        protected Void doInBackground(final Site... params) {
            synchronized (this) {
                asyncRssDao.deleteSite(params[0]);
                asyncRssDao.deleteArticlesBySite(params [0].id);
            }
            return null;
        }
    }

    private static class deleteArticle extends AsyncTask<Article, Void, Void> {
        private RssDao asyncRssDao;

        deleteArticle(RssDao dao) {
            asyncRssDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            synchronized (this) {
                asyncRssDao.deleteArticle(params[0]);
            }
            return null;
        }
    }

    private static class getSites extends AsyncTask<Void, Void, LiveData<List<Site>>> {
        private RssDao asyncRssDao;

        getSites(RssDao dao) {
            asyncRssDao = dao;
        }

        @Override
        protected LiveData<List<Site>> doInBackground(final Void... params) {
            synchronized (this) {
                return asyncRssDao.getAllSite();
            }
        }
    }

    private static class getArticles extends AsyncTask<Void, Void, LiveData<List<Article>>> {
        private RssDao asyncRssDao;

        getArticles(RssDao dao) {
            asyncRssDao = dao;
        }

        @Override
        protected LiveData<List<Article>> doInBackground(final Void... params) {
            synchronized (this) {
                return asyncRssDao.getAllArticle();
            }
        }
    }

    private static class getArticlesBySite extends AsyncTask<Site, Void, LiveData<List<Article>>> {
        private RssDao asyncRssDao;

        getArticlesBySite(RssDao dao) {
            asyncRssDao = dao;
        }

        @Override
        protected LiveData<List<Article>> doInBackground(final Site... params) {
            synchronized (this) {
                return asyncRssDao.getArticlesBySite(params[0].id);
            }
        }
    }

}
