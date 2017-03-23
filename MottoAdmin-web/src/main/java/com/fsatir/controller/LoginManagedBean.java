/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.controller;

import com.fsatir.service.UserService;
import com.fsatir.types.SiteUser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

/**
 *
 * @author abdurrahmanturkeri
 */
@Named(value = "loginManagedBean")
@RequestScoped
public class LoginManagedBean {

    @Inject
    UserService userService;

    private SiteUser siteUser = new SiteUser();
    
    private static final String  HOME_URL_WELCOME="/faces/secure/index.jsf";
    private static final String  HOME_URL_BYE=FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();

    /**
     * Creates a new instance of QuestionManagedBean
     */
    public LoginManagedBean() {
    }

    @PostConstruct
    public void init() {

    }

    public void loginUser() {
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(siteUser.getUserName(), siteUser.getPassword(),false));
            SiteUser siteUserFromDb= userService.loadUser(siteUser.getUserName(), siteUser.getPassword());
            SecurityUtils.getSubject().getSession().setAttribute("siteUser", siteUserFromDb);
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(
                     (ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
            FacesContext.getCurrentInstance().getExternalContext().redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL_WELCOME);
      } catch (Exception ex) {
          
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login Hatasi", "Kullanici Bilgileri Kontrol edilmeli!"));
      }

    }
    
    public void logout() {
        try {
            SecurityUtils.getSubject().logout();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
             FacesContext.getCurrentInstance().getExternalContext().redirect( HOME_URL_BYE);
          
            //siteUser = userService.LoadUser(siteUser.getUserName(), siteUser.getPassword());
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("siteUser", siteUser);
        } catch (Exception ex) {
            Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
    

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }
    
    

}