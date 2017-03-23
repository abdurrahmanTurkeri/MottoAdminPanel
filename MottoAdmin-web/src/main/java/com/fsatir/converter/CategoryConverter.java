/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.converter;



import com.fsatir.types.ProductCategory;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author abdurrahmanturkeri
 */
@FacesConverter("categoryConverter")
public class CategoryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ProductCategory productCategory=null;    
        if (value != null && value.trim().length() > 0) {
            List<ProductCategory> photoCategoryList = (List<ProductCategory>) context.getCurrentInstance().getExternalContext().getSessionMap().get("categoryList");

            for (ProductCategory f : photoCategoryList) {
                if (f.getId().equals(value)) {
                  productCategory=f;  
                }
            }
            return productCategory;
        } else {
            return null;
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return String.valueOf(((ProductCategory) value).getId());
        } else {
            return null;
        }

    }

}
