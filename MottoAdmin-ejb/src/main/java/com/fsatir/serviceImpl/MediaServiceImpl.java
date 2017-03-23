/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.serviceImpl;

import com.fsatir.service.MediaService;
import com.fsatir.types.Media;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Dependent
@Stateless
public class MediaServiceImpl extends BaseServiceImpl implements MediaService {

    
    EntityManager entityManager;

   

    @Override
    public List<Media> listOfMedia() throws Exception {

        List<Media> mediaList = new ArrayList<>();
        try {
            entityManager = accessEntityManager();
            entityManager.getTransaction().begin();
            mediaList = entityManager.createQuery("from Media").getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mediaList;
    }

    @Override
    public List<Media> listOfMediaByCategory(String mediaCategoryId) throws Exception {

        List<Media> mediaList = new ArrayList<>();
        try {
            entityManager = accessEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("from Media a where a.category.id=:param1");
            query.setParameter("param1", mediaCategoryId);
            mediaList = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mediaList;
    }

    @Override
    public Media getMediaDetail(String mediaId) throws Exception {
        Media media = null;
        entityManager = accessEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from Media a where a.id=:param1");
        query.setParameter("param1", mediaId);
        media = (Media) query.getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();

        return media;
    }

    @Override
    public Media saveMedia(Media media) throws Exception {
        entityManager = accessEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(media);
        entityManager.getTransaction().commit();
        entityManager.close();
        return media;
     }

    
        @Override
    public void deleteMedia(List<Media> mediaList) throws Exception {
        
        entityManager = accessEntityManager();
        entityManager.getTransaction().begin();
        for(Media media:mediaList){
            entityManager.remove(media);
        }
        entityManager.getTransaction().commit();
        entityManager.close(); 
    }

}
