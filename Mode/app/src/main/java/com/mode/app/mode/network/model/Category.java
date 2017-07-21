package com.mode.app.mode.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tom on 17-7-20.
 */
public class Category {
    @SerializedName("id")
    public String mID;
    @SerializedName("name")
    public String mName;
    @SerializedName("description")
    public String mDescription;
    @SerializedName("icon")
    public Icon mIcon;
}
