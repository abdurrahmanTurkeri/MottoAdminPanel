package com.fsatir.rest.conf;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

@ApplicationPath("/rest")
public class ApplicationConfig extends Application{

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources=new HashSet<Class<?>>();
        resources.add(com.fsatir.rest.controller.PhotoCategoryRsController.class);     
        resources.add(com.fsatir.rest.controller.MediaRsController.class);          
        resources.add(MultiPartFeature.class); 
        return resources;
    }

    
}
