/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.controller;

import com.fsatir.service.BrandService;
import com.fsatir.service.MediaService;
import com.fsatir.service.ProductCategoryService;
import com.fsatir.service.ProductService;
import com.fsatir.types.Brand;
import com.fsatir.types.Product;
import com.fsatir.types.ProductCategory;
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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author abdurrahmanturkeri
 */
@Named(value = "productManagedBean")
@javax.faces.view.ViewScoped
public class ProductManagedBean implements Serializable {

    @EJB
    MediaService mediaService;
    @EJB
    BrandService brandService;

    @Inject
    ProductCategoryService categoryService;

    @Inject
    ProductService productService;

    private UploadedFile uploadedFile;
    
    private List<ProductCategory> categoryList;
    private List<Brand> brandList;
    
    private List<ProductCategory> hierarcycalCategories;

    private List<ProductCategory>  selectedCategoryList;
    private Brand selectedBrand;
    private Product product = new Product();
    private HashMap<String, UploadedFile> uploadedFiles = new HashMap<>();

    /**
     * Creates a new instance of QuestionManagedBean
     */
    public ProductManagedBean() {

    }

    @PostConstruct
    public void init() {
        try {
            categoryList = categoryService.listOfLeafCategory();
            if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("categoryList") == null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoryList",categoryList);
            }
            brandList=brandService.listOfBrand();
            sortCategories();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sortCategories(){
        for(ProductCategory category:categoryList){
            if(category.getUpperCategory()!=null){
                hierarcycalCategories.add(category);
            }
        }        
    }
    
    

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
                String filePath = "/products/" + product.getId();

                product.setImageUrl("/media/detail" + filePath + "/" + strFile);
                product.setAssetUrl("/media/detail" + filePath + "/" + strFile);
                product.setAssetName(baseName);

                
                
                saveFile(filePath, imageFullName, file.getInputstream());
                saveFile(filePath, assetFullName, file.getInputstream());

                product.setProductCategoryList(selectedCategoryList);               
                productService.saveProduct(product);

                FacesMessage message = new FacesMessage("Başarılı", product.getName() + " Ürün  Kaydedildi");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } catch (Exception ex) {
                Logger.getLogger(ProductManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            product = new Product();
            selectedCategoryList = new ArrayList<>();
        }

    }

//    
    public List<ProductCategory> completeCategory(String query) {

        List<ProductCategory> filteredCategorys = new ArrayList<>();

        for (ProductCategory photoCategory : hierarcycalCategories) {
            if (photoCategory.getName().toLowerCase().startsWith(query.toLowerCase())) {
                filteredCategorys.add(photoCategory);
            }
        }

        return filteredCategorys;
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

    public List<ProductCategory> getSelectedCategoryList() {
        return selectedCategoryList;
    }

    public void setSelectedCategoryList(List<ProductCategory> selectedCategoryList) {
        this.selectedCategoryList = selectedCategoryList;
    }

   
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ProductCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ProductCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ProductCategory> getHierarcycalCategories() {
        return hierarcycalCategories;
    }

    public void setHierarcycalCategories(List<ProductCategory> hierarcycalCategories) {
        this.hierarcycalCategories = hierarcycalCategories;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public Brand getSelectedBrand() {
        return selectedBrand;
    }

    public void setSelectedBrand(Brand selectedBrand) {
        this.selectedBrand = selectedBrand;
    }
}
