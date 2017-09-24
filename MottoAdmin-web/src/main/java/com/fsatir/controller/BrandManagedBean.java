/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.controller;

import com.fsatir.service.BrandService;
import com.fsatir.types.Brand;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.TreeNode;

/**
 *
 * @author abdurrahmanturkeri
 */
@Named(value = "brandBean")
@javax.faces.view.ViewScoped
public class BrandManagedBean implements Serializable {

    @Inject
    BrandService service;

    private Brand brand = new Brand();
    private Brand selectedBrand;
    private List<Brand> brandList;

   
    public BrandManagedBean() {
    }

    @PostConstruct
    public void init() {
        brandList = service.listOfBrand();

    }

    public void saveBrand() {
        try {
            service.saveBrand(brand);
            brand = new Brand();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
        brandList = service.listOfBrand();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("brandList", brandList);
    }
    
    

    public void deleteBrand() {

        try {
            service.deleteBrand(selectedBrand);
            brandList = service.listOfBrand();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("brandList", brandList);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));

        }

    }

    public void updateBrand() {

    }

    public void showFetvaList() {

    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Brand getSelectedBrand() {
        return selectedBrand;
    }

    public void setSelectedBrand(Brand selectedBrand) {
        this.selectedBrand = selectedBrand;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }
    
    
    

}
