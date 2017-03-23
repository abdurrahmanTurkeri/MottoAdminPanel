/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.converter;



import com.fsatir.types.Location;
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
@FacesConverter("locationConverter")
public class LocationConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Location location=null;    
        if (value != null && value.trim().length() > 0) {
            List<Location> locationList = (List<Location>) context.getCurrentInstance().getExternalContext().getSessionMap().get("locationList");
            for (Location s : locationList) {
                if (s.getId().equals(value)) {
                  location=s;  
                }
            }
            return location;
        } else {
            return null;
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return String.valueOf(((Location) value).getId());
        } else {
            return null;
        }

    }

}
