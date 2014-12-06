package io.glassjournalism.glassgenius.video;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.glass.widget.CardScrollAdapter;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.glassjournalism.glassgenius.R;
import io.glassjournalism.glassgenius.data.json.VideoResponse;

public class VideoCardAdapter extends CardScrollAdapter {

    private final String TAG = getClass().getName();
    private List<VideoResponse> cardList = new ArrayList<VideoResponse>();
    private Map<String, Bitmap> imageList = new HashMap<String, Bitmap>();
    private Activity mActivity;

    public VideoCardAdapter(Activity activity) {
        this.mActivity = activity;
        notifyDataSetChanged();
    }

    public void addVideos(final List<VideoResponse> videoResponses) {
        cardList.addAll(videoResponses);
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = View.inflate(mActivity, R.layout.video_card_layout, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        VideoResponse video = cardList.get(position);
        Log.d(TAG, video.getUrl());
        holder.videoTitle.setText(video.getName());
        Ion.with(holder.videoThumb).load(video.getThumbnail());
        Log.d(TAG, video.getThumbnail());
        return view;
    }


    @Override
    public int getPosition(Object o) {
        return 0;
    }

    static class ViewHolder {
        @InjectView(R.id.videoThumb)
        ImageView videoThumb;
        @InjectView(R.id.videoTitle)
        TextView videoTitle;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }
}