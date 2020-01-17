package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.apps.restclienttemplate.adaptors.TweetAdaptor;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcException;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;

    public ChirpClient client;
    public RecyclerView rvTweets;
    public ArrayList<Tweet> tweets;
    public TweetAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);



        swipeRefreshLayout = findViewById(R.id.svTimeline);

        rvTweets = findViewById(R.id.rvTweet);
        tweets = new ArrayList<>();
        adaptor = new TweetAdaptor(this, tweets);

        rvTweets.setAdapter(adaptor);
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        client = ChirpApplication.getRestClient(this);
        populateHomeTimeline(0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateHomeTimeline(0);
            }
        });
    }


    private void populateHomeTimeline(int page) {
        client.getHomeTimeline(page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json)
            {
                Log.i("JsonCheck", json.toString());

                JSONArray jsonArray = json.jsonArray;
                adaptor.clear();
                tweets.addAll(Tweet.fromJson(jsonArray));
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable)
            {
                Log.e("populateHomeTimeline", "unable to populate hometimeline exit with response : " + response);
            }
        });

    }
}
