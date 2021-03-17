package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.data.Photos;
import com.example.finalproject.utils.NetworkUtils;
import com.example.finalproject.utils.UnsplashUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public ArrayList<Photos> photos;
    private ArrayList<PhotoDataItem> photoDataItems;
    private ProgressBar loadingIndicatorPB;
    private TextView errorMessageTV;

    ArrayList<String> imageURLs = new ArrayList<>();
    ArrayList<String> authorNames = new ArrayList<>();

    public PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView forecastListRV = findViewById(R.id.rv_forecast_list);
        forecastListRV.setLayoutManager(new LinearLayoutManager(this));
        forecastListRV.setHasFixedSize(true);

        this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        this.errorMessageTV = findViewById(R.id.tv_error_message);


        this.photos = new ArrayList<Photos>();

        this.photoAdapter = new PhotoAdapter(this, imageURLs, authorNames);
        forecastListRV.setAdapter(photoAdapter);
        photoAdapter.updatePhotoResults(this.photos);

        doWeatherSearch();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.mainactivity, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_view_map:
//                viewOnMap();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    private void viewOnMap() {
//        Uri gmmIntentUri = Uri.parse("geo:44.5646,123.2620");
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        if (mapIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(mapIntent);
//        }
//    }

    private void doWeatherSearch() {
        String url = UnsplashUtils.buildUnsplashUrl("sad", "wTR5nfFF1E5Qib5cwQ59RZubNJQQBHafy-v1RPEMoGU");
        Log.d(TAG, "querying search URL: " + url);
        new WeatherSearchTask().execute(url);
    }


    public class WeatherSearchTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String results = null;
            try {
                results = NetworkUtils.doHttpGet(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String results) {
            loadingIndicatorPB.setVisibility(View.INVISIBLE);
            if (results != null) {
                ArrayList<String> searchResultsList = new ArrayList<>();
                searchResultsList.add(results);

                JSONObject obj = null;
                try {
                    obj = new JSONObject(results);
                    JSONArray resultsArr = obj.getJSONArray("results");

                    for (int i = 0; i < resultsArr.length(); i++) {
                        JSONObject individualImage = resultsArr.getJSONObject(i);

                        JSONObject urls = individualImage.getJSONObject("urls");

//                        JSONObject smallURL = urls.getJSONObject("small");

                        imageURLs.add(urls.getString("small"));

                        JSONObject user = individualImage.getJSONObject("user");

                        authorNames.add(user.getString("name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "Image #: " + imageURLs.size());
                Log.d(TAG, "Author Name: " + authorNames.size());

//                Log.d(TAG, "result list:" + searchResultsList);
                photos = UnsplashUtils.parseWeatherSearchResults(results);
                photoAdapter.updatePhotoResults(photos);
                //adapter shit here
//                Log.d(TAG, "HERE");
//                Log.d(TAG, "result list:" + photos);
                errorMessageTV.setVisibility(View.INVISIBLE);
            } else {
//                searchResultsRV.setVisibility(View.INVISIBLE);
                Log.d(TAG, "Results = null");
                errorMessageTV.setVisibility(View.VISIBLE);
            }
        }
    }
}
