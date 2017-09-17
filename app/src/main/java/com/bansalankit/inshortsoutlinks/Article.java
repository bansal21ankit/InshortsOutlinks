package com.bansalankit.inshortsoutlinks;

import com.google.gson.annotations.SerializedName;

/**
 * Model class used to access the information of outlinks provided by inshorts.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>16 Sep 2017</b></i>
 * <br><i>Modified Date : <b>16 Sep 2017</b></i>
 */
public class Article {
    @SerializedName("ID")
    private int mId;

    @SerializedName("TITLE")
    private String mTitle;

    @SerializedName("URL")
    private String mUrl;

    @SerializedName("PUBLISHER")
    private String mPublisher;

    @SerializedName("CATEGORY")
    private String mCategory;

    @SerializedName("HOSTNAME")
    private String mHostname;

    @SerializedName("TIMESTAMP")
    private long mTimestamp;

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getHostname() {
        return mHostname;
    }

    public String getDate() {
        return CommonUtils.getDate(mTimestamp);
    }

    public String getTime() {
        return CommonUtils.getTime(mTimestamp);
    }
}
