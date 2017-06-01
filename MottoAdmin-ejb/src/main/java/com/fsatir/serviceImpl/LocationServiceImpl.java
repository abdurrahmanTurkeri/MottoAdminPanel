/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.serviceImpl;

import com.fsatir.service.InventoryService;
import com.fsatir.service.LocationService;
import com.fsatir.service.MediaService;
import com.fsatir.service.ProductService;
import com.fsatir.types.Inventory;
import com.fsatir.types.Location;
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
public class LocationServiceImpl extends BaseServiceImpl implements LocationService {

    
    @Override
    public void saveLocation(Location location) throws Exception {
        em = accessEntityManager();
        em.getTransaction().begin();
        em.persist(location);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Location> listOfLocation() {
        List<Location> locationList = new ArrayList<>();
        try {
            em = accessEntityManager();
            if(!em.getTransaction().isActive()){
             em.getTransaction().begin();
            }
            locationList = em.createQuery("from Location").getResultList();
            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return locationList;
    
    }

    @Override
    public List<Location> listOfLocationByStoreId(String storeId) {
   
          List<Location> locationList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("from Location a where a.store.id=:param1");
            query.setParameter("param1", storeId);
            locationList = query.getResultList();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return locationList;
    }

    @Override
    public void deleteLocation(Location location) throws Exception {
        em = accessEntityManager();
        em.getTransaction().begin();   
      //  em.remove(location);
        em.remove(em.contains(location) ? location : em.merge(location));
        em.getTransaction().commit();
        em.close(); 
        
    }

    @Override
    public Location getLocationDetail(String locationId) throws Exception {
        Location location = null;
        em = accessEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("from Location a where a.id=:param1");
        query.setParameter("param1", locationId);
        location = (Location) query.getSingleResult();
        em.getTransaction().commit();
        em.close();

        return location;
    
    }
    

}
