package hu.elte.projekteszkozok.rssreader.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hu.elte.projekteszkozok.rssreader.database.entity.Article;
import hu.elte.projekteszkozok.rssreader.database.entity.Site;

//Created by Zsolt Bakos on 2019.03.12

@Dao
public interface RssDao {

    //Site queries

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSite(Site site);

    @Update
    void updateSite(Site site);

    @Delete
    void deleteSite(Site site);

    @Query("SELECT * FROM sites")
    void getAllSite();

    //Article queries

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(Article article);

    @Update
    void updateArticle(Article article);

    @Delete
    void deleteArticle(Article article);

    @Query("SELECT * from articles where siteId = :siteId")
    List<Article> getArticleBySite(int siteId);

    @Query("SELECT * from articles")
    List<Article> getSllArticle();

}
