package hu.elte.projekteszkozok.rssreader.database.entity;

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
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "desc")
    public String desc;

    @ColumnInfo(name = "pubDate")
    public Date pubDate;

    @ColumnInfo(name = "siteId")
    public int siteId;
}
