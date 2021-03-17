package com.example.finalproject.data;


import java.io.Serializable;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class User implements Serializable {
    @SerializedName("name")
    public String name;
}
