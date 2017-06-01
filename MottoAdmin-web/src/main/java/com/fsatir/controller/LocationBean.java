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
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author abdurrahmanturkeri
 */
@Named(value = "locationBean")
@javax.faces.view.ViewScoped
public class LocationBean implements Serializable {

    @Inject
    LocationService service;

    @Inject
    StoreService storeService;

    private Location location = new Location();
    private Location selectedLocation;

    private List<Location> locationList;

    private List<Store> selectedStoreList = new ArrayList<>();

    private List<Store> storeList = new ArrayList<>();

    private MapModel draggableModel;

    private Marker marker;

    /**
     * Creates a new instance of FetvaCayegoryManagedBean
     */
    public LocationBean() {
    }

    @PostConstruct
    public void init() {
        locationList = service.listOfLocation();

        draggableModel = new DefaultMapModel();

        //Shared coordinates
        LatLng coord1 = new LatLng(41.0078815, 29.1571111);

        //Draggable
        draggableModel.addOverlay(new Marker(coord1, "Modoko"));
        for (Marker premarker : draggableModel.getMarkers()) {
            premarker.setDraggable(true);
        }
    }

    public void onMarkerDrag(MarkerDragEvent event) {
        marker = event.getMarker();
        location.setLatitudeValue(String.valueOf(marker.getLatlng().getLat()));
        location.setLongtitudeValue(String.valueOf(marker.getLatlng().getLng()));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Dragged", "Lat:" + marker.getLatlng().getLat() + ", Lng:" + marker.getLatlng().getLng()));

    }

    public void saveLocation() {
        try {
            service.saveLocation(location);
            location = new Location();  // Sayfa yenilenmediği için locationId değişmiyor.LocationId'yi değiştirmek için. 
            locationList = service.listOfLocation();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("locationList", locationList);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
        //locationList = service.listOfLocation();

    }

    public void deleteLocation() {

        try {

            service.deleteLocation(selectedLocation);
            location = new Location();
            locationList = service.listOfLocation();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("locationList", locationList);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));

        }

    }

    public List<Store> completeStore(String query) {
        storeList = (List<Store>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("storeList");
        if (storeList == null || storeList.isEmpty()) {
            storeList = storeService.listOfStore();
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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kategori eklendi.", ((Store) event.getObject()).getName()));
        selectedStoreList.add((Store) event.getObject());

    }

    public void fillSelectedCategory(Location location) {
        selectedLocation = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(Location selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Store> getSelectedStoreList() {
        return selectedStoreList;
    }

    public void setSelectedStoreList(List<Store> selectedStoreList) {
        this.selectedStoreList = selectedStoreList;
    }

    public List<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }

    public MapModel getDraggableModel() {
        return draggableModel;
    }

    public void setDraggableModel(MapModel draggableModel) {
        this.draggableModel = draggableModel;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

}
