package hu.elte.projekteszkozok.rssreader;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import hu.elte.projekteszkozok.rssreader.persistence.db.RssDatabase;
import hu.elte.projekteszkozok.rssreader.persistence.db.dao.RssDao;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Article;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Site;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private RssDao rssDao;
    private RssDatabase db;

    private final String NASA_URL = "https://www.nasa.gov/rss/dyn/breaking_news.rss";
    private final String INDEX_URL = "https://index.hu/techtud/rss.php";

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, RssDatabase.class).build();
        rssDao = db.rssDao();
    }

    @After
    public void closeDb() {
        rssDao.deleteAllSite();
        db.close();
    }

    @Test
    public void insertSite() {
        Site site  = new Site(NASA_URL);
        rssDao.insertSite(site);

        List<Site> sites = rssDao.getAllSite();

        if (sites.size() != 1)
            fail();

        Site siteFromDB = sites.get(0);
        assertEquals(site.getUrl(), siteFromDB.getUrl());
    }

    @Test
    public void insertMultipleSite() {
        Site site1 = new Site(NASA_URL);
        Site site2 = new Site(INDEX_URL);
        rssDao.insertSite(site1);
        rssDao.insertSite(site2);

        List<Site> sites = rssDao.getAllSite();

        if (sites.size() != 2)
            fail();

        Site siteFromDB1 = sites.get(0);
        Site siteFromDB2 = sites.get(1);
        assertEquals(site1.getUrl(), siteFromDB1.getUrl());
        assertEquals(site2.getUrl(), siteFromDB2.getUrl());
    }

    @Test
    public void insertArticle() {
        Site indexSite = new Site(INDEX_URL);
        rssDao.insertSite(indexSite);

        Site indexSiteFromDB = rssDao.getSiteByURL(INDEX_URL).get(0);

        Article article1 = new Article("TestTitle#1", "This is a test article",
                new Date(2018, 12, 20, 13, 21, 0),
                indexSiteFromDB.getId());
        rssDao.insertArticle(article1);

        Article article2 = new Article("TestTitle#2", "This is another test article",
                new Date(2018, 11, 23, 13, 25, 0),
                indexSiteFromDB.getId());
        rssDao.insertArticle(article2);

        List<Article> articles = rssDao.getAllArticle();

        if (articles.size() != 2)
            fail();

        assertEquals(article1.toStringWithoudIds(), articles.get(0).toStringWithoudIds());
        assertEquals(article2.toStringWithoudIds(), articles.get(1).toStringWithoudIds());
    }

    @Test
    public void getArticlesBySiteUrl() {
        Site nasaSite = new Site(NASA_URL);
        Site indexSite = new Site(INDEX_URL);
        rssDao.insertSite(nasaSite);
        rssDao.insertSite(indexSite);

        Site indexSiteFromDB = rssDao.getSiteByURL(INDEX_URL).get(0);
        Site nasaSiteFromDB = rssDao.getSiteByURL(NASA_URL).get(0);

        Article nasaArticle1 = new Article("TestTitle#1", "This is a test article",
                new Date(2018, 12, 20, 13, 21, 0),
                nasaSiteFromDB.getId());
        rssDao.insertArticle(nasaArticle1);

        Article indexArticle1 = new Article("TestTitle#2", "This is another test article",
                new Date(2018, 11, 23, 13, 25, 0),
                indexSiteFromDB.getId());
        rssDao.insertArticle(indexArticle1);

        Article nasaArticle2 = new Article("TestTitle#3", "This is the third test article",
                new Date(2019, 03, 10, 11, 30, 0),
                nasaSiteFromDB.getId());
        rssDao.insertArticle(nasaArticle2);

        List<Article> indexArticles = rssDao.getAllArticleBySiteURL(INDEX_URL);

        if (indexArticles.size() != 1)
            fail();

        assertEquals(indexArticles.get(0).toStringWithoudIds(), indexArticle1.toStringWithoudIds());

        List<Article> nasaArticles = rssDao.getAllArticleBySiteURL(NASA_URL);

        if (nasaArticles.size() != 2)
            fail();

        assertEquals(nasaArticles.get(0).toStringWithoudIds(), nasaArticle1.toStringWithoudIds());
        assertEquals(nasaArticles.get(1).toStringWithoudIds(), nasaArticle2.toStringWithoudIds());
    }

    @Test
    public void deleteArticlesIfSiteIsDeleted() {
        Site nasaSite = new Site(NASA_URL);
        Site indexSite = new Site(INDEX_URL);
        rssDao.insertSite(nasaSite);
        rssDao.insertSite(indexSite);

        Site nasaSiteFromDB = rssDao.getSiteByURL(NASA_URL).get(0);
        Site indexSiteFromDB = rssDao.getSiteByURL(INDEX_URL).get(0);

        Article nasaArticle1 = new Article("TestTitle#1", "This is a test article",
                new Date(2018, 12, 20, 13, 21, 0),
                nasaSiteFromDB.getId());
        rssDao.insertArticle(nasaArticle1);

        Article indexArticle1 = new Article("TestTitle#2", "This is another test article",
                new Date(2018, 11, 23, 13, 25, 0),
                indexSiteFromDB.getId());
        rssDao.insertArticle(indexArticle1);

        Article nasaArticle2 = new Article("TestTitle#3", "This is the third test article",
                new Date(2019, 03, 10, 11, 30, 0),
                nasaSiteFromDB.getId());
        rssDao.insertArticle(nasaArticle2);

        rssDao.deleteSite(indexSiteFromDB);

        List<Article> indexArticles = rssDao.getAllArticleBySiteURL(INDEX_URL);

        assertTrue(indexArticles.isEmpty());
    }

    @Test
    public void deleteDatabase() {
        Site nasaSite = new Site(NASA_URL);
        Site indexSite = new Site(INDEX_URL);
        rssDao.insertSite(nasaSite);
        rssDao.insertSite(indexSite);

        Site nasaSiteFromDB = rssDao.getSiteByURL(NASA_URL).get(0);
        Site indexSiteFromDB = rssDao.getSiteByURL(INDEX_URL).get(0);

        Article nasaArticle1 = new Article("TestTitle#1", "This is a test article",
                new Date(2018, 12, 20, 13, 21, 0),
                nasaSiteFromDB.getId());
        rssDao.insertArticle(nasaArticle1);

        Article indexArticle1 = new Article("TestTitle#2", "This is another test article",
                new Date(2018, 11, 23, 13, 25, 0),
                indexSiteFromDB.getId());
        rssDao.insertArticle(indexArticle1);

        Article nasaArticle2 = new Article("TestTitle#3", "This is the third test article",
                new Date(2019, 03, 10, 11, 30, 0),
                nasaSiteFromDB.getId());
        rssDao.insertArticle(nasaArticle2);
        rssDao.deleteAllSite();

        List<Article> articles = rssDao.getAllArticle();
        List<Site> sites = rssDao.getAllSite();

        assertTrue(articles.isEmpty());
        assertTrue(sites.isEmpty());
    }
}