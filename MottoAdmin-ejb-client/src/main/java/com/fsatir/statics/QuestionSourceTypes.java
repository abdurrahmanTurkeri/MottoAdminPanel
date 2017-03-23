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
public enum QuestionSourceTypes {
    
    FROM_MOBILE("FROM_MOBILE"),FROM_WEB("FROM_WEB"),FROM_ADMIN("FROM_ADMIN");
    
    final String sourceType;
    
     QuestionSourceTypes(String type){
        this.sourceType=type;
    }
    
     public String getSourceType(){
         return this.sourceType;
     }
    
}
