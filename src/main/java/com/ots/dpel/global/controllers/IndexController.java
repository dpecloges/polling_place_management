package com.ots.dpel.global.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author sdimitriadis
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {
    
    @PreAuthorize("hasAuthority('cm.general')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }
    
}