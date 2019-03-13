package hu.elte.projekteszkozok.rssreader.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import hu.elte.projekteszkozok.rssreader.database.entity.Article;
import hu.elte.projekteszkozok.rssreader.database.entity.Site;
import hu.elte.projekteszkozok.rssreader.database.tools.Converters;

//Created by Zsolt Bakos on 2019.03.13

@Database(entities = {Site.class, Article.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class RssDatabase extends RoomDatabase {
    public abstract RssDao rssDao();

    private static volatile RssDatabase INSTANCE;

    static RssDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RssDatabase.class) {
                if (INSTANCE == null) {
                    //Create the database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RssDatabase.class, "rss_database")
                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
