package hu.elte.projekteszkozok.rssreader.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//Created by Zsolt Bakos on 2019.03.12

@Entity(tableName = "sites")
public class Site {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "url")
    @NonNull
    public String url;

    public Site(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }
}
