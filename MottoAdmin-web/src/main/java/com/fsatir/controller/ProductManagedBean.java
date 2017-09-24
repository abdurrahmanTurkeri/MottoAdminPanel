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
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
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

    private static final long serialVersionUID = 1L;

    @EJB
    MediaService mediaService;
    @EJB
    BrandService brandService;

    @Inject
    ProductCategoryService categoryService;

    @Inject
    ProductService productService;
    private boolean  uploadActive=true;

    private UploadedFile uploadedFile;

    private List<Product> productList;
    private List<ProductCategory> categoryList;

    private List<Brand> brandList;

    private List<ProductCategory> selectedCategoryList;
    private List<String> selectedBrand;
    
    private List<Product> datatableProductSelection;
    private Product product = new Product();

    private Product selectedProduct;
    private HashMap<String, UploadedFile> uploadedFiles = new HashMap<>();

    /**
     * Creates a new instance of QuestionManagedBean
     */
    public ProductManagedBean() {

    }

    @PostConstruct
    public void init() {
        try {
            productList = productService.listOfProduct();
            categoryList = categoryService.listOfLeafCategory();
            if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("categoryList") == null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoryList", categoryList);
            }
            brandList = brandService.listOfBrand();
            if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("brandList") == null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("brandList", brandList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        if (!PhaseId.INVOKE_APPLICATION.equals(event.getPhaseId())) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
        } else {
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
                    uploadActive=false;

                } catch (Exception ex) {
                    Logger.getLogger(ProductManagedBean.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı!", "1 adet Thumbnail 1 adet Unity File gerekmektedir.");
            }
        }

    }

    public void saveFileDB() {
        try {
            Brand brand=new Brand();
            brand.setId(selectedBrand.get(0));
            product.setBrand(brand);
            product.setProductCategoryList(selectedCategoryList);
            productService.saveProduct(product);
            FacesMessage message = new FacesMessage("Başarılı", product.getName() + " Ürün  Kaydedildi");
            FacesContext.getCurrentInstance().addMessage(null, message);
            product = new Product();
            selectedCategoryList = new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesMessage message = new FacesMessage("Hata!!! ", ex.getMessage() + " Hata Mesajı");
        }
    }
    
    public boolean filterByCategory(Object value, Object filter, Locale locale){
        String selectedCategory=(String)filter;
        ProductCategory productCategory=null;
        if(selectedCategory == null||selectedCategory.length()<1 || selectedCategory.equals("-1")) {
            return true;
        }
        if(value == null || value.equals(new Long(0)) || value.equals("-")) {
            if(selectedCategory.equals("0")){
                return true;
            }            
            return false;
         }  
       
        for(ProductCategory  category:  categoryList){
            
            if(category.getName().contains(selectedCategory)){
                productCategory=category;
            }
        }
        
        if(productCategory==null){
            return false;
        }else{
            return true;
        }
        
        
      
    }
    
   

    public List<ProductCategory> completeCategory(String query) {

        List<ProductCategory> filteredCategorys = new ArrayList<>();

        for (ProductCategory photoCategory : categoryList) {
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

    public List<Brand> getBrandList() {
        return brandList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

   


    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<ProductCategory> getSelectedCategoryList() {
        return selectedCategoryList;
    }

    public void setSelectedCategoryList(List<ProductCategory> selectedCategoryList) {
        this.selectedCategoryList = selectedCategoryList;
    }

    public List<String> getSelectedBrand() {
        return selectedBrand;
    }

    public void setSelectedBrand(List<String> selectedBrand) {
        this.selectedBrand = selectedBrand;
    }

    public boolean isUploadActive() {
        return uploadActive;
    }

    public void setUploadActive(boolean uploadActive) {
        this.uploadActive = uploadActive;
    }

    public List<Product> getDatatableProductSelection() {
        return datatableProductSelection;
    }

    public void setDatatableProductSelection(List<Product> datatableProductSelection) {
        this.datatableProductSelection = datatableProductSelection;
    }
    
    

}
