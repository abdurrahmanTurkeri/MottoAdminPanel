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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author abdurrahmanturkeri
 */
@Local
public interface InventoryService {

    public void saveInventory(Inventory inventory) throws Exception;
    
    public List<Inventory> listOfInventory();
    
    public List<Inventory> listOfInventoryByProductId(String productId);
    
    public void deleteInventory(Inventory inventory) throws Exception ;
    
    public Inventory getInventoryDetail(String inventoryId) throws Exception ;
    
    
}
