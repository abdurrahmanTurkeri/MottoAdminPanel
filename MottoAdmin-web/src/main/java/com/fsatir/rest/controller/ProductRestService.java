/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.rest.controller;

import com.fsatir.service.MediaService;
import com.fsatir.service.ProductService;
import com.fsatir.types.Media;
import com.fsatir.types.Product;
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
@Path("/product")
@RequestScoped
public class ProductRestService {

    @Inject
    ProductService productService;

    /**
     * Creates a new instance of FetvaRsController
     */
    public ProductRestService() {
    }

    @GET
    @Path("/category/{categoryParam}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Product> getMediaList(@PathParam("categoryParam") String categoryId) {
        try {
            List<Product> result = productService.listOfProductByCategory(categoryId);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @GET
    @Path("/detail/{productCatId}/{productId}/{mediaName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getMediaDetail(@PathParam("productCatId") String productCatId,
            @PathParam("productId") String productId,
            @PathParam("mediaName") String mediaName) {
        try {
            File file = new File(productCatId + "/" + productId + "/" + mediaName);
            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"") //optional
                    .build();
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
