/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.rest.controller;

import com.fsatir.service.ProductCategoryService;
import com.fsatir.types.ProductCategory;


import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * Kategori listesini donen Servis
 */
@Path("/kategori")
@RequestScoped
public class CategoryRestService {

    @Inject
    ProductCategoryService categoryService;

    /**
     * Creates a new instance of FetvaRsController
     */
    public CategoryRestService() {
    }
    /**
     * Tum kategoriler doner
     * @return 
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public CategoryContainer getCategoryList() {
        try {
            List<ProductCategory> result = categoryService.listOfCategory();
            CategoryContainer categoryContainer = new CategoryContainer();
            categoryContainer.setCategoryList(result);
            return categoryContainer;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * 
     * @param categoryId 
     * @return categoryId  parameterisinin altinda kategori var ise onu doner
     */
    @GET
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public CategoryContainer getFetvaCategoryList(@PathParam("categoryId")String categoryId) {
        try {
            List<ProductCategory> result = categoryService.categoryListByUpperCategory(categoryId);
            CategoryContainer categoryContainer = new CategoryContainer();
            categoryContainer.setCategoryList(result);
            return categoryContainer;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
