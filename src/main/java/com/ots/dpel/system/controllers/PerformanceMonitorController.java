package com.ots.dpel.system.controllers;

import com.jamonapi.MonitorComposite;
import com.jamonapi.MonitorFactory;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Controller Στατιστικών Στοιχείων Χρήσης Εφαρμογής
 */
@Controller
@RequestMapping("/system/performance")
public class PerformanceMonitorController {
    
    
    /**
     * JSON
     * Ανάκτηση των στατιστικών στοιχείων απόδοσης της εφαρμογής σε μορφή Raw Json Data
     */
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(method = RequestMethod.GET, value = "stats/data")
    public @ResponseBody
    Object[][] getStatisticsData(Principal principal) {
        
        MonitorComposite monitor = MonitorFactory.getRootMonitor();
        
        return monitor.getData();
    }
    
    /**
     * JSON
     * Ανάκτηση των στατιστικών στοιχείων απόδοσης της εφαρμογής σε μορφή Report
     */
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(method = RequestMethod.GET, value = "stats/report")
    public @ResponseBody
    JSONObject getStatisticsReport(Principal principal) {
        
        MonitorComposite monitor = MonitorFactory.getRootMonitor();
        
        String statsReport = monitor.getReport();
        
        JSONObject jsonObject = new JSONObject();
        
        if (statsReport != null) {
            jsonObject.put("content", statsReport);
        }
        
        return jsonObject;
    }
    
    /**
     * JSON
     * Αρχικοποίηση των στατιστικών στοιχείων απόδοσης της εφαρμογής Reset
     */
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(method = RequestMethod.GET, value = "stats/reset")
    public ResponseEntity<Boolean> resetStatistics(Principal principal) {
        
        MonitorComposite monitor = MonitorFactory.getRootMonitor();
        
        monitor.reset();
        
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    
}
