package com.example.finalproject.utils;

import android.net.Uri;

import com.example.finalproject.data.Photos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class UnsplashUtils {

    private static final String UNSPLASH_BASE_URL = "https://api.unsplash.com/search/photos";
    private static final String UNSPLASH_PAGES = "page";
    private static final String UNSPLASH_QUERY = "query";
    private static final String UNSPLASH_CLIENT_ID = "client_id";

    private static class UnsplashSearchResults{
        public ArrayList<Photos> list;

    }


    public static String buildUnsplashUrl(String query, String clientId) {
        return Uri.parse(UNSPLASH_BASE_URL).buildUpon()
                .appendQueryParameter(UNSPLASH_PAGES, "1")
                .appendQueryParameter(UNSPLASH_QUERY, query)
                .appendQueryParameter(UNSPLASH_CLIENT_ID, clientId)
                .build()
                .toString();
    }

    public static ArrayList<Photos> parseWeatherSearchResults(String json) {
        Gson gson = new GsonBuilder().create();
        UnsplashSearchResults results = gson.fromJson(json, UnsplashSearchResults.class);
        return results != null ? results.list : null;
    }


}
