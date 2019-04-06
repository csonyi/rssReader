package hu.elte.projekteszkozok.rssreader;

import android.app.Application;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import hu.elte.projekteszkozok.rssreader.persistence.RssRepository;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Article;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Site;

public class RssRepositoryUnitTest {

    private RssRepository rssRepository;
    private final String NASA_URL = "https://www.nasa.gov/rss/dyn/breaking_news.rss";
    private final String INDEX_URL = "https://index.hu/techtud/rss.php";

    @Before
    void beforeTest() {
        rssRepository = new RssRepository(new Application());
    }

    @After
    void afterTest() {
        rssRepository = null;
    }

    @Test
    void insertSite() {
        Site site  = new Site(NASA_URL);
        rssRepository.insertSite(site);

        List<Site> sites = rssRepository.getAllSite();

        if (sites.size() != 1)
            fail();

        Site siteFromDB = sites.get(0);
        assertEquals(site.getId(), siteFromDB.getId());
        assertEquals(site.getUrl(), siteFromDB.getUrl());
    }


    @Test
    void insertMultipleSite() {
        Site site1 = new Site(NASA_URL);
        Site site2 = new Site(INDEX_URL);
        rssRepository.insertSite(site1);
        rssRepository.insertSite(site2);

        List<Site> sites = rssRepository.getAllSite();

        if (sites.size() != 2)
            fail();

        Site siteFromDB1 = sites.get(0);
        Site siteFromDB2 = sites.get(1);
        assertEquals(site1.getUrl(), siteFromDB1.getUrl());
        assertEquals(site2.getUrl(), siteFromDB2.getUrl());
    }

    @Test
    void insertArticle() {
        Site indexSite = new Site(INDEX_URL);
        rssRepository.insertSite(indexSite);

        Article article1 = new Article("TestTitle#1", "This is a test article",
                new Date(2018, 12, 20, 13, 21, 0),
                indexSite.getId());
        rssRepository.insertArticle(article1);

        Article article2 = new Article("TestTitle#2", "This is another test article",
                new Date(2018, 11, 23, 13, 25, 0),
                article1.getId());
        rssRepository.insertArticle(article2);

        List<Article> articles = rssRepository.getAllArticle();

        if (articles.size() != 2)
            fail();

        assertEquals(article1.toString(), articles.get(0).toString());
        assertEquals(article2.toString(), articles.get(2).toString());

    }

    @Test
    void getArticlesBySiteUrl() {
        Site nasaSite = new Site(NASA_URL);
        Site indexSite = new Site(INDEX_URL);
        rssRepository.insertSite(nasaSite);
        rssRepository.insertSite(indexSite);

        Article nasaArticle1 = new Article("TestTitle#1", "This is a test article",
                new Date(2018, 12, 20, 13, 21, 0),
                nasaSite.getId());
        rssRepository.insertArticle(nasaArticle1);

        Article indexArticle1 = new Article("TestTitle#2", "This is another test article",
                new Date(2018, 11, 23, 13, 25, 0),
                indexSite.getId());
        rssRepository.insertArticle(indexArticle1);

        Article nasaArticle2 = new Article("TestTitle#3", "This is the third test article",
                new Date(2019, 03, 10, 11, 30, 0),
                indexSite.getId());
        rssRepository.insertArticle(nasaArticle2);

        List<Article> indexArticles = rssRepository.getAllArticleBySiteURL(INDEX_URL);

        if (indexArticles.size() != 1)
            fail();

        assertEquals(indexArticles.get(0).toString(), indexArticle1.toString());

        List<Article> nasaArticles = rssRepository.getAllArticleBySiteURL(NASA_URL);

        if (nasaArticles.size() != 2)
            fail();

        assertEquals(nasaArticles.get(0).toString(), nasaArticle1.toString());
        assertEquals(nasaArticles.get(1).toString(), nasaArticle2.toString());
    }

    @Test
    void deleteArticlesIfSiteIsDeleted() {
        Site nasaSite = new Site(NASA_URL);
        Site indexSite = new Site(INDEX_URL);
        rssRepository.insertSite(nasaSite);
        rssRepository.insertSite(indexSite);

        Article nasaArticle1 = new Article("TestTitle#1", "This is a test article",
                new Date(2018, 12, 20, 13, 21, 0),
                nasaSite.getId());
        rssRepository.insertArticle(nasaArticle1);

        Article indexArticle1 = new Article("TestTitle#2", "This is another test article",
                new Date(2018, 11, 23, 13, 25, 0),
                indexSite.getId());
        rssRepository.insertArticle(indexArticle1);

        Article nasaArticle2 = new Article("TestTitle#3", "This is the third test article",
                new Date(2019, 03, 10, 11, 30, 0),
                indexSite.getId());
        rssRepository.insertArticle(nasaArticle2);

        rssRepository.deleteSite(indexSite);

        List<Article> indexArticles = rssRepository.getAllArticleBySiteURL(INDEX_URL);

        assertTrue(indexArticles.isEmpty());
    }

    @Test
    void deleteDatabase() {
        Site nasaSite = new Site(NASA_URL);
        Site indexSite = new Site(INDEX_URL);
        rssRepository.insertSite(nasaSite);
        rssRepository.insertSite(indexSite);

        Article nasaArticle1 = new Article("TestTitle#1", "This is a test article",
                new Date(2018, 12, 20, 13, 21, 0),
                nasaSite.getId());
        rssRepository.insertArticle(nasaArticle1);

        Article indexArticle1 = new Article("TestTitle#2", "This is another test article",
                new Date(2018, 11, 23, 13, 25, 0),
                indexSite.getId());
        rssRepository.insertArticle(indexArticle1);

        Article nasaArticle2 = new Article("TestTitle#3", "This is the third test article",
                new Date(2019, 03, 10, 11, 30, 0),
                indexSite.getId());
        rssRepository.insertArticle(nasaArticle2);

        rssRepository.deleteAllSite();

        List<Article> articles = rssRepository.getAllArticle();
        List<Site> sites = rssRepository.getAllSite();

        assertTrue(articles.isEmpty());
        assertTrue(sites.isEmpty());
    }


}
