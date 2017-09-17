package com.bansalankit.inshortsoutlinks;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Article detailing activity.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>17 Sep 2017</b></i>
 * <br><i>Modified Date : <b>17 Sep 2017</b></i>
 */
public class ArticleActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "Article Title";
    public static final String EXTRA_URL = "URL to load";
    public static final String EXTRA_ID = "Article ID";

    @BindView(R.id.article_favorite)
    protected CheckBox mCheckFavorite;
    private FavoriteManager mFavoriteManager;
    private String mArticleId;

    @BindView(R.id.article_page)
    protected WebView mWebView;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        try {
            initToolbar();

            // Prepare the webview for viewing content
            WebSettings settings = mWebView.getSettings();
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            settings.setJavaScriptEnabled(true);
            settings.setUseWideViewPort(true);
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.setWebChromeClient(new WebChromeClient());
            mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

            // Load content in webview
            mWebView.loadUrl(getIntent().getStringExtra(EXTRA_URL));
        }
        // To avoid unexpected crash
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void initToolbar() {
        if (getSupportActionBar() != null) return;

        Toolbar toolbar = (Toolbar) findViewById(R.id.article_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getIntent().getStringExtra(EXTRA_TITLE));
            toolbar.setSubtitle(getIntent().getStringExtra(EXTRA_URL));
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        if (mCheckFavorite != null) {
            // Update the favorite status of article
            mArticleId = getIntent().getStringExtra(EXTRA_ID);
            mFavoriteManager = FavoriteManager.getInstance(this);
            mCheckFavorite.setChecked(mFavoriteManager.isFavorite(mArticleId));
        }
    }

    @OnClick(R.id.article_favorite)
    protected void onFavoriteToggle() {
        if (mCheckFavorite == null) return;
        try {
            // Set article as favorite
            if (mCheckFavorite.isChecked()) {
                if (mFavoriteManager.setFavorite(mArticleId)) {
                    CommonUtils.showToastShort(this, R.string.article_favorite_added);
                } else {
                    CommonUtils.showToastShort(this, R.string.msg_error_unknown);
                    mCheckFavorite.setChecked(false);
                }
            }
            // Remove article from favorite
            else {
                if (mFavoriteManager.removeFavorite(mArticleId)) {
                    CommonUtils.showToastShort(this, R.string.article_favorite_removed);
                } else {
                    CommonUtils.showToastShort(this, R.string.msg_error_unknown);
                    mCheckFavorite.setChecked(true);
                }
            }
        }
        // To avoid unexpected crash
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) mWebView.goBack();
        else closeActivity();
    }

    private void closeActivity() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }
}
