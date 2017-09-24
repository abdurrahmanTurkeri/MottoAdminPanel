/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.controller;

import com.fsatir.service.ProductCategoryService;
import com.fsatir.types.ProductCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author abdurrahmanturkeri
 */
@Named(value = "categoryManagedBean")
@javax.faces.view.ViewScoped
public class ProductCategoryBean implements Serializable {

    @Inject
    ProductCategoryService service;

    private ProductCategory productCategory = new ProductCategory();
    private ProductCategory selectedCategory;

    private TreeNode root;
    private List<ProductCategory> categoryList;

    /**
     * Key: ProductCategoryId VAlue: TreeNode
     */
    private HashMap<String, TreeNode> nodesMap = new HashMap<>();

    /**
     * Creates a new instance of FetvaCayegoryManagedBean
     */
    public ProductCategoryBean() {
    }

    @PostConstruct
    public void init() {
        categoryList = service.listOfCategory();
        root = getTreeFromCategoryList();
        System.out.println("Kategori Sayfası Açıldı");

    }

    public void saveCategory() {
        try {
            ProductCategory upperCategory = productCategory.getUpperCategory();
            if (upperCategory != null && (upperCategory.getProducts() != null && upperCategory.getProducts().size() > 0)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kategori Eklenemedi.", "'" + upperCategory.getName() + "' kategorisinin medya dosyaları bulunmakta. Bu kategoriye alt kategori eklenemez."));
                return;
            }

            service.saveCategory(productCategory);
            productCategory = new ProductCategory();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
        categoryList = service.listOfCategory();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoryList", categoryList);
        root = getTreeFromCategoryList();
    }

    public void deleteCategory() {

        try {
            service.deleteCategory(selectedCategory);
            categoryList = service.listOfCategory();
            root = getTreeFromCategoryList();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoryList", categoryList);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));

        }

    }

    public void updateCategory() {

    }

    public void showFetvaList() {

    }

    public ProductCategoryService getService() {
        return service;
    }

    public void setService(ProductCategoryService service) {
        this.service = service;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductCategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(ProductCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<ProductCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ProductCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public List<ProductCategory> completeCategory(String query) {
        List<ProductCategory> sessionlist = (List<ProductCategory>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("categoryList");
        sessionlist = categoryList;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoryList", sessionlist);
        List<ProductCategory> filteredCategory = new ArrayList<>();

        for (ProductCategory productCategory : categoryList) {
            if (productCategory.getName().toLowerCase().startsWith(query.toLowerCase())) {
                filteredCategory.add(productCategory);
            }
        }
        return filteredCategory;
    }

    public TreeNode getTreeFromCategoryList() {
        System.out.println("Ağaç oluşturuluyor");
        nodesMap = new HashMap<>();
        TreeNode rootnode = new DefaultTreeNode("Root", null);
        return createTree(null, rootnode);

    }
    

    /**
     *
     * @param productCategory Node' a eklenecek object;
     * @param rootNode Node
     * @return TreeNode döner recursive şekilde çalışır.
     */
    public TreeNode createTree(ProductCategory productCategory, TreeNode rootNode) {
        TreeNode newNode;

        List<ProductCategory> childNodes;
        if (productCategory == null) {
            childNodes = categoryList.stream()
                    .filter(x -> x.getUpperCategory() == null)
                    .collect(Collectors.toList());
            newNode = rootNode;
        } else {
            childNodes = categoryList.stream()
                    .filter(x -> x.getUpperCategory() != null && x.getUpperCategory().equals(productCategory))
                    .collect(Collectors.toList());
            newNode = new DefaultTreeNode(productCategory, rootNode);
            nodesMap.put(productCategory.getId(), newNode);
        }

        for (ProductCategory pc : childNodes) {
            TreeNode child = createTree(pc, newNode);
        }

        try {
            ProductCategory prdCat = (ProductCategory) newNode.getData();
            int val = checkIsLeafAndRequireUpdate(newNode);
            if (val != 0) {
                service.saveCategory(prdCat);
            }
        } catch (ClassCastException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newNode;
    }

    public void treeDragDropEvent(TreeDragDropEvent event) {
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        //int dropIndex = event.getDropIndex();
        ProductCategory productCategory = (ProductCategory) dragNode.getData();
        ProductCategory upperCategory;
        try {
            //RootObject productcategory'e cast edilemez ve exception oluşur.Catche düşer
            upperCategory = (ProductCategory) dropNode.getData();

        } catch (Exception e) {
            upperCategory = null;
        }

        if (upperCategory != null && (upperCategory.getProducts() != null && upperCategory.getProducts().size() > 0)) {
            root = getTreeFromCategoryList();
            System.out.println("Kategori taşınamadı.");
            RequestContext.getCurrentInstance().update("insertForm:tree1");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kategori Taşınamadı.", "'" + upperCategory.getName() + "' kategorisinin medya dosyaları bulunmakta. Bu kategoriye alt kategori eklenemez."));
            return;
        }

        try {
            ProductCategory oldParentCategory = productCategory.getUpperCategory();
            if (oldParentCategory != null) {
                TreeNode oldParentNode = nodesMap.get(productCategory.getUpperCategory().getId());
                int val = checkIsLeafAndRequireUpdate(oldParentNode);
                if (val != 0) {
                    oldParentCategory = (ProductCategory) oldParentNode.getData();
                    service.saveCategory(oldParentCategory);
                }
            }
        } catch (ClassCastException exception) {

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        checkIsLeafAndRequireUpdate(dragNode);
        checkIsLeafAndRequireUpdate(dropNode);
        productCategory.setUpperCategory(upperCategory);

        try {
            //service.updateUpperCategory(productCategory,upperCategory);
            if (upperCategory != null) {
                service.saveCategory(upperCategory);
            }
            service.saveCategory(productCategory);
            categoryList = service.listOfCategory();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoryList", categoryList);
        } catch (Exception ex) {
            Logger.getLogger(ProductCategoryBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return 0 değişikliğe gerek yok.
     * @return 1 yaprak ve değişikliğe gerek var
     * @return 2 yaprak değil ve değişikliğe gerek var
     */
    public int checkIsLeafAndRequireUpdate(TreeNode node) {
        try {
            ProductCategory prdCat = (ProductCategory) node.getData();
            if (node.getChildCount() > 0 && prdCat.getIsLeaf() != 0) {
                prdCat.setIsLeaf(0);
                return 2;

            } else if (node.getChildCount() <= 0 && prdCat.getIsLeaf() != 1) {
                prdCat.setIsLeaf(1);
                return 1;
            }
            return 0;
        } catch (ClassCastException exception) {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
