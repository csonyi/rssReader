package hu.elte.projekteszkozok.rssreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;
import java.util.List;

import hu.elte.projekteszkozok.rssreader.persistence.RssRepository;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Article;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Site;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mockDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void mockDatabase() {
        String indexURL = "https://index.hu/techtud/rss.php";
        String nasaURL = "https://www.nasa.gov/rss/dyn/breaking_news.rss";
        RssRepository repo = new RssRepository(getApplication());
        //Delete all Datas in the DB
        repo.deleteAllSite();
        
        //Add 2 new mock sites
        Site site = new Site(indexURL);
        repo.insertSite(site);
        site = new Site(nasaURL);
        repo.insertSite(site);
        
        //Print all sites in DB - there should be only 2, inserted above
        List<Site> sites = repo.getAllSite();
        for (Site st: sites) {
            Log.d("MOCK_DB", st.toString());
        }
        
        //Insert articles with id of the recently inserted site
        Site indexSite = repo.getSiteByUrl(indexURL);
        Site nasaSite = repo.getSiteByUrl(nasaURL);
        Article article1 = new Article("TestTitle#1", "This is a test article",
                new Date(2018, 12, 20, 13, 21, 0),
                indexSite.id);
        repo.insertArticle(article1);
        Article article2 = new Article("TestTitle#2", "This is another test article",
                new Date(2018, 11, 23, 13, 25, 0),
                nasaSite.id);
        repo.insertArticle(article2);
        Article article3 = new Article("TestTitle#3", "This is the third test article",
                new Date(2019, 03, 10, 11, 30, 0),
                indexSite.id);
        repo.insertArticle(article3);
        
        Log.d("MOCK_DB", "Articles at Index");
        List<Article> articlesAtIndex = repo.getAllArticleBySiteURL(indexURL);
        for (Article art: articlesAtIndex) {
            Log.d("MOCK_DB", art.toString());
        }

        Log.d("MOCK_DB", "Articles at NASA");
        List<Article> articlesAtNasa = repo.getAllArticleBySite(nasaSite);
        for (Article art: articlesAtNasa) {
            Log.d("MOCK_DB", art.toString());
        }

        Log.d("MOCK_DB", "Get articles from all source");
        List<Article> allArticle = repo.getAllArticle();
        for (Article art: allArticle) {
            Log.d("MOCK_DB", art.toString());
        }

    }
}
