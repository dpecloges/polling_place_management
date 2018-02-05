package com.ots.dpel.global.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * Trivial implementation of the {@link MultipartFile} interface to wrap a byte[] decoded
 * from a BASE64 encoded String
 * </p>
 */
public class BASE64DecodedMultipartFile implements MultipartFile {
    
    private String name;
    
    private String originalFileName;
    
    private String contentType;
    
    private final byte[] imgContent;

    public BASE64DecodedMultipartFile(byte[] imgContent) {
        this.imgContent = imgContent;
    }
    
    public BASE64DecodedMultipartFile(String name, String originalFileName, byte[] imgContent) {
        this.imgContent = imgContent;
        this.name = name;
        this.originalFileName = originalFileName;
    }
    
    public BASE64DecodedMultipartFile(String name, String originalFileName, String contentType, byte[] imgContent) {
        this.imgContent = imgContent;
        this.name = name;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getOriginalFilename() {
        return this.originalFileName;
    }
    
    @Override
    public String getContentType() {
        return this.contentType;
    }
    
    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }
    
    @Override
    public long getSize() {
        return imgContent.length;
    }
    
    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }
    
    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }
}