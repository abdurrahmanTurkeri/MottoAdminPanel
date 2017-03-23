/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.service;


import com.fsatir.types.Brand;
import com.fsatir.types.Company;
import com.fsatir.types.Inventory;
import com.fsatir.types.Location;
import com.fsatir.types.ProductCategory;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author abdurrahmanturkeri
 */
@Local
public interface LocationService {

    public void saveLocation(Location location) throws Exception;
    
    public List<Location> listOfLocation();
    
    public List<Location> listOfLocationByStoreId(String storeId);
    
    public void deleteLocation(Location location) throws Exception ;
    
    public Location getLocationDetail(String locationId) throws Exception ;
    
    
}
