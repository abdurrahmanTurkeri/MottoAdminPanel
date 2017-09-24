/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.converter;



import com.fsatir.types.Brand;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author abdurrahmanturkeri
 */
@FacesConverter("brandConverter")
public class BrandConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Brand brandSelected=null;    
        if (value != null && value.trim().length() > 0) {
            List<Brand> brandList = (List<Brand>) context.getCurrentInstance().getExternalContext().getSessionMap().get("brandList");

            for (Brand brand : brandList) {
                if (brand.getName().equals(value)) {
                  brandSelected=brand;  
                }
            }
            return brandSelected;
        } else {
            return null;
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return String.valueOf(((Brand) value).getName());
        } else {
            return null;
        }

    }

}
