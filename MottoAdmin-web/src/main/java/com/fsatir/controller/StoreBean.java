/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.controller;

import com.fsatir.service.LocationService;
import com.fsatir.service.StoreService;
import com.fsatir.types.Store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author abdurrahmanturkeri
 */
@Named(value = "storeBean")
@javax.faces.view.ViewScoped
public class StoreBean implements Serializable{

    @Inject
    LocationService locationService;
    
    @Inject
    StoreService service;

    private Store store = new Store();
    private Store selectedStore;

    private List<Store> storeList;
    
    

   
    
    
    /**
     * Creates a new instance of FetvaCayegoryManagedBean
     */
    public StoreBean() {
    }

    @PostConstruct
    public void init() {
        storeList = service.listOfStore();
    }
    
     

    public void saveStore() {
        try {
            service.saveStore(store);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
        //locationList = service.listOfLocation();

    }

    public void deleteStore() {

        try {
            service.deleteStore(selectedStore);
            storeList = service.listOfStore();
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));

        }

    }
    
    public List<Store> completeStore(String query) {
        storeList = (List<Store>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("storeList");
        if (storeList == null || storeList.isEmpty()) {
            storeList = service.listOfStore();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("storeList", storeList);
        }
        List<Store> filteredStores = new ArrayList<>();

        for (Store store : storeList) {
            if (store.getName().toLowerCase().startsWith(query.toLowerCase())) {
                filteredStores.add(store);
            }
        }

        return filteredStores;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Store getSelectedStore() {
        return selectedStore;
    }

    public void setSelectedStore(Store selectedStore) {
        this.selectedStore = selectedStore;
    }

    public List<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }
    
     
   
}
