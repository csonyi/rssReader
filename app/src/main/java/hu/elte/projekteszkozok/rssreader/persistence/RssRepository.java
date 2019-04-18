package hu.elte.projekteszkozok.rssreader.persistence;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import hu.elte.projekteszkozok.rssreader.persistence.db.RssDatabase;
import hu.elte.projekteszkozok.rssreader.persistence.db.dao.RssDao;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Article;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Site;

//Created by Zsolt Bakos on 2019.03.13

public class RssRepository {

    private RssDao rssDao;

    public RssDao getRssDao() {
        return rssDao;
    }

    public RssRepository(Application application) {
        RssDatabase db = RssDatabase.getDatabase(application);
        rssDao = db.rssDao();
    }

    public List<Site> getAllSite() {
        List<Site> sites = new ArrayList<>();
        try {
            sites = new getAllSiteTask(rssDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sites;
    }

    public Site getSiteByUrl(String url) {
        Site site = null;
        try {
            site = new getSiteByURLTask(rssDao).execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return site;
    }

    public List<Article> getAllArticleBySiteURL(String url) {
        List<Article> articles = new ArrayList<>();
        try {
            articles = new getAllArticleBySiteURLTask(rssDao).execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public List<Article> getAllArticle() {
        List<Article> articles = new ArrayList<>();
        try {
            articles = new getAllArticleTask(rssDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public List<Article> getAllArticleBySite(Site site) {
        List<Article> articles = new ArrayList<>();
        try {
            articles = new getAllArticleBySiteIdTask(rssDao).execute(site.getId()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public void insertSite(Site site) {
        new insertSiteTask(rssDao).execute(site);
    }

    public void insertMultipleSite(List<Site> sites) {
        new insertMultipleSiteTask(rssDao).execute(sites);
    }
    public void updateSite(Site site) {
        new updateSiteTask(rssDao).execute(site);
    }

    public void deleteSite(Site site) {
        new deleteSiteTask(rssDao).execute(site);
    }

    public void deleteAllSite() {
        new deleteAllSiteTask(rssDao).execute();
    }

    public void insertArticle(Article article) {
        new insertArticleTask(rssDao).execute(article);
    }

    public void insertMultipleArticle(List<Article> articles) {
        new insertMultipleArticleTask(rssDao).execute(articles);
    }

    public void updateArticle(Article article) {
        new updateArticleTask(rssDao).execute(article);
    }

    public void deleteArticle(Article article) {
        new deleteArticleTask(rssDao).execute(article);
    }

    public void deleteAllArticle() {
        new deleteAllArticleTask(rssDao).execute();
    }



    //Async Database tasks - use it only by methods within this repo class!

    private static class insertSiteTask extends AsyncTask<Site, Void, Void> {
        private RssDao rssDao;

        insertSiteTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected Void doInBackground(final Site... params) {
            synchronized (this) {
                rssDao.insertSite(params[0]);
            }
            return null;
        }
    }

    private static class insertMultipleSiteTask extends AsyncTask<List<Site>, Void, Void> {
        private RssDao rssDao;

        insertMultipleSiteTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Site>... params) {
            synchronized (this) {
                rssDao.insertMultipleSite(params[0]);
            }
            return null;
        }
    }

    private static class insertArticleTask extends AsyncTask<Article, Void, Void> {
        private RssDao rssDao;

        insertArticleTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            synchronized (this) {
                rssDao.insertArticle(params[0]);
            }
            return null;
        }
    }

    private static class insertMultipleArticleTask extends AsyncTask<List<Article>, Void, Void> {
        private RssDao rssDao;

        insertMultipleArticleTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Article>... params) {
            synchronized (this) {
                rssDao.insertMultipleArticle(params[0]);
            }
            return null;
        }
    }

    private static class updateSiteTask extends AsyncTask<Site, Void, Void> {
        private RssDao rssDao;

        updateSiteTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected Void doInBackground(final Site... params) {
            synchronized (this) {
                rssDao.updateSite(params[0]);
            }
            return null;
        }
    }

    private static class updateArticleTask extends AsyncTask<Article, Void, Void> {
        private RssDao rssDao;

        updateArticleTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            synchronized (this) {
                rssDao.updateArticle(params[0]);
            }
            return null;
        }
    }

    private static class deleteSiteTask extends AsyncTask<Site, Void, Void> {
        private RssDao rssDao;

        deleteSiteTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected Void doInBackground(final Site... params) {
            synchronized (this) {
                rssDao.deleteSite(params[0]);
                rssDao.deleteArticlesBySite(params [0].getId());
            }
            return null;
        }
    }

    private static class deleteAllSiteTask extends AsyncTask<Void, Void, Void> {
        private RssDao rssDao;

        deleteAllSiteTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            synchronized (this) {
                rssDao.deleteAllArticle();
                rssDao.deleteAllSite();
            }
            return null;
        }
    }

    private static class deleteArticleTask extends AsyncTask<Article, Void, Void> {
        private RssDao rssDao;

        deleteArticleTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            synchronized (this) {
                rssDao.deleteArticle(params[0]);
            }
            return null;
        }
    }
    private static class deleteAllArticleTask extends AsyncTask<Void, Void, Void> {
        private RssDao rssDao;

        deleteAllArticleTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            synchronized (this) {
                rssDao.deleteAllArticle();
            }
            return null;
        }
    }

    private static class getAllSiteTask extends AsyncTask<Void, Void, List<Site>> {
        private RssDao rssDao;

        getAllSiteTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected List<Site> doInBackground(final Void... params) {
            synchronized (this) {
                return rssDao.getAllSite();
            }
        }
    }

    private static class getSiteByURLTask extends AsyncTask<String, Void, Site> {
        private RssDao rssDao;

        getSiteByURLTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected Site doInBackground(final String... params) {
            synchronized (this) {
                return rssDao.getSiteByURL(params[0]);
            }
        }
    }

    private static class getAllArticleTask extends AsyncTask<Void, Void, List<Article>> {
        private RssDao rssDao;

        getAllArticleTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected List<Article> doInBackground(final Void... params) {
            synchronized (this) {
                return rssDao.getAllArticle();
            }
        }
    }

    private static class getAllArticleBySiteURLTask extends AsyncTask<String, Void, List<Article>> {
        private RssDao rssDao;

        getAllArticleBySiteURLTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected List<Article> doInBackground(final String... params) {
            synchronized (this) {
                return rssDao.getAllArticleBySiteURL(params[0]);
            }
        }
    }

    private static class getAllArticleBySiteIdTask extends AsyncTask<Integer, Void, List<Article>> {
        private RssDao rssDao;

        getAllArticleBySiteIdTask(RssDao dao) {
            rssDao = dao;
        }

        @Override
        protected List<Article> doInBackground(final Integer... params) {
            synchronized (this) {
                return rssDao.getAllArticleBySiteId(params[0]);
            }
        }
    }

}
