/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.controller;

import com.fsatir.service.UserService;
import com.fsatir.types.SiteUser;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author abdurrahmanturkeri
 */
@Named(value = "userManagedBean")
@RequestScoped
public class UserManagedBean {

    @Inject
    UserService userService;

    private SiteUser siteUser=new SiteUser();
    private List<SiteUser> userList;

    /**
     * Creates a new instance of QuestionManagedBean
     */
    public UserManagedBean() {
    }

    @PostConstruct
    public void init() {
        userList = userService.listOfUser();
    }

    public void saveUser() {
        try {
            
            userService.saveUser(siteUser);
        } catch (Exception ex) {
            Logger.getLogger(UserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        userList = userService.listOfUser();
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

    public List<SiteUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SiteUser> userList) {
        this.userList = userList;
    }

    
    

}
