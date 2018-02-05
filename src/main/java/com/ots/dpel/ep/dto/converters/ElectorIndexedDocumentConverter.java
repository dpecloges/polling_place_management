package com.ots.dpel.ep.dto.converters;

import com.ots.dpel.ep.dto.ElectorDto;
import com.ots.dpel.ep.dto.indexing.ElectorIndexedDocument;
import com.ots.dpel.global.utils.DpDateUtils;
import org.springframework.core.convert.converter.Converter;

public class ElectorIndexedDocumentConverter implements Converter<ElectorIndexedDocument, ElectorDto> {
    
    @Override
    public ElectorDto convert(ElectorIndexedDocument electorIndexedDocument) {
        
        ElectorDto electorDto = new ElectorDto();
        
        electorDto.setId(Long.parseLong(electorIndexedDocument.getId()));
        electorDto.setEklSpecialNo(electorIndexedDocument.getEklSpecialNo());
        
        electorDto.setLastName(electorIndexedDocument.getLastName());
        electorDto.setFirstName(electorIndexedDocument.getFirstName());
        electorDto.setFatherFirstName(electorIndexedDocument.getFatherFirstName());
        electorDto.setMotherFirstName(electorIndexedDocument.getMotherFirstName());
        
        electorDto.setBirthDate(DpDateUtils.getDate(electorIndexedDocument.getBirthYear(), electorIndexedDocument.getBirthMonth() - 1,
                electorIndexedDocument.getBirthDay(), 0, 0, 0));
        
        electorDto.setMunicipalRecordNo(electorIndexedDocument.getMunicipalRecordNo());
        electorDto.setMunicipalUnitDescription(electorIndexedDocument.getMunicipalUnitDescription());
        
        return electorDto;
    }
}