/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.statics;

/**
 *
 * @author abdurrahmanturkeri
 */
public enum TwitterInfos {
    
    CONSUMER_KEY("CONSUMER_KEY","3N0HoJIzv2WtAYv1z1KpYq8jk"),
    CONSUMER_SECRET("CONSUMER_SECRET","uKJUJp9Slp54kxCnvgVFoxOMGmzdYy7OaBfGW3tsYaKtOoD7dA"),
    OAUTH_TOKEN("OAUTH_TOKEN",""),
    OAUTH_SECRET("OAUTH_SECRET","");
    
    final String code;
    final String  value;
     
     TwitterInfos(String code,String value){
        this.code=code;
        this.value=value;
     }
    
     public String getCredentialValue(){
         return this.value;
     }
    
}
