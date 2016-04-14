/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fetva.serviceImpl;

import com.fetva.service.QuestionService;
import com.fetva.types.Question;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@Dependent
@Stateless
public class QuestionServiceImpl implements QuestionService {

    EntityManagerFactory emf;
    EntityManager entityManager;

    // @PersistenceContext 
    // EntityManager entityManager;
    @Override
    public void saveQuestion(Question question) throws Exception {
        emf = Persistence.createEntityManagerFactory("fetva_mongo_db_pu");
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(question);
        entityManager.getTransaction().commit();

        entityManager.close();

    }

    @Override
    public List<Question> listOfQuestion() {
        List<Question> questionList = new ArrayList<>();
        try {

            emf = Persistence.createEntityManagerFactory("fetva_mongo_db_pu");
            entityManager = emf.createEntityManager();
            entityManager.getTransaction().begin();
            questionList = entityManager.createQuery("from Question").getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return questionList;
    }

    @Override
    public void deleteQuestion(Question question) {
        try {

            emf = Persistence.createEntityManagerFactory("fetva_mongo_db_pu");
            entityManager = emf.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(question);
            entityManager.getTransaction().commit();
            entityManager.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    
    }

}
