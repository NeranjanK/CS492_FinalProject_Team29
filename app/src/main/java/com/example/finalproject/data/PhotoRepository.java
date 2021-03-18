package com.example.finalproject.data;

import java.util.List;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.finalproject.MainActivity;

import static com.example.finalproject.MainActivity.*;

public class PhotoRepository {
    private static final String TAG = PhotoRepository.class.getSimpleName();
    private MutableLiveData<List<String>> mSearchImageUrls;
    private MutableLiveData<List<String>> mSearchAuthorNames;

    public PhotoRepository() {
        mSearchImageUrls = new MutableLiveData<>();
        mSearchImageUrls.setValue(null);

        mSearchAuthorNames = new MutableLiveData<>();
        mSearchAuthorNames.setValue(null);
    }

    public LiveData<List<String>> getImageUrls() {
        return mSearchImageUrls;
    }

    public LiveData<List<String>> getAuthorNames() {
        return mSearchAuthorNames;
    }

    public void loadSearchResults(String query) {
        mSearchAuthorNames.setValue(null);
        mSearchImageUrls.setValue(null);
        Log.d(TAG, "search for this query: " + query);
        // Execute search for query...

    }

}
