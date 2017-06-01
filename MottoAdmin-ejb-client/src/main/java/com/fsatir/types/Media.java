/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.types;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author abdurrahmanturkeri
 */
@Entity(name = "Media")
@Table(name = "Media")
public class Media implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String url;
    
    private byte[] mediaData;
    
    private String name;

    /*
     *Media Type jpg avi mp4 mpeg  
     */
    private String sourceType;
    
    
    private String mediaRowNo;
    

    
    
  
   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public byte[] getMediaData() {
        return mediaData;
    }

    public void setMediaData(byte[] mediaData) {
        this.mediaData = mediaData;
    }

    public String getName() {
        return name;
    }

   

    public void setName(String name) {
        this.name = name;
    }

    public String getMediaRowNo() {
        return mediaRowNo;
    }

    public void setMediaRowNo(String value) {
        this.mediaRowNo = mediaRowNo;
    }


    
    
    
    
    

}
