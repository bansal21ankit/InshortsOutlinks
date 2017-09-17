package com.bansalankit.inshortsoutlinks;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Manager class to work with inshorts APIs.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>16 Sep 2017</b></i>
 * <br><i>Modified Date : <b>16 Sep 2017</b></i>
 */
public final class ApiManager {
    private static final String BASE_URL = "http://starlord.hackerearth.com/";
    private static ApiManager sInstance;
    private OutlinksService mService;

    public static ApiManager getInstance() {
        if (sInstance == null) sInstance = new ApiManager();
        return sInstance;
    }

    private ApiManager() {
        // Initialize the logger for retrofit request and responses
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Initialize the retrofit for making API calls
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();

        // Initialize the service that provide helper methods
        mService = retrofit.create(OutlinksService.class);
    }

    public OutlinksService getService() {
        return mService;
    }
}
