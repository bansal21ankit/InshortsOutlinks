package com.bansalankit.inshortsoutlinks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Main activity to list the inshorts outlinks articles and their details.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>16 Sep 2017</b></i>
 * <br><i>Modified Date : <b>16 Sep 2017</b></i>
 */
public class MainActivity extends AppCompatActivity implements Callback<List<Article>>, ArticlesAdapter.ClickListener {
    @BindView(R.id.main_layout_progress)
    protected View mLayoutProgress;

    @BindView(R.id.main_layout_error)
    protected LinearLayout mLayoutError;

    @BindView(R.id.main_txt_error)
    protected TextView mTxtError;

    @BindView(R.id.main_recycler)
    protected RecyclerView mRecyclerArticles;
    private ArticlesAdapter mAdapterArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();

        // Prepare recycler view for viewing
        mAdapterArticles = new ArticlesAdapter(this, this);
        mRecyclerArticles.setAdapter(mAdapterArticles);
        mRecyclerArticles.setLayoutManager(new LinearLayoutManager(this));

        fetchArticles();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (toolbar != null && getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getTitle());
        }
    }

    @OnClick(R.id.main_filter)
    protected void onClickFilter() {
        CommonUtils.showToastShort(this, R.string.msg_under_construction);
    }

    @OnClick(R.id.main_btn_retry)
    protected void fetchArticles() {
        try {
            // Toggle the visibility of views.
            mLayoutProgress.setVisibility(View.VISIBLE);
            mLayoutError.setVisibility(View.GONE);
            mRecyclerArticles.setVisibility(View.GONE);

            // Fetch the Outlinks Article list.
            ApiManager.getInstance().getService().fetchArticles().enqueue(this);
        }
        // To avoid unexpected crash
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onResponse(@NonNull Call<List<Article>> call, @NonNull Response<List<Article>> response) {
        try {
            if (response.isSuccessful()) {
                // Toggle the visibility of views.
                mLayoutProgress.setVisibility(View.GONE);
                mLayoutError.setVisibility(View.GONE);
                mRecyclerArticles.setVisibility(View.VISIBLE);

                // List out the articles.
                List<Article> articles = response.body();
                mAdapterArticles.setArticleData(articles);
            } else {
                showError(response.errorBody().string());
            }
        } catch (NullPointerException | IOException exception) {
            showError(getString(R.string.msg_error_unknown));
            exception.printStackTrace();
        }
    }

    @Override
    public void onFailure(@NonNull Call<List<Article>> call, @NonNull Throwable throwable) {
        showError(getString(R.string.msg_error_unknown));
        throwable.printStackTrace();
    }

    private void showError(String message) {
        try {
            // Toggle the visibility of views.
            mLayoutProgress.setVisibility(View.GONE);
            mLayoutError.setVisibility(View.VISIBLE);
            mRecyclerArticles.setVisibility(View.GONE);

            // Change the error message.
            mTxtError.setText(CommonUtils.getHtmlString(message));
        }
        // To avoid unexpected crash
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapterArticles.notifyDataSetChanged();
    }

    @Override
    public void onClick(Article article) {
        try {
            Intent intent = new Intent(this, ArticleActivity.class);
            intent.putExtra(ArticleActivity.EXTRA_ID, String.valueOf(article.getId()));
            intent.putExtra(ArticleActivity.EXTRA_TITLE, article.getTitle());
            intent.putExtra(ArticleActivity.EXTRA_URL, article.getUrl());

            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
        // To avoid unexpected crash
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
