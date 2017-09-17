package com.bansalankit.inshortsoutlinks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Service interface to provide the helper methods for working with inshorts API.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>16 Sep 2017</b></i>
 * <br><i>Modified Date : <b>16 Sep 2017</b></i>
 */
public interface OutlinksService {
    @GET("newsjson")
    Call<List<Article>> fetchArticles();
}
