/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.controller;

import com.fsatir.service.LocationService;
import com.fsatir.service.StoreService;
import com.fsatir.types.Location;
import com.fsatir.types.Store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author abdurrahmanturkeri
 */
@Named(value = "storeBean")
@javax.faces.view.ViewScoped
public class StoreBean implements Serializable {

    @Inject
    LocationService locationService;

    @Inject
    StoreService service;

    private Store store = new Store();
    private Store selectedStore;

    private List<Store> storeList;
    private List<Location> locationList;

    /**
     * Creates a new instance of FetvaCayegoryManagedBean
     */
    public StoreBean() {

    }

    @PostConstruct
    public void init() {
        System.out.println("StoreBean Sayfası");
        storeList = service.listOfStore();
    }

    public void saveStore() {
        try {
            service.saveStore(store);
            storeList = service.listOfStore();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("storeList", storeList);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }

    }

    public void deleteStore() {

        try {
            service.deleteStore(selectedStore);
            storeList = service.listOfStore();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("storeList", storeList);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));

        }

    }

    public List<Location> completeLocation(String query) {
        locationList = (List<Location>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("locationList");
        if (locationList == null || locationList.isEmpty()) {
            locationList = locationService.listOfLocation();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("locationList", locationList);
        }
        List<Location> filteredLocations = new ArrayList<>();

        for (Location location : locationList) {
            if (location.getLabel().toLowerCase().startsWith(query.toLowerCase())) {
                filteredLocations.add(location);
            }
        }
        return filteredLocations;
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

    public void onItemSelect(SelectEvent event) {
        Location location = (Location) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lokasyon Seçildi.", location.getLabel() + ", " + location.getExactAddressDesc()));

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

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }
}
