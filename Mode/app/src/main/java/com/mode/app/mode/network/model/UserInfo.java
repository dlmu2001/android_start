package com.mode.app.mode.network.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tom on 17-7-21.
 *
 {
 "id": 375162,
 "profile": {
 "userId": 375162,
 "nickname": "clound.huang",
 "avatar": null,
 "bio": null,
 "followerCount": 0,
 "followingCount": 0,
 "invitedBy": null,
 "insUrl": null,
 "youtubeUrl": null,
 "brandType": null,
 "followed": false
 },
 "username": "45267aca-d1ec-4ff2-881c-aaa12dbf4d57",
 "email": "clound.huang@whatsmode.com",
 "mobile": null,
 "authorities": [
 {
 "name": "ROLE_USER"
 }
 ],
 "userChannels": [],
 "status": 1
 }
 */
public class UserInfo implements Serializable{
    private static final long serialVersionUID = -5551226410280018422L;
    @SerializedName("id")
    public String mId;
    @SerializedName("profile")
    public Profile mProfile;
    @SerializedName("username")
    public String mUserName;
    @SerializedName("email")
    public String mEmail;
    @SerializedName("mobile")
    public String mMobile;
    @SerializedName("authorities")
    public List<Authority> mAuthorityList;
    @SerializedName("status")
    public int mStatus;


}
