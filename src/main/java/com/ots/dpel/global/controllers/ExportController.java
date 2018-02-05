package com.ots.dpel.global.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.global.dto.ExportDataDTO;
import com.ots.dpel.global.utils.JsonExcelUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller για την εξαγωγή δεδομένων
 */
@Controller
@RequestMapping("/global/export")
public class ExportController {
    
    private static final Logger logger = LogManager.getLogger(ExportController.class);
    
    @Autowired
    private ObjectMapper mapper;
    
    /**
     * Μέθοδος εξαγωγής δεδομένων από τις λίστες των ευρετηρίων JqGrid σε αρχείο Excel
     * Στο payload του request περιλαμβάνονται
     * ο τίτλος της εκτύπωσης (title)
     * η περιγραφή στηλών της εκτύπωσης (model)
     * και τα δεδομένα (data)
     * @param request
     * @param response
     */
    @PreAuthorize("hasAuthority('cm.general')")
    @RequestMapping(value = "excel", method = RequestMethod.POST)
    public void exportDataToExcel(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            ServletInputStream inputStream = request.getInputStream();
            String input = IOUtils.toString(inputStream, "UTF-8");
            
            ExportDataDTO exportDataDto = mapper.readValue(input, ExportDataDTO.class);
            
            ServletOutputStream outputStream = response.getOutputStream();
            
            JsonExcelUtils.writeToFile(exportDataDto.getTitle(), exportDataDto.getData(), exportDataDto.getModel(), outputStream);
        } catch (Exception e) {
            logger.error("Error occurred while exporting data to excel", e);
        }
    }
}
