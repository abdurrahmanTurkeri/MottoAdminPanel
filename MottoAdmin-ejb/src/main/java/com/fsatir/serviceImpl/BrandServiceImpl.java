/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.serviceImpl;

import com.fsatir.service.BrandService;
import com.fsatir.service.MediaService;
import com.fsatir.service.ProductService;
import com.fsatir.types.Brand;
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
public class BrandServiceImpl extends BaseServiceImpl implements BrandService {

    
    

   

    @Override
    public List<Brand> listOfBrand() {

        List<Brand> brandList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            brandList = em.createQuery("from Brand").getResultList();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return brandList;
    }

    @Override
    public List<Brand> listOfBrandByCompany(String companyId) {

        List<Brand> brandList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("from Brand a where a.company.id=:param1");
            query.setParameter("param1", companyId);
            brandList = query.getResultList();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return brandList;
    }

   

    @Override
    public void saveBrand(Brand brand) throws Exception {
        em = accessEntityManager();
        em.getTransaction().begin();
        em.persist(brand);
        em.getTransaction().commit();
        em.close();
        
     }

    
    @Override
    public void deleteBrand(Brand brand) throws Exception {
        
        em = accessEntityManager();
        em.getTransaction().begin();
        em.remove(brand);
        em.getTransaction().commit();
        em.close(); 
    }

   

}
