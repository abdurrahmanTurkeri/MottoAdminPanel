/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.converter;



import com.fsatir.types.Store;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author abdurrahmanturkeri
 */
@FacesConverter("storeConverter")
public class StoreConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Store store=null;    
        if (value != null && value.trim().length() > 0) {
            List<Store> storeList = (List<Store>) context.getCurrentInstance().getExternalContext().getSessionMap().get("storeList");
            for (Store s : storeList) {
                if (s.getId().equals(value)) {
                  store=s;  
                }
            }
            return store;
        } else {
            return null;
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return String.valueOf(((Store) value).getId());
        } else {
            return null;
        }

    }

}
