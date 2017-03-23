/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsatir.service;

import com.fsatir.types.Media;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MediaService {
    
    
    public List<Media> listOfMedia() throws Exception;
    
    public List<Media> listOfMediaByCategory(String categoryId) throws Exception;
    
    public Media getMediaDetail(String mediaId) throws Exception;
    
    public Media saveMedia(Media media) throws Exception;
    
    public void deleteMedia(List<Media> media) throws Exception;

}
