/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.service;


import com.fsatir.types.Brand;
import com.fsatir.types.Company;
import com.fsatir.types.ProductCategory;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author abdurrahmanturkeri
 */
@Local
public interface BrandService {

    public void saveBrand(Brand brand) throws Exception;
    
    public List<Brand> listOfBrand();
    
    public List<Brand> listOfBrandByCompany(String companyId);
    
    public void deleteBrand(Brand brand) throws Exception ;
    
    
}
