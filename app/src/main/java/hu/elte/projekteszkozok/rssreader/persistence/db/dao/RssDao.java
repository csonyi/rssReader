package hu.elte.projekteszkozok.rssreader.persistence.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Article;
import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Site;

//Created by Zsolt Bakos on 2019.03.12

@Dao
public interface RssDao {

    //Site queries

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSite(Site site);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleSite(List<Site> site);

    @Update
    void updateSite(Site site);

    @Delete
    void deleteSite(Site site);

    @Query("DELETE FROM sites")
    void deleteAllSite();

    @Query("SELECT * FROM sites")
    List<Site> getAllSite();

    @Query("SELECT * FROM sites WHERE url=:url")
    Site getSiteByURL(String url);

    //Article queries

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(Article article);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleArticle(List<Article> article);


    @Update
    void updateArticle(Article article);

    @Delete
    void deleteArticle(Article article);

    @Query("DELETE FROM articles")
    void deleteAllArticle();

    @Query("DELETE FROM articles WHERE siteId =:siteId")
    void deleteArticlesBySite(int siteId);

    @Query("SELECT * from articles WHERE siteId = :siteId")
    List<Article> getAllArticleBySiteId(int siteId);

    @Query("SELECT articles.* FROM articles JOIN sites ON articles.siteId = sites.id WHERE sites.url = :url")
    List<Article> getAllArticleBySiteURL(String url);

    @Query("SELECT * FROM articles")
    List<Article> getAllArticle();

}
