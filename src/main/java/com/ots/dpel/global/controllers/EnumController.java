package com.ots.dpel.global.controllers;

import com.google.gson.Gson;
import com.ots.dpel.global.dto.EnumDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sdimitriadis
 */
@RestController
@RequestMapping("/global/enums")
public class EnumController {
    
    /**
     * Ανάκτηση λίστας με όλες τις τιμές του enum της δοθείσας κλάσης
     * Το όνομα της κλάσης είναι αυτό που ακολουθεί το "com.ots.dpel."
     * @param enumClass
     * @return
     */
    @PreAuthorize("hasAuthority('cm.general')")
    @RequestMapping(value = "getvalues/{enumClass:.+}", method = RequestMethod.GET)
    public List<EnumDTO> getEnumValues(@PathVariable("enumClass") String enumClass) {
        
        List<EnumDTO> list = new ArrayList<EnumDTO>();
        
        try {
            Class<?> clazz = Class.forName("com.ots.dpel." + enumClass);
            
            for (Object obj : clazz.getEnumConstants()) {
                Enum<?> enumeration = (Enum<?>) obj;
                
                String value = enumeration.name();
                String label = enumeration.toString();
                Integer ordinal = enumeration.ordinal();
                
                EnumDTO enumDto = new EnumDTO();
                enumDto.setValue(value);
                enumDto.setLabel(label);
                enumDto.setOrdinal(ordinal);
                
                list.add(enumDto);
            }
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    /**
     * Ανάκτηση λίστας με όλες τις τιμές των enums πολλαπλών κλάσεων.
     * Το όνομα της κάθε κλάσης είναι αυτό που ακολουθεί το "com.ots.dpel."
     * @param enumClasses
     * @return {List} Λίστα με λίστες για κάθε enumeration.
     */
    @PreAuthorize("hasAuthority('cm.general')")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public String getMultipleEnumValues(@RequestParam("enums") List<String> enumClasses) {
        
        Gson gson = new Gson();
        
        List<List<EnumDTO>> enumsList = new ArrayList<>();
        
        for (String enumClass : enumClasses) {
            List<EnumDTO> enumList = new ArrayList<>();
            
            try {
                Class<?> clazz = Class.forName("com.ots.dpel." + enumClass);
                
                for (Object obj : clazz.getEnumConstants()) {
                    Enum<?> enumeration = (Enum<?>) obj;
                    
                    EnumDTO enumDto = new EnumDTO();
                    enumDto.setValue(enumeration.name());
                    enumDto.setLabel(enumeration.toString());
                    enumDto.setOrdinal(enumeration.ordinal());
                    
                    enumList.add(enumDto);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            
            enumsList.add(enumList);
        }
        
        return gson.toJson(enumsList);
    }
}
