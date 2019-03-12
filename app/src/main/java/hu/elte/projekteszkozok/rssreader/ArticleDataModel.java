package hu.elte.projekteszkozok.rssreader;

public class ArticleDataModel {
    private String mTitle, mDesc, mPubDate, mLink;
    private int rssId;

    public String getLink() {
        return mLink;
    }

    public int getRssId() {
        return rssId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public void setPubDate(String pubDate) {
        mPubDate = pubDate;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public void setRssId(int rssId) {
        this.rssId = rssId;
    }

    public String getPubDate() {
        return mPubDate;
    }
}
