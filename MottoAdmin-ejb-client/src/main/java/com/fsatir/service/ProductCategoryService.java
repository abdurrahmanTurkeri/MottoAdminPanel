/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.service;

import com.fsatir.types.ProductCategory;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ProductCategoryService extends BaseService {

    public void saveCategory(ProductCategory productCategory) throws Exception;

    public List<ProductCategory> listOfCategory();

    public List<ProductCategory> listOfCategoryByBrand(String brandId);

    public List<ProductCategory> listOfLeafCategory();

    public void deleteCategory(ProductCategory productCategory) throws Exception;

    public void updateUpperCategory(ProductCategory productCategory, ProductCategory upperCategory);

    public List<ProductCategory> categoryListByUpperCategory(String categoryId);

}
