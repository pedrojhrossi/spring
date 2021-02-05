/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.app.models.service;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author pj
 */
public interface IUploadFileService {

    public Resource load(String filename)  throws MalformedURLException;

    public String copy(MultipartFile file)  throws IOException;

    public boolean delete(String filename);
    
    public void deleteAll();
    
    public void init() throws IOException;
}
