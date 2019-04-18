package hu.elte.projekteszkozok.rssreader.recyclerview;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hu.elte.projekteszkozok.rssreader.persistence.db.entity.Article;

public class RssFeedProvider {

    private static final String PUB_DATE = "pubDate";
    private static final String DESCRIPTION = "description";
    private static final String LINK = "link";
    private static final String TITLE = "title";
    private static final String ITEM = "item";

    public static List<Article> parse(String rssFeed, int siteId) {
        List<Article> articleList = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        int id = 0;
        try {
            stream = new URL(rssFeed).openConnection().getInputStream();
            parser.setInput(stream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            Article article = null;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ITEM)) {
                            article = new Article();
                            article.setId(id);
                            id++;
                            article.setSiteId(siteId);
                        } else if (article != null) {
                            if (name.equalsIgnoreCase(LINK)) {
                                article.setLink(parser.nextText());
                            } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                                article.setDesc(parser.nextText().trim());
                            } else if (name.equalsIgnoreCase(PUB_DATE)) {
                                article.setPubDate(parser.nextText());
                            } else if (name.equalsIgnoreCase(TITLE)) {
                                article.setTitle(parser.nextText().trim());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ITEM) && article != null) {
                            Log.d("RssReader", article.toString());
                            articleList.add(article);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return articleList;
    }
}