/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.types;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author abdurrahmanturkeri
 */
@Entity(name = "Location")
@Table(name = "LOCATION")
public class Location implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String longtitudeValue;
    
    private String latitudeValue;

    private String exactAddressDesc;
    
    private String label;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongtitudeValue() {
        return longtitudeValue;
    }

    public void setLongtitudeValue(String longtitudeValue) {
        this.longtitudeValue = longtitudeValue;
    }

    public String getLatitudeValue() {
        return latitudeValue;
    }

    public void setLatitudeValue(String latitudeValue) {
        this.latitudeValue = latitudeValue;
    }

    public String getExactAddressDesc() {
        return exactAddressDesc;
    }

    public void setExactAddressDesc(String exactAddressDesc) {
        this.exactAddressDesc = exactAddressDesc;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    
    
    

}
