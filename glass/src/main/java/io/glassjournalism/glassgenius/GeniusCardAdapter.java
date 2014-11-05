package io.glassjournalism.glassgenius;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.android.glass.widget.CardScrollAdapter;

import java.util.ArrayList;
import java.util.List;

import io.glassjournalism.glassgenius.data.json.Constants;
import io.glassjournalism.glassgenius.data.json.GeniusCard;
import io.glassjournalism.glassgenius.data.json.GlassGeniusAPI;
import retrofit.RestAdapter;

public class GeniusCardAdapter extends CardScrollAdapter {

    private final String TAG = getClass().getName();
    private List<GeniusCard> cardList;
    private GlassGeniusAPI glassGeniusAPI;
    private Activity mActivity;

    public GeniusCardAdapter(Activity activity) {
        this.cardList = new ArrayList<GeniusCard>();
        this.mActivity = activity;
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constants.API_ROOT).build();
        glassGeniusAPI = restAdapter.create(GlassGeniusAPI.class);
    }

    public void addCard(GeniusCard card) {
        Log.d(TAG, "added: " + card.getName());
        cardList.add(0, card);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cardList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        GeniusCard card = cardList.get(position);
        WebView webView = new WebView(mActivity);
        webView.setInitialScale(100);
        webView.setBackgroundColor(Color.TRANSPARENT);
        String url = Constants.API_ROOT + "/card/preview/" + card.getId();
        Log.d(TAG, url);
        webView.loadUrl(url);
        return webView;
    }

    @Override
    public int getPosition(Object o) {
        return 0;
    }
}
