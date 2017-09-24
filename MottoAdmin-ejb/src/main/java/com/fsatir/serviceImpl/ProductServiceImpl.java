/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.serviceImpl;

import com.fsatir.service.MediaService;
import com.fsatir.service.ProductService;
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
public class ProductServiceImpl extends BaseServiceImpl implements ProductService {

    
    EntityManager entityManager;

   

    @Override
    public List<Product> listOfProduct() throws Exception {

        List<Product> productList = new ArrayList<>();
        try {
            entityManager = accessEntityManager();
             if(!entityManager.getTransaction().isActive()){
                entityManager.getTransaction().begin();
               }
            productList = entityManager.createQuery("from Product").getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return productList;
    }

    @Override
    public List<Product> listOfProductByCategory(String productCategoryId) throws Exception {

        List<Product> productList = new ArrayList<>();
        try {
            entityManager = accessEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("from Product a where a.category.id=:param1");
            query.setParameter("param1", productCategoryId);
            productList = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product getProductDetail(String productId) throws Exception {
        Product product = null;
        entityManager = accessEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from Product a where a.id=:param1");
        query.setParameter("param1", productId);
        product = (Product) query.getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();

        return product;
    }

    @Override
    public Product saveProduct(Product product) throws Exception {
        entityManager = accessEntityManager();
        if(!entityManager.getTransaction().isActive()){
         entityManager.getTransaction().begin();
        }
        entityManager.persist(em.contains(product) ? product : em.merge(product));
        entityManager.getTransaction().commit();
        entityManager.close();
        return product;
     }

    
        @Override
    public void deleteProduct(List<Product> productList) throws Exception {
        
        entityManager = accessEntityManager();
        entityManager.getTransaction().begin();
        for(Product product:productList){
            entityManager.remove(product);
        }
        entityManager.getTransaction().commit();
        entityManager.close(); 
    }

}
