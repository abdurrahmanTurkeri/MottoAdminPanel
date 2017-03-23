/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.types;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;



@XmlAccessorType(XmlAccessType.FIELD)
public class TrendImages implements Serializable{

    private String user_screenName;
    private int retweet_count;
    private int favorite_count;
    private String trendImgURL;

    public TrendImages() {

    }

  

    public String getUser_screenName() {
        return user_screenName;
    }

    public void setUser_screenName(String user_screenName) {
        this.user_screenName = user_screenName;
    }

    public int getRetweet_count() {
        return retweet_count;
    }

    public void setRetweet_count(int retweet_count) {
        this.retweet_count = retweet_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public String getTrendImgURL() {
        return trendImgURL;
    }

    public void setTrendImgURL(String trendImgURL) {
        this.trendImgURL = trendImgURL;
    }

}

