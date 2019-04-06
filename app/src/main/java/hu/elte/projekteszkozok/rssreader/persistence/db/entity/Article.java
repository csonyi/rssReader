package hu.elte.projekteszkozok.rssreader.persistence.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

//Created by Zsolt Bakos on 2019.03.12

@Entity(tableName = "articles",
        foreignKeys = @ForeignKey(entity = Site.class,
            parentColumns = "id",
            childColumns = "siteId",
            onDelete = CASCADE))
public class Article {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "desc")
    private String desc;

    @ColumnInfo(name = "pubDate")
    private Date pubDate;

    @ColumnInfo(name = "siteId")
    private int siteId;

    public Article(String title, String desc, Date pubDate, int siteId) {
        this.title = title;
        this.desc = desc;
        this.pubDate = pubDate;
        this.siteId = siteId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", pubDate=" + pubDate +
                ", siteId=" + siteId +
                '}';
    }
}
