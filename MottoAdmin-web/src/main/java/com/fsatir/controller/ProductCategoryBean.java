/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.controller;

import com.fsatir.service.ProductCategoryService;
import com.fsatir.types.ProductCategory;

import java.io.Serializable;
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
@Named(value = "categoryManagedBean")
@javax.faces.view.ViewScoped
public class ProductCategoryBean implements Serializable{

    @Inject
    ProductCategoryService service;

    private ProductCategory productCategory = new ProductCategory();
    private ProductCategory selectedCategory;

    private List<ProductCategory> categoryList;

    /**
     * Creates a new instance of FetvaCayegoryManagedBean
     */
    public ProductCategoryBean() {
    }

    @PostConstruct
    public void init() {
        categoryList = service.listOfCategory();
    }

    public void saveCategory() {
        try {
            service.saveCategory(productCategory);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
        categoryList = service.listOfCategory();

    }

    public void deleteCategory() {

        try {
            service.deleteCategory(selectedCategory);
            categoryList = service.listOfCategory();
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));

        }

    }

    public void updateCategory() {

    }

    public void showFetvaList() {

    }

    public ProductCategoryService getService() {
        return service;
    }

    public void setService(ProductCategoryService service) {
        this.service = service;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductCategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(ProductCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<ProductCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ProductCategory> categoryList) {
        this.categoryList = categoryList;
    }

   
}
