package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
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




import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;




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






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    // Determines if Action bar item was selected. If true then do corresponding action.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_camera:
                intent = new Intent(MainActivity.this,Camera.class);
                startActivity(intent);
                return true;
            case R.id.action_photos:
                Intent i;
                PackageManager manager = getPackageManager();
                try {
                    i = manager.getLaunchIntentForPackage("com.google.android.apps.photos");
                    if (i == null)
                        throw new PackageManager.NameNotFoundException();
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(i);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                    return false;
                }
            default:
                return super.onOptionsItemSelected(item);
        }

//        //handle presses on the action bar items
//        int id = item.getItemId();
//
//        com.google.android.apps.photos
//        if (id==R.id.action_camera){
//
//            Intent intent = new Intent(MainActivity.this,Camera.class);
//            startActivity(intent);
//
//            return true;
//        }

//        return super.onOptionsItemSelected(item);

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
        String url = UnsplashUtils.buildUnsplashUrl("happy", "wTR5nfFF1E5Qib5cwQ59RZubNJQQBHafy-v1RPEMoGU");
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

//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            getMenuInflater().inflate(R.menu.main, menu);
//            return true;
//        }


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
