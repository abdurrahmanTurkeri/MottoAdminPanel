/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.service;

import javax.persistence.EntityManager;

/**
 *
 * @author abdurrahmanturkeri
 */
public interface BaseService {
    
    EntityManager accessEntityManager();
    
    
}
