/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.serviceImpl;

import com.fsatir.service.InventoryService;
import com.fsatir.service.MediaService;
import com.fsatir.service.ProductService;
import com.fsatir.types.Inventory;
import com.fsatir.types.Media;
import com.fsatir.types.Product;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Dependent
@Stateless
public class InventoryServiceImpl extends BaseServiceImpl implements InventoryService {

    
    @Override
    public Inventory getInventoryDetail(String inventoryId) throws Exception {
        Inventory inventory = null;
        em = accessEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("from Inventory a where a.id=:param1");
        query.setParameter("param1", inventoryId);
        inventory = (Inventory) query.getSingleResult();
        em.getTransaction().commit();
        em.close();

        return inventory;
    }


    @Override
    public void saveInventory(Inventory inventory) throws Exception {
        em = accessEntityManager();
        em.getTransaction().begin();
        em.persist(inventory);
        em.getTransaction().commit();
        em.close();
        
    
    }

    @Override
    public List<Inventory> listOfInventory() {
        List<Inventory> inventoryList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            inventoryList = em.createQuery("from Inventory").getResultList();
            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inventoryList;
    }

    @Override
    public List<Inventory> listOfInventoryByProductId(String productId) {
    
        List<Inventory> inventoryList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("from Inventory a where a.product.id=:param1");
            query.setParameter("param1", productId);
            inventoryList = query.getResultList();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inventoryList;
    }

    @Override
    public void deleteInventory(Inventory inventory) throws Exception {
        em = accessEntityManager();
        em.getTransaction().begin();
        em.remove(inventory);
        em.getTransaction().commit();
        em.close(); 
    }
    

}
