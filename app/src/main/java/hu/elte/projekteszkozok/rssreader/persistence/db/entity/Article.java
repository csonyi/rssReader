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
    private String pubDate;

    @ColumnInfo(name = "link")
    private String link;

    @ColumnInfo(name = "siteId")
    private int siteId;

    public Article(String title, String desc, String pubDate, int siteId, String link) {
        this.title = title;
        this.desc = desc;
        this.pubDate = pubDate;
        this.siteId = siteId;
        this.link = link;
    }

    public Article(){}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getPubDate() {
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

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String toStringWithoudIds() {
        return "Article{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", pubDate=" + pubDate +
                '}';
    }
}
