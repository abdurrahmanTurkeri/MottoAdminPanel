/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.serviceImpl;

import com.fsatir.service.StoreService;
import com.fsatir.types.Store;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.Query;

@Dependent
@Stateless
public class StoreServiceImpl extends BaseServiceImpl implements StoreService {

    @Override
    public void saveStore(Store store) throws Exception {

        em = accessEntityManager();
        em.getTransaction().begin();
        em.persist(store);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Store> listOfStore() {
        List<Store> storeList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            storeList = em.createQuery("from Store").getResultList();
            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return storeList;

    }

    @Override
    public List<Store> listOfStoreByProductId(String productId) {

        List<Store> storeList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("from Store a where a.inventory.product.id=:param1");
            query.setParameter("param1", productId);
            storeList = query.getResultList();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return storeList;
    }

    @Override
    public List<Store> listOfStoreByInventory(String inventoryId) {

        List<Store> storeList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("from Store a where a.inventory.id=:param1");
            query.setParameter("param1", inventoryId);
            storeList = query.getResultList();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return storeList;
    }

    @Override
    public void deleteStore(Store store) throws Exception {
        em = accessEntityManager();
        em.getTransaction().begin();
        //em.remove(store);
        em.remove(em.contains(store) ? store : em.merge(store));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Store getStoreDetail(String storeId) throws Exception {
        Store store = null;
        em = accessEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("from Store a where a.id=:param1");
        query.setParameter("param1", storeId);
        store = (Store) query.getSingleResult();
        em.getTransaction().commit();
        em.close();

        return store;

    }

}
