package com.bansalankit.inshortsoutlinks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>16 Sep 2017</b></i>
 * <br><i>Modified Date : <b>16 Sep 2017</b></i>
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {
    private final FavoriteManager mFavoriteManager;
    private final ClickListener mClickListener;
    private List<Article> mArticleData;

    /**
     * Interface working as click event listener on the base view of items shown via this adapter.
     */
    public interface ClickListener {
        void onClick(Article article);
    }

    /**
     * ViewHolder class for the items in this recycler view.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.article_thumbnail)
        ImageView mThumbnail;

        @BindView(R.id.article_title)
        TextView mTitle;

        @BindView(R.id.article_publisher)
        TextView mPublisher;

        @BindView(R.id.article_category)
        TextView mCategory;

        @BindView(R.id.article_timestamp)
        TextView mTimestamp;

        public ViewHolder(View rootView) {
            super(rootView);
            ButterKnife.bind(this, rootView);
        }

        public void updateViews(final Article article, final ClickListener listener) {
            try {
                // Update background based on favorite status
                itemView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mFavoriteManager.isFavorite(String.valueOf(article.getId()))) {
                            itemView.setBackgroundResource(R.drawable.background_favorite);
                        } else itemView.setBackgroundResource(android.R.color.white);
                    }
                });

                CommonUtils.loadThumbnail(mThumbnail, article.getUrl());
                mTitle.setText(article.getTitle());

                String publisherTxt = mCategory.getResources().getString(R.string.article_publisher);
                publisherTxt = String.format(publisherTxt, article.getPublisher(), article.getHostname());
                mPublisher.setText(CommonUtils.getHtmlString(publisherTxt));

                String categoryTxt = mCategory.getResources().getString(R.string.article_category);
                categoryTxt = String.format(categoryTxt, article.getCategory());
                mCategory.setText(CommonUtils.getHtmlString(categoryTxt));

                String timestampTxt = mTimestamp.getResources().getString(R.string.article_timestamp);
                timestampTxt = String.format(timestampTxt, article.getDate(), article.getTime());
                mTimestamp.setText(CommonUtils.getHtmlString(timestampTxt));

                // Set a listener to capture click events
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) listener.onClick(article);
                    }
                });
            }
            // To avoid unexpected crash
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public ArticlesAdapter(Context context, ClickListener listener) {
        mFavoriteManager = FavoriteManager.getInstance(context);
        mClickListener = listener;
    }

    public void setArticleData(List<Article> articles) {
        if (articles == null) return;
        mArticleData = articles;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_card, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updateViews(mArticleData.get(position), mClickListener);
    }

    @Override
    public int getItemCount() {
        return mArticleData == null ? 0 : mArticleData.size();
    }
}
