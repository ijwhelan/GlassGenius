package io.glassjournalism.glassgenius.read;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.glassjournalism.glassgenius.R;
import io.glassjournalism.glassgenius.data.json.Article;
import io.glassjournalism.glassgenius.data.json.Constants;


public class ReadActivity extends Activity {

    private final String TAG = getClass().getName();
    @InjectView(R.id.cardScrollView)
    CardScrollView mCardScroller;
    private List<CardBuilder> mCards = new ArrayList<CardBuilder>();
    private List<Article> articleList = new ArrayList<Article>();
    private ArticleCardScrollAdapter mAdapter;
    private AudioManager audio;

    public final static String EXTRA_MESSAGE = "ARTICLE_TEXT";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_read);
        ButterKnife.inject(this);
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAdapter = new ArticleCardScrollAdapter();
        mCardScroller.setAdapter(mAdapter);
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                audio.playSoundEffect(Sounds.TAP);
                Intent intent = new Intent(ReadActivity.this, SpeedReader.class);
                intent.putExtra(EXTRA_MESSAGE, articleList.get(i).getContents());
                startActivity(intent);
            }
        });

        Ion.with(this)
                .load(Constants.API_ROOT + "/article")
                .as(new TypeToken<List<Article>>() {
                })
                .setCallback(new FutureCallback<List<Article>>() {
                    @Override
                    public void onCompleted(Exception e, List<Article> articles) {
                        if (null != e) Log.d(TAG, e.getMessage());
                        articleList.addAll(articles);
                        Log.d(TAG, "article count: " + articles.size());
                        for (Article article : articles) {
                            new FetchArticleTask().execute(article);
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }

    private class ArticleCardScrollAdapter extends CardScrollAdapter {
        @Override
        public int getPosition(Object item) {
            return mCards.indexOf(item);
        }

        @Override
        public int getCount() {
            return mCards.size();
        }

        @Override
        public Object getItem(int position) {
            return mCards.get(position);
        }

        @Override
        public int getViewTypeCount() {
            return CardBuilder.getViewTypeCount();
        }

        @Override
        public int getItemViewType(int position) {
            return mCards.get(position).getItemViewType();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return mCards.get(position).getView(convertView, parent);
        }
    }

    private class FetchArticleTask extends AsyncTask<Article, Void, Void> {

        @Override
        protected Void doInBackground(Article... articles) {
            Article article = articles[0];
            Bitmap icon = null;
            Bitmap image = null;
            try {
                icon = Ion.with(ReadActivity.this).load(article.getIconURL()).asBitmap().get();
                image = Ion.with(ReadActivity.this).load(article.getThumbnailURL()).asBitmap().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            mCards.add(new CardBuilder(ReadActivity.this, CardBuilder.Layout.AUTHOR)
                    .setText(article.getTitle())
                    .setHeading(article.getPublication())
                    .setSubheading(article.getAuthor())
                    .setFootnote(article.getDate())
                    .setIcon(icon)
                    .setAttributionIcon(R.drawable.glass_genius_glass)
                    .addImage(image));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mAdapter.notifyDataSetChanged();
        }
    }

}