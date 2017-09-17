package com.bansalankit.inshortsoutlinks;

import android.content.Context;
import android.os.Build;
import android.support.annotation.StringRes;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Utility class providing common functionality.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>16 Sep 2017</b></i>
 * <br><i>Modified Date : <b>16 Sep 2017</b></i>
 */
public final class CommonUtils {
    private static final String TAG = CommonUtils.class.getSimpleName();

    private static final String THUMBNAIL_URL = "http://api.screenshotmachine.com/?key=%s&dimension=%s&url=%s";
    private static final String THUMBNAIL_SIZE = "700x300";
    private static final String THUMBNAIL_KEY = "b37c1c";

    private static final String ENCODING_FORMAT_UTF = "UTF-8";

    private static final String PATTERN_DATE = "MMMM d, yyyy";
    private static final String PATTERN_TIME = "h:mm a";

    private CommonUtils() {/*Ignored*/}

    public static String getDate(long utcMillis) {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();

        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE, Locale.getDefault());
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(new Date(utcMillis));
    }

    public static String getTime(long utcMillis) {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();

        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_TIME, Locale.getDefault());
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(new Date(utcMillis));
    }

    public static Spanned getHtmlString(String string) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(string, Html.FROM_HTML_MODE_COMPACT);
        return Html.fromHtml(string);
    }

    public static void loadThumbnail(final ImageView target, final String url) {
        target.post(new Runnable() {
            @Override
            public void run() {
                try {
                    String thumbUrl = String.format(THUMBNAIL_URL, THUMBNAIL_KEY, THUMBNAIL_SIZE, URLEncoder.encode(url, ENCODING_FORMAT_UTF));
                    Picasso.with(target.getContext()).load(thumbUrl).placeholder(R.drawable.img_not_available).into(target);
                    Log.d(TAG, "Loading thumbnail URL : " + thumbUrl);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void showToastShort(Context context, @StringRes int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
