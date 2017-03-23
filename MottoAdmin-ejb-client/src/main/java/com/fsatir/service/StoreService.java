/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.service;


import com.fsatir.types.Brand;
import com.fsatir.types.Company;
import com.fsatir.types.Inventory;
import com.fsatir.types.ProductCategory;
import com.fsatir.types.Store;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author abdurrahmanturkeri
 */
@Local
public interface StoreService {

    public void saveStore(Store store) throws Exception;
    
    public List<Store> listOfStore();
    
    public List<Store> listOfStoreByProductId(String productId);
    
    public List<Store> listOfStoreByInventory(String inventoryId);
    
    public void deleteStore(Store store) throws Exception ;
    
    public Store getStoreDetail(String storeId) throws Exception ;
    
    
}
