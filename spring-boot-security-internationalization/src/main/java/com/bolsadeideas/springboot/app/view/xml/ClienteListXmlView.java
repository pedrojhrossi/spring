/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.app.view.xml;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

/**
 *
 * @author pj
 */
@Component("listar.xml")
public class ClienteListXmlView extends MarshallingView {

    @Autowired
    public ClienteListXmlView(Jaxb2Marshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        model.remove("titulo");
        model.remove("page");
        
        Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
        
        model.remove("clientes");
        
        model.put("clientesList", new ClienteList(clientes.getContent()));
        
        super.renderMergedOutputModel(model, request, response); //To change body of generated methods, choose Tools | Templates.
    }

}
