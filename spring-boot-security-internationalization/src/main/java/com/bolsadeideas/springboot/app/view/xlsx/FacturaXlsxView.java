/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.app.view.xlsx;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

/**
 *
 * @author pj
 */
@Component("/factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Factura factura = (Factura) model.get("factura");
        
        Sheet sheet = workbook.createSheet();
        
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        
        cell.setCellValue("Datos del Cliente");
        
        row = sheet.createRow(1);
        cell = row.createCell(0);
        
        cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
        
        row = sheet.createRow(2);
        cell = row.createCell(0);
        
        cell.setCellValue(factura.getCliente().getEmail());
        
//        De forma encadenada se puede crear la celda
        sheet.createRow(3).createCell(0).setCellValue("Datos de la factura");
        sheet.createRow(4).createCell(0).setCellValue("Folio: " + factura.getId());
        sheet.createRow(5).createCell(0).setCellValue("Descripción: " + factura.getDescripcion());
        sheet.createRow(6).createCell(0).setCellValue("Fecha: " + factura.getCreateAt());
    }

}
