/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.app.view.json;

import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 *
 * @author pj
 */
@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView {

    @Override
    protected Object filterModel(Map<String, Object> model) {
        model.remove("titulo");
        model.remove("page");
        
        return super.filterModel(model); //To change body of generated methods, choose Tools | Templates.
    }
    
}
