/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.service;


import com.fsatir.types.Company;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author abdurrahmanturkeri
 */
@Local
public interface CompanyService {

    public void saveCompany(Company company) throws Exception;
    
    public List<Company> listOfCompany();
    
    public void deleteCompany(Company company) throws Exception ;
    
}
