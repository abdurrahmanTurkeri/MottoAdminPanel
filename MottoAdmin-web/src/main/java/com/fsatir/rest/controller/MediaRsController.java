/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.rest.controller;

import com.fsatir.service.MediaService;
import com.fsatir.types.Media;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;


/**
 *
 * @author abdurrahmanturkeri
 */
@Path("/media")
@RequestScoped
public class MediaRsController {

    @Inject
    MediaService mediaService;
    
    

    /**
     * Creates a new instance of FetvaRsController
     */
    public MediaRsController() {
    }

    @GET
    @Path("/media/list/category/{param}/{noDetail}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Media> getMediaList(@PathParam("param") String categoryId,
            @PathParam("noDetail") String noDetail
    ) {
        try {
            List<Media> result = null;

            if (noDetail != null && noDetail.equals("true")) {

            } else {
                result = mediaService.listOfMediaByCategory(categoryId);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @GET
    @Path("/media/detail/{param}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Media getMediaDetail(@PathParam("param") String mediaId) {
        try {
            Media media = mediaService.getMediaDetail(mediaId);
            return media;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @POST
    @Path("/fileupload")  //Your Path or URL to call this service
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @DefaultValue("true") @FormDataParam("enabled") boolean enabled,
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        //Your local disk path where you want to store the file
        String uploadedFileLocation = "/Users/abdurrahmanturkeri/temp/" + fileDetail.getFileName();
        System.out.println(uploadedFileLocation);
        // save it
        File objFile = new File(uploadedFileLocation);
        if (objFile.exists()) {
            objFile.delete();

        }

        saveToFile(uploadedInputStream, uploadedFileLocation);

        String output = "File uploaded via Jersey based RESTFul Webservice to: " + uploadedFileLocation;

        return Response.status(200).entity(output).build();

    }
    
    private void saveToFile(InputStream uploadedInputStream,
        String uploadedFileLocation) {

    try {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];

        out = new FileOutputStream(new File(uploadedFileLocation));
        while ((read = uploadedInputStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    } catch (IOException e) {

        e.printStackTrace();
    }

}

}
