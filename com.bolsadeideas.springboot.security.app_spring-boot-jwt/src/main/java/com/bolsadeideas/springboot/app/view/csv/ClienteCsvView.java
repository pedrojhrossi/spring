/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.app.view.csv;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import java.io.FileWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author pj
 */
//@Component("listar.csv") //TODO: Se utiliza para manejarlo y detectarlo por MIME Types en el application properties
@Component("listar")
public class ClienteCsvView extends AbstractView {

    public ClienteCsvView() {
        setContentType("text/csv");
    }
    
    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    
    
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
        response.setContentType(getContentType());
        Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
        
        ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.TAB_PREFERENCE);
        
        String[] header = {"id", "nombre", "apellido", "email", "createAt"};
        
        beanWriter.writeHeader(header);
        
        for(Cliente cliente : clientes) {
            beanWriter.write(cliente, header);
        }
        
        beanWriter.close();
    }

}
