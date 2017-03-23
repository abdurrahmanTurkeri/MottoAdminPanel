/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.twitter;

import com.fsatir.statics.TwitterInfos;
import com.fsatir.types.TrendImages;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author abdurrahmanturkeri
 */
@Named(value = "twitterManagedBean")
@Dependent
public class TwitterManagedBean {
    
    public static final int TURKEY_WOEID = 23424969;
    List<TrendImages> trendImagesList =new ArrayList<>();
    
    public TwitterManagedBean() {
    }
    
    @PostConstruct
    public void init(){
        try{
            Twitter twitter=bringMyTwitterInstance();
            Trends trends = twitter.getPlaceTrends(TURKEY_WOEID);
            
            for(Trend trend:trends.getTrends()){
               trendImageList(twitter, trend.getName());
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public List<TrendImages> trendImageList(Twitter twitter,String trendName){
        
        Query query = new Query(trendName+" AND filter:images");
        query.setCount(10000);
        QueryResult result;
        try {
                do {
                    result = twitter.search(query);

                    List<twitter4j.Status> tweets = result.getTweets();
                    int counter = 0;
                    for (twitter4j.Status status : tweets)
                    {
                        TrendImages trendImages = new TrendImages();
                        int control=0;
                        for(MediaEntity me : status.getMediaEntities())
                        {
                            //Tek görsel almayı kesinleştirmek için kontrol.
                            //İleride modeldeki trendImgURL diziye dönüştürülerek kontrol kaldırılabilir.
                           if(control < 1)
                                trendImages.setTrendImgURL(me.getMediaURLHttps());
                            control++;
                        }
                        // URL null değilse, görsel ve bigileri list'e eklenir.
                        if(trendImages.getTrendImgURL() != null)
                        {
                            trendImages.setFavorite_count(status.getFavoriteCount());
                            trendImages.setRetweet_count(status.getRetweetCount());
                            trendImages.setUser_screenName(status.getUser().getScreenName());
                            trendImagesList.add(counter++,trendImages);
                        }
                    }
               }
                while ((query = result.nextQuery()) != null);

            } catch (TwitterException e) {
                e.printStackTrace();
            }
        
        return trendImagesList;
        
    }
    
    
    
    
    
     public Twitter bringMyTwitterInstance() throws TwitterException{
        
        String consumerKey = TwitterInfos.CONSUMER_KEY.getCredentialValue();
        String consumerSecret = TwitterInfos.CONSUMER_SECRET.getCredentialValue();
        String oAuthToken = TwitterInfos.OAUTH_TOKEN.getCredentialValue();
        String oAuthSecret = TwitterInfos.OAUTH_SECRET.getCredentialValue();

        
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(consumerKey);
        builder.setOAuthConsumerSecret(consumerSecret);
        Configuration configuration = builder.build();
        TwitterFactory factory = new TwitterFactory(configuration);
        Twitter twitter = factory.getInstance();
        return twitter;
    }

    public List<TrendImages> getTrendImagesList() {
        return trendImagesList;
    }

    public void setTrendImagesList(List<TrendImages> trendImagesList) {
        this.trendImagesList = trendImagesList;
    }
     
     
    
}
