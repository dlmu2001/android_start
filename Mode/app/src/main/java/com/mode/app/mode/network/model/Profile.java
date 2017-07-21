package com.mode.app.mode.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tom on 17-7-21.
 */
public class Profile implements Serializable{
    private static final long serialVersionUID = 3544934564886291952L;
    @SerializedName("userId")
    public String mUserId;
    @SerializedName("nickname")
    public String mNickName;
    @SerializedName("avatar")
    public String mAvtar;
    @SerializedName("bio")
    public String mBio;
    @SerializedName("followerCount")
    public int mFollowerCount;
    @SerializedName("followingCount")
    public int mFollowingCount;
    @SerializedName("invitedBy")
    public String mInviteBy;
    @SerializedName("insUrl")
    public String mInsUrl;
    @SerializedName("youtubeUrl")
    public String mYoutubeUrl;
    @SerializedName("brandType")
    public String mBrandType;
    @SerializedName("followed")
    public boolean mFollowed;


}
