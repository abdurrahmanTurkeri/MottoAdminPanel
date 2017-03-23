/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.serviceImpl;

import com.fsatir.service.UserService;
import com.fsatir.types.SiteUser;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.Query;

@Dependent
@Stateless
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    @Override
    public SiteUser saveUser(SiteUser siteUser) {
        em = accessEntityManager();
        em.getTransaction().begin();
        em.persist(siteUser);
        em.getTransaction().commit();
        em.close();
        return siteUser;
    }

    @Override
    public List<SiteUser> listOfUser() {
        List<SiteUser> siteUserList = new ArrayList<>();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            siteUserList = em.createQuery("from SiteUser").getResultList();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return siteUserList;

    }

    @Override
    public SiteUser loadUser(String userName, String password) {
        SiteUser siteUser = new SiteUser();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("select a from SiteUser a where  a.userName=:param1 and a.password=:param2");
            query.setParameter("param1", userName);
            query.setParameter("param2", password);
            siteUser = (SiteUser) query.getSingleResult();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return siteUser;

    }
    
    
     @Override
    public SiteUser getUserProfile(String userName) {
        SiteUser siteUser = new SiteUser();
        try {
            em = accessEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("select a from SiteUser a where a.userName=:param1");
            query.setParameter("param1", userName);
            siteUser = (SiteUser) query.getSingleResult();
            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return siteUser;

    }

}
