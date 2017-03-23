/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.service;

import com.fsatir.types.Product;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ProductService {
    
    
    public List<Product> listOfProduct() throws Exception;
    
    public List<Product> listOfProductByCategory(String categoryId) throws Exception;
    
    public Product getProductDetail(String productId) throws Exception;
    
    public Product saveProduct(Product product) throws Exception;
    
    public void deleteProduct(List<Product> productList) throws Exception;

}
