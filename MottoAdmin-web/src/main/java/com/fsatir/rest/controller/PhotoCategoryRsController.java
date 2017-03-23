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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author abdurrahmanturkeri
 */
@Path("/kategori")
@RequestScoped
public class PhotoCategoryRsController {

    @Inject
    ProductCategoryService categoryService;

    /**
     * Creates a new instance of FetvaRsController
     */
    public PhotoCategoryRsController() {
    }

    @GET
    @Path("/kategoriListesi")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public CategoryContainer getFetvaCategoryList() {
        try {
            List<ProductCategory> result = categoryService.listOfCategory();

            CategoryContainer categoryContainer = new CategoryContainer();
            categoryContainer.setMediaCategoryList(result);
            return categoryContainer;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
