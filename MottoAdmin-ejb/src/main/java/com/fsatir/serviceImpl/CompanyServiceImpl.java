/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.serviceImpl;


import com.fsatir.service.CompanyService;
import com.fsatir.types.Company;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author abdurrahmanturkeri
 */
@Stateless
public  class CompanyServiceImpl extends BaseServiceImpl implements CompanyService {

    @Override
    public void saveCompany(com.fsatir.types.Company company) throws Exception {

        em = accessEntityManager();
        em.getTransaction().begin();
        em.persist(company);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<com.fsatir.types.Company> listOfCompany() {
        em = accessEntityManager();
        List<Company> companyList = new ArrayList<>();
        em.getTransaction().begin();
        companyList = em.createQuery("from Company").getResultList();
        em.getTransaction().commit();
        em.close();
        return companyList;
    }

    @Override
    public void deleteCompany(com.fsatir.types.Company company) throws Exception {
        em = accessEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(company) ? company : em.merge(company));
        em.getTransaction().commit();
        em.close();
    }

   

    
}
