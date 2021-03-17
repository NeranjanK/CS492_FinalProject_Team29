package com.example.finalproject.data;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Photos implements Serializable {
    @SerializedName("id")
    public String id;

    @SerializedName("width")
    public Integer width;

    @SerializedName("height")
    public Integer height;

    @SerializedName("urls")
    public Urls urls;

    @SerializedName("user")
    public User user;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }


}
