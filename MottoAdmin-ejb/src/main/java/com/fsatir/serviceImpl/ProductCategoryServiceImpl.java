/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.serviceImpl;

import com.fsatir.service.ProductCategoryService;
import com.fsatir.types.Brand;
import com.fsatir.types.ProductCategory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.Query;

@Dependent
@Stateless
public class ProductCategoryServiceImpl extends BaseServiceImpl implements ProductCategoryService {

    @Override
    public void saveCategory(ProductCategory fetvaCategory) throws Exception {

        em = accessEntityManager();
        em.getTransaction().begin();
        em.persist(em.contains(fetvaCategory) ? fetvaCategory : em.merge(fetvaCategory));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<ProductCategory> listOfCategory() {
        em = accessEntityManager();
        List<ProductCategory> categoryList = new ArrayList<>();
        em.getTransaction().begin();
        categoryList = em.createQuery("from ProductCategory").getResultList();
        em.getTransaction().commit();
        em.close();
        return categoryList;
    }

    @Override
    public List<ProductCategory> listOfCategoryByBrand(String brandId) {

        List<ProductCategory> productCategoryList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("from ProductCategory a where a.brand.id=:param1");
            query.setParameter("param1", brandId);
            productCategoryList = query.getResultList();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return productCategoryList;
    }

    @Override
    public void deleteCategory(ProductCategory fetvaCategory) throws Exception {
        em = accessEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(fetvaCategory) ? fetvaCategory : em.merge(fetvaCategory));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void updateUpperCategory(ProductCategory productCategory, ProductCategory upperCategory) {
        em = accessEntityManager();
        ProductCategory data = em.find(ProductCategory.class, productCategory.getId());

        em.getTransaction().begin();
        if (data != null) {
            data.setUpperCategory(upperCategory);
        }
        em.getTransaction().commit();
    }

    @Override
    public List<ProductCategory> listOfLeafCategory() {
        List<ProductCategory> productCategoryList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("from ProductCategory a where a.isLeaf=1");
            productCategoryList = query.getResultList();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return productCategoryList;
    }

    @Override
    public List<ProductCategory> categoryListByUpperCategory(String categoryId) {
        List<ProductCategory> productCategoryList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            //Query query = em.createQuery("from ProductCategory a where a.upperCategory.id=:categoryId");
            String nativeQuery = "db.ProductCategory.find({'upperCategory_id':'"+categoryId+"'})";
            System.out.println(nativeQuery);
            Query query = em.createNativeQuery(nativeQuery,ProductCategory.class);
           // query.setParameter("categoryId", categoryId);
            productCategoryList = query.getResultList();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return productCategoryList;
    }

}
