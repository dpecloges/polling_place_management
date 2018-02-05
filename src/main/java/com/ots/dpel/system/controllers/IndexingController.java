package com.ots.dpel.system.controllers;

import com.ots.dpel.system.dto.IndexingResponse;
import com.ots.dpel.system.services.IndexingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller Διαχείρισης των Solr Indexes
 */
@Controller
@RequestMapping("/system/indexing")
public class IndexingController {
    
    @Autowired
    private IndexingService indexingService;
    
    /**
     * Ανάκτηση της ένδειξης ενεργοποίησης της διασύνδεσης με το Solr
     * @return
     */
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(value = "enabled", method = RequestMethod.GET)
    public ResponseEntity<IndexingResponse> solrEnabled() {
        
        Boolean solrEnabled = indexingService.solrEnabled();
        
        if (solrEnabled == null) solrEnabled = false;
        
        return new ResponseEntity<IndexingResponse>(new IndexingResponse(solrEnabled), HttpStatus.OK);
    }
    
    /**
     * Ανάκτηση της ένδειξης ενεργοποιημένου Solr Core για τη ζητούμενη ενότητα εγγραφών
     * @param core Το αναγνωριστικό του core
     * @return
     */
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(value = "online/{core}", method = RequestMethod.GET)
    public ResponseEntity<IndexingResponse> isCoreOnline(@PathVariable("core") String core) {
        
        Boolean coreOnline = indexingService.isCoreOnline(core);
        
        if (coreOnline == null) coreOnline = false;
        
        return new ResponseEntity<IndexingResponse>(new IndexingResponse(coreOnline), HttpStatus.OK);
    }
    
    /**
     * Αλλαγή της κατάστασης της ένδειξης ενεργοποιημένης σύνδεσης με το Solr Core σε true
     * @param core Το αναγνωριστικό του core (all για όλα τα διαθέσιμα cores)
     * @return
     */
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(value = "setonline/{core}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> setCoreOnline(@PathVariable("core") String core) {
        
        if (core.equals("all")) {
            indexingService.setCoreOnlineStatus(IndexingService.ELECTORS_INDEX, Boolean.TRUE);
        } else {
            indexingService.setCoreOnlineStatus(core, Boolean.TRUE);
        }
        
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
    /**
     * Αλλαγή της κατάστασης της ένδειξης ενεργοποιημένης σύνδεσης με το Solr Core σε false
     * @param core Το αναγνωριστικό του core (all για όλα τα διαθέσιμα cores)
     * @return
     */
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(value = "setoffline/{core}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> setCoreOffline(@PathVariable("core") String core) {
        
        if (core.equals("all")) {
            indexingService.setCoreOnlineStatus(IndexingService.ELECTORS_INDEX, Boolean.FALSE);
        } else {
            indexingService.setCoreOnlineStatus(core, Boolean.FALSE);
        }
        
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
