package hu.elte.projekteszkozok.rssreader;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssFeedProvider {

    private static final String PUB_DATE = "pubDate";
    private static final String DESCRIPTION = "description";
    private static final String LINK = "link";
    private static final String TITLE = "title";
    private static final String ITEM = "item";

    public static List<ArticleDataModel> parse(String rssFeed) {
        List<ArticleDataModel> list = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        try {
            stream = new URL(rssFeed).openConnection().getInputStream();
            parser.setInput(stream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            ArticleDataModel item = null;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ITEM)) {
                            item = new ArticleDataModel();
                        } else if (item != null) {
                            if (name.equalsIgnoreCase(LINK)) {
                                item.setLink(parser.nextText());
                            } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                                item.setDesc(parser.nextText().trim());
                            } else if (name.equalsIgnoreCase(PUB_DATE)) {
                                item.setPubDate(parser.nextText());
                            } else if (name.equalsIgnoreCase(TITLE)) {
                                item.setTitle(parser.nextText().trim());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ITEM) && item != null) {
                            Log.d("RssFeedProvider", item.toString());
                            list.add(item);
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
        return list;
    }
}