package com.ots.dpel.global.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ImageUtils {
    /**
     * Returns the bytes of an image, based on the given
     * <a href="http://en.wikipedia.org/wiki/Data_URI_scheme">Data URI</a>
     * @param dataUri The Data URI
     * @return The bytes of the image
     */
    public static byte[] getImageBytes(String dataUri) {
        byte[] imageBytes = null;
        
        try {
            if (dataUri == null || dataUri.isEmpty()) {
                throw new Exception("Empty dataUri");
            }
            String encodingPrefix = "base64,";
            int contentStartIndex = dataUri.indexOf(encodingPrefix) + encodingPrefix.length();
            imageBytes = Base64.decodeBase64(dataUri.substring(contentStartIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return imageBytes;
    }
    
    
    public static String getContentType(String dataUri) {
        String encodingPrefix = ";base64,";
        String preamble = "data:";
        
        return dataUri.substring(preamble.length(), dataUri.indexOf(encodingPrefix));
    }
    
    
    /**
     * Returns the scaled version of an image, in bytes
     * @param imageBytes   The bytes of the given image
     * @param format       The output format (e.g., jpeg, png)
     * @param scaledWidth  The width in which to scale the image
     * @param scaledHeight The height in which to scale the image
     * @return The bytes of the scaled image (i.e., thumbnail)
     */
    public static byte[] getScaledImageBytes(byte[] imageBytes, String format, int scaledWidth, int scaledHeight) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            
            Dimension scaledDimension = getScaledDimension(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()), new Dimension
                (scaledWidth, scaledHeight));
            
            Image resizedImage = bufferedImage.getScaledInstance((int) scaledDimension.getWidth(), (int) scaledDimension.getHeight(), Image
                .SCALE_SMOOTH);
            
            BufferedImage bufferedResized = new BufferedImage((int) scaledDimension.getWidth(), (int) scaledDimension.getHeight(), Image
                .SCALE_REPLICATE);
            bufferedResized.getGraphics().drawImage(resizedImage, 0, 0, null);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufferedResized, format, out);
            
            return out.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
        
        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;
        
        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }
        
        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }
        
        return new Dimension(new_width, new_height);
    }
    
    public static byte[] getResizedImageBytes(byte[] imageBytes, String format, int scaledWidth, int scaledHeight) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            Image resizedImage = bufferedImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            BufferedImage bufferedResized = new BufferedImage(scaledWidth, scaledHeight, Image.SCALE_REPLICATE);
            bufferedResized.getGraphics().drawImage(resizedImage, 0, 0, null);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufferedResized, format, out);
            
            return out.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Returns the dataUri of an image, encoded in Base64
     * @param bytes
     * @return dataUri of an image
     */
    public static String getDataUriFromImageBytes(byte[] bytes, String fileType) {
        String dataUri = "";
        StringBuilder sb = new StringBuilder();
        sb.append("data:image/" + fileType + ";base64,");
        sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(bytes, false)));
        dataUri = sb.toString();
        
        return dataUri;
    }
    
    public static String getImageDocument(String dataUri) {
        
        String imageDocument = "";
        
        try {
            if (dataUri == null || dataUri.isEmpty()) {
                throw new Exception("Empty dataUri");
            }
            String encodingPrefix = "base64,";
            int contentStartIndex = dataUri.indexOf(encodingPrefix) + encodingPrefix.length();
            imageDocument = new String(Base64.decodeBase64(dataUri.substring(contentStartIndex)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return imageDocument;
    }
}
