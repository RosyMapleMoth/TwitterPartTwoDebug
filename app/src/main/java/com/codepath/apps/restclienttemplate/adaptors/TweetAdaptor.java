package com.codepath.apps.restclienttemplate.adaptors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepath.apps.restclienttemplate.GlideApp;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimeFormatter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetAdaptor extends  RecyclerView.Adapter<TweetAdaptor.ViewHolder> {

    Context context;
    ArrayList<Tweet> tweets;

    public TweetAdaptor(Context context, ArrayList<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);

        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void clear()
    {
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> tweets)
    {
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePic, ivMedia;
        TextView tvUsername,tvBody,tvRelAge;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMedia = itemView.findViewById(R.id.ivMedia);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvUsername = itemView.findViewById(R.id.tvName);
            tvRelAge = itemView.findViewById(R.id.tvRelAge);
            tvBody = itemView.findViewById(R.id.tvBody);

        }

        public void bind(Tweet tweet)
        {


            if (tweet.Media != "")
            {
                int radius = 1,margin=2;
                GlideApp.with(context).load(tweet.Media).apply(new RequestOptions().transforms(new RoundedCorners(30))).override(500,400).into(ivMedia);
                ivMedia.setVisibility(View.VISIBLE);
                Log.i("MediaLoading","tweet " + tweet.id + " has media : " + tweet.Media);
            }
            else
            {
                ivMedia.setVisibility(View.GONE);
                Log.i("MediaLoading ERROR","tweet " + tweet.id + " has media : " + tweet.Media);
            }
            tvRelAge.setText(TimeFormatter.getTimeDifference(tweet.timestamp));
            tvBody.setText(tweet.body);
            tvUsername.setText(tweet.user.name);
            GlideApp.with(context).load(tweet.user.profilePicture).override(400,400).apply(RequestOptions.circleCropTransform()).into(ivProfilePic);
        }
    }

}
