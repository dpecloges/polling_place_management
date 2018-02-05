package com.ots.dpel.global.services;

import com.ots.dpel.global.dto.ExportDataDTO;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.util.List;

/**
 * Interface λειτουργιών που αφορούν διαδικασίες εξαγωγής
 * δεδομένων σε αρχεία
 */
public interface ExportService {

    /**
     * Αρχικοποίηση αντικειμένου παραμέτρων εξαγωγής αρχείων βάση του request
     * @param request Το request που περιλαμβάνει τις σχετικές παραμέτρους
     * @return
     */
    public ExportDataDTO initializeExportParams(HttpServletRequest request);

    /**
     * Δημιουργία αντικειμένου της σελίδας εξαγωγής
     * @param exportParams Παράμετροι
     * @return
     */
    public Pageable getExportPageable(ExportDataDTO exportParams);

    /**
     * Εξαγωγή δεδομένων ευρετηρίων σε αρχεία Excel
     * Δημιουργία του Excel αρχείου σε binary format στο output stream του request
     * @param title Τίτλος αρχείου
     * @param data  Δεδομένα των γραμμών (λίστα με αντικείμενα dto)
     * @param model Περιγραφή στηλών αρχείου
     * @param fos   Το output stream του request
     */
    public void listToExcelFile(String title, List data, String model, OutputStream fos);
}
