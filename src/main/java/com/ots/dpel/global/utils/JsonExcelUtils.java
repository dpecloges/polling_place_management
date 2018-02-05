package com.ots.dpel.global.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Βοηθητικές λειτουργίες που αφορούν τη διαδικασία εξαγωγής δεδομένων σε Excel
 * Περιλαμβάνονται στατικές μέθοδοι
 * Απαιτείται η ύπαρξη των δεδομένων σε serialized JSON μορφή
 * Η κλάση γίνεται deprecated χάριν της αντίστοιχης λειτουργίας στο
 * {@link com.ots.dpel.global.services.impl.ExportServiceImpl}
 * στην οποία τα δεδομένα εισάγονται ως List από Dto Objects
 */
@Deprecated
public class JsonExcelUtils {
    
    static Map<String, Integer> cellTypes = new HashMap<String, Integer>() {{
        put("string", XSSFCell.CELL_TYPE_STRING);
        put("number", XSSFCell.CELL_TYPE_NUMERIC);
        put("blank", XSSFCell.CELL_TYPE_BLANK);
    }};
    
    /**
     * Μέθοδος δημιουργίας excel αρχείου για τα δεδομένα λίστας ευρετηρίου
     * @param title
     * @param data
     * @param model
     * @param fos
     */
    @Deprecated
    public static void writeToFile(String title, String data, String model, OutputStream fos) {
        GsonBuilder gsb = new GsonBuilder();
        Gson gson = gsb.disableHtmlEscaping().create();
        
        List<LinkedTreeMap<String, Object>> rows = gson.fromJson(data, List.class);
        List<LinkedTreeMap<String, Object>> columns = gson.fromJson(model, List.class);
        
        ByteArrayInputStream fis = null;
        XSSFWorkbook workbook;
        try {
            
            byte buf[] = new byte[]{};
            workbook = new XSSFWorkbook();
            
            XSSFSheet sheet = workbook.createSheet();
            
            //Create rows
            XSSFCellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short) 10);
            font.setBold(true);
            style.setFont(font);
            
            XSSFCellStyle style2 = workbook.createCellStyle();
            XSSFFont font2 = workbook.createFont();
            font2.setFontHeightInPoints((short) 16);
            style2.cloneStyleFrom(style);
            style2.setFont(font2);
            style2.setAlignment(CellStyle.ALIGN_CENTER);
            
            XSSFRow row0 = sheet.createRow(0);
            XSSFCell spanCell = row0.createCell(0);
            spanCell.setCellValue(title);
            spanCell.setCellStyle(style2);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.size() - 1));
            
            XSSFRow row1 = sheet.createRow(1);
            
            for (int c = 0; c < columns.size(); c++) {
                if (!cellTypes.get((String) columns.get(c).get("type")).equals(XSSFCell.CELL_TYPE_BLANK)) {
                    String columnName = columns.get(c).get("name").toString();
                    XSSFCell titleCol = row1.createCell(c);
                    titleCol.setCellValue(columnName);
                    titleCol.setCellStyle(style);
                }
            }
            
            for (int r = 0; r < rows.size(); r++) {
                
                LinkedTreeMap<String, Object> row = rows.get(r);
                
                XSSFRow row_x = sheet.createRow(r + 2);
                
                for (int c = 0; c < columns.size(); c++) {
                    if (!cellTypes.get((String) columns.get(c).get("type")).equals(XSSFCell.CELL_TYPE_BLANK)) {
                        String columnName = columns.get(c).get("name").toString();
                        
                        //String dataType = columns.get(c).get("type").toString();
                        String dataType = "";
                        if (columns.get(c).get("type") == null) {
                            dataType = "string";
                        } else {
                            dataType = columns.get(c).get("type").toString();
                        }
                        
                        XSSFCell titleCol = row_x.createCell(c);
                        Object cellObj = row.get(columnName);
                        String cell = cellObj != null ? cellObj.toString() : "";
                        
                        titleCol.setCellType(cellTypes.get(dataType));
                        if (cellTypes.get(dataType) == XSSFCell.CELL_TYPE_NUMERIC) {
                            
                            NumberFormat format = NumberFormat.getInstance(Locale.forLanguageTag("el-GR"));
                            Number number = format.parse(cell);
                            
                            Double val = cell == null ? 0 : number.doubleValue();
                            titleCol.setCellValue(val);
                            
                        } else
                            titleCol.setCellValue(cell == null ? null : cell);
                    }
                }
                
            }
            
            for (int c = 0; c < columns.size(); c++) {
                sheet.autoSizeColumn(c);
            }
            
            workbook.write(fos);
            fos.flush();
            fos.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
    }
    
}
