package com.example.finalproject;

public class PhotoDataItem {
    private String photoUrl;
    private String authorName;

    public PhotoDataItem(String photoUrl, String authorName) {
        this.photoUrl = photoUrl;
        this.authorName = authorName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getAuthorName() {
        return authorName;
    }


}
