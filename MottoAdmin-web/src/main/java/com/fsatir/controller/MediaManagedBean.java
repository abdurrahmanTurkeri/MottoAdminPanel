/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.controller;

import com.fsatir.service.MediaService;
import com.fsatir.service.ProductCategoryService;
import com.fsatir.service.ProductService;
import com.fsatir.types.Media;
import com.fsatir.types.Product;
import com.fsatir.types.ProductCategory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author abdurrahmanturkeri
 */
@Named(value = "mediaManagedBean")
@javax.faces.view.ViewScoped
public class MediaManagedBean implements Serializable {

    @EJB
    MediaService mediaService;

    @Inject
    ProductCategoryService categoryService;

    @Inject
    ProductService productService;

    private UploadedFile uploadedFile;

    private List<ProductCategory> leafCategoryList;

    private ProductCategory selectedCategory;

    private Product product = new Product();
    private HashMap<String, UploadedFile> uploadedFiles = new HashMap<>();

    /**
     * Creates a new instance of QuestionManagedBean
     */
    public MediaManagedBean() {

    }

    @PostConstruct
    public void init() {
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("categoryList") == null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoryList", categoryService.listOfCategory());
            }

            leafCategoryList = categoryService.listOfLeafCategory();
            System.out.println("Dosya yükleme sayfası.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void saveMedia() {
//        try {            
//            //media.setCategoryList(selectedCategoryList);
//            mediaService.saveMedia(media);
//            mediaList = mediaService.listOfMedia();
//        } catch (Exception ex) {
//            Logger.getLogger(MediaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//    
//    public void deleteSelectedMedia() {
//        try {
//            mediaService.deleteMedia(selectedMediaList);
//            mediaList = mediaService.listOfMedia();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Silme başarılı!"));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
//        }
//    }
//
//    /*
//        Görsellerin DB'den çekilip, <p:graphicImage 'lerde gösterilmesi
//    */
//    public StreamedContent getImage() throws Exception {
//        FacesContext context = FacesContext.getCurrentInstance();
//
//        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
//            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
//            return new DefaultStreamedContent();
//        }        
//        else
//        {
//            HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
//            String imgID = req.getParameter("imageID");           
//            Media m = mediaService.getMediaDetail(imgID);           
//            if(m.getMediaData() == null)
//            {
//                return null;
//            }
//            else
//                return new DefaultStreamedContent(new ByteArrayInputStream(m.getMediaData()), "image/jpeg");   
//        }
//    }
//
//    
    /*
        FileUpload olayı
     */
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        String strFile = new String(file.getFileName().getBytes(Charset.defaultCharset()), "UTF-8");
        String baseName = FilenameUtils.getBaseName(strFile);
        String extension = FilenameUtils.getExtension(strFile);

        if (extension.toLowerCase().equals("jpeg") || extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("png")) {
            //İmage
            uploadedFiles.put("IMAGE", file);
        } else {
            //Asset
            uploadedFiles.put("ASSET", file);
        }

        if (uploadedFiles.size() == 2) {
            try {

                UploadedFile imagefile = uploadedFiles.get("IMAGE");
                UploadedFile assetFile = uploadedFiles.get("ASSET");

                String imageFullName = new String(imagefile.getFileName().getBytes(Charset.defaultCharset()), "UTF-8");
                String assetFullName = new String(assetFile.getFileName().getBytes(Charset.defaultCharset()), "UTF-8");
                String assetBaseName = FilenameUtils.getBaseName(assetFullName);
                String filePath = "/products/" + selectedCategory.getId() + "/" + product.getId();

                product.setImageUrl("/media/detail" + filePath + "/" + strFile);
                product.setAssetUrl("/media/detail" + filePath + "/" + strFile);
                product.setAssetName(baseName);

                
                
                saveFile(filePath, imageFullName, file.getInputstream());
                saveFile(filePath, assetFullName, file.getInputstream());

                product.setProductCategory(selectedCategory);
                selectedCategory.getProducts().add(product);
                productService.saveProduct(product);
                categoryService.saveCategory(selectedCategory);

                FacesMessage message = new FacesMessage("Başarılı", product.getName() + " Ürün  Kaydedildi");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } catch (Exception ex) {
                Logger.getLogger(MediaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            product = new Product();
            selectedCategory = null;
        }

    }

//    
    public List<ProductCategory> completeCategory(String query) {

        List<ProductCategory> filteredCategorys = new ArrayList<>();

        for (ProductCategory photoCategory : leafCategoryList) {
            if (photoCategory.getName().toLowerCase().startsWith(query.toLowerCase())) {
                filteredCategorys.add(photoCategory);
            }
        }

        return filteredCategorys;
    }

    /*
        Kategori seçiminde tetiklenen olayı
     */
    public void onItemSelect(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kategori Seçildi.", ((ProductCategory) event.getObject()).getName()));
    }

    public void onSelectedItemsDelete(SelectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı!", "Silme işlemi tamamlandı.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void testet() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı!", "Silme işlemi tamamlandı.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<ProductCategory> getLeafCategoryList() {
        return leafCategoryList;
    }

    public void setCategoryList(List<ProductCategory> leafCategoryList) {
        this.leafCategoryList = leafCategoryList;
    }

    public ProductCategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(ProductCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void saveFile(String filePath, String filename, InputStream inputStream) throws IOException {
        File file = new File(filePath, filename);
        file.getParentFile().mkdirs();
        OutputStream output = new FileOutputStream(file);

        try {
            IOUtils.copy(inputStream, output);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(output);
        }
    }

    public String uniqeFolderName(String directory) {
        String uuid = UUID.randomUUID().toString();
        Path path = Paths.get(directory + "/" + uuid);
        if (Files.exists(path)) {
            return uniqeFolderName(directory);
        }
        return uuid;
    }
}
