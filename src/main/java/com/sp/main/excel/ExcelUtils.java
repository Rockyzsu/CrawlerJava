package com.sp.main.excel;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel操作工具类
 * @author Rocky
 */
public class ExcelUtils {

    // @Value("${file_base_path}")
    // private static String fileBasePath;//文件的基础路径
    // private static String fileBasePath = System.getProperty("user.dir") + File.separator + "excel" + File.separator;;//文件的基础路径

    public static final String OFFICE_EXCEL_XLS = "xls";
    public static final String OFFICE_EXCEL_XLSX = "xlsx";

    /**
     * 读取指定Sheet也的内容
     * @param filepath filepath 文件全路径
     * @param sheetNo sheet序号,从0开始,如果读取全文sheetNo设置null
     */
    public static String readExcel(String filepath, Integer sheetNo)
            throws EncryptedDocumentException, InvalidFormatException, IOException {
        StringBuilder sb = new StringBuilder();
        Workbook workbook = getWorkbook(filepath);
        if (workbook != null) {
            if (sheetNo == null) {
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    Sheet sheet = workbook.getSheetAt(i);
                    if (sheet == null) {
                        continue;
                    }
                    sb.append(readExcelSheet(sheet));
                }
            } else {
                Sheet sheet = workbook.getSheetAt(sheetNo);
                if (sheet != null) {
                    sb.append(readExcelSheet(sheet));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 根据文件路径获取Workbook对象
     * @param filepath 文件全路径
     */
    public static Workbook getWorkbook(String filepath)
            throws EncryptedDocumentException, InvalidFormatException, IOException {
        InputStream is = null;
        Workbook wb = null;
        if (StringUtils.isBlank(filepath)) {
            throw new IllegalArgumentException("文件路径不能为空");
        } else {
            String suffiex = getSuffiex(filepath);
            if (StringUtils.isBlank(suffiex)) {
                throw new IllegalArgumentException("文件后缀不能为空");
            }
            if (OFFICE_EXCEL_XLS.equals(suffiex) || OFFICE_EXCEL_XLSX.equals(suffiex)) {
                try {
                    is = new FileInputStream(filepath);
                    wb = WorkbookFactory.create(is);
                } finally {
                    if (is != null) {
                        is.close();
                    }
                    if (wb != null) {
                        wb.close();
                    }
                }
            } else {
                throw new IllegalArgumentException("该文件非Excel文件");
            }
        }
        return wb;
    }

    /**
     * 获取后缀
     * @param filepath filepath 文件全路径
     */
    private static String getSuffiex(String filepath) {
        if (StringUtils.isBlank(filepath)) {
            return "";
        }
        int index = filepath.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return filepath.substring(index + 1, filepath.length());
    }

    private static String readExcelSheet(Sheet sheet) {
        StringBuilder sb = new StringBuilder();
        
        if(sheet != null){
            int rowNos = sheet.getLastRowNum();// 得到excel的总记录条数
            for (int i = 0; i <= rowNos; i++) {// 遍历行
                Row row = sheet.getRow(i);
                if(row != null){
                    int columNos = row.getLastCellNum();// 表头总共的列数
                    for (int j = 0; j < columNos; j++) {
                        Cell cell = row.getCell(j);
                        if(cell != null){
                            cell.setCellType(CellType.STRING);
                            sb.append(cell.getStringCellValue() + " ");
                            // System.out.print(cell.getStringCellValue() + " ");
                        }
                    }
                    // System.out.println();
                }
            }
        }
        
        return sb.toString();
    }

    /**
     * 读取指定Sheet页的表头
     * @param filepath filepath 文件全路径
     * @param sheetNo sheet序号,从0开始,必填
     */
    public static Row readTitle(String filepath, int sheetNo)
            throws IOException, EncryptedDocumentException, InvalidFormatException {
        Row returnRow = null;
        Workbook workbook = getWorkbook(filepath);
        if (workbook != null) {
            Sheet sheet = workbook.getSheetAt(sheetNo);
            returnRow = readTitle(sheet);
        }
        return returnRow;
    }

    /**
     * 读取指定Sheet页的表头
     */
    public static Row readTitle(Sheet sheet) throws IOException {
        Row returnRow = null;
        int totalRow = sheet.getLastRowNum();// 得到excel的总记录条数
        for (int i = 0; i < totalRow; i++) {// 遍历行
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            returnRow = sheet.getRow(0);
            break;
        }
        return returnRow;
    }

    /**
     * 创建Excel文件
     * @param filepath filepath 文件全路径
     * @param sheetName 新Sheet页的名字
     * @param titles 表头
     * @param values 每行的单元格
     */
    public static boolean writeExcel(String filepath, String sheetName, List<String> titles,
            List<Map<String, Object>> values) throws IOException {
        boolean success = false;
        OutputStream outputStream = null;
        if (StringUtils.isBlank(filepath)) {
            throw new IllegalArgumentException("文件路径不能为空");
        } else {
            String suffiex = getSuffiex(filepath);
            if (StringUtils.isBlank(suffiex)) {
                throw new IllegalArgumentException("文件后缀不能为空");
            }
            Workbook workbook;
            if ("xls".equals(suffiex.toLowerCase())) {
                workbook = new HSSFWorkbook();
            } else {
                workbook = new XSSFWorkbook();
            }
            // 生成一个表格
            Sheet sheet;
            if (StringUtils.isBlank(sheetName)) {
                // name 为空则使用默认值
                sheet = workbook.createSheet();
            } else {
                sheet = workbook.createSheet(sheetName);
            }
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth((short) 15);
            // 生成样式
            Map<String, CellStyle> styles = createStyles(workbook);
            // 创建标题行
            Row row = sheet.createRow(0);
            // 存储标题在Excel文件中的序号
            Map<String, Integer> titleOrder = Maps.newHashMap();
            for (int i = 0; i < titles.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(styles.get("header"));
                String title = titles.get(i);
                cell.setCellValue(title);
                titleOrder.put(title, i);
            }
            // 写入正文
            Iterator<Map<String, Object>> iterator = values.iterator();
            // 行号
            int index = 1;
            while (iterator.hasNext()) {
                row = sheet.createRow(index);
                Map<String, Object> value = iterator.next();
                for (Map.Entry<String, Object> map : value.entrySet()) {
                    // 获取列名
                    String title = map.getKey();
                    // 根据列名获取序号
                    int i = titleOrder.get(title);
                    // 在指定序号处创建cell
                    Cell cell = row.createCell(i);
                    // 设置cell的样式
                    if (index % 2 == 1) {
                        cell.setCellStyle(styles.get("cellA"));
                    } else {
                        cell.setCellStyle(styles.get("cellB"));
                    }
                    // 获取列的值
                    Object object = map.getValue();
                    // 判断object的类型
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (object instanceof Double) {
                        cell.setCellValue((Double) object);
                    } else if (object instanceof Date) {
                        String time = simpleDateFormat.format((Date) object);
                        cell.setCellValue(time);
                    } else if (object instanceof Calendar) {
                        Calendar calendar = (Calendar) object;
                        String time = simpleDateFormat.format(calendar.getTime());
                        cell.setCellValue(time);
                    } else if (object instanceof Boolean) {
                        cell.setCellValue((Boolean) object);
                    } else {
                        if (object != null) {
                            cell.setCellValue(object.toString());
                        }
                    }
                }
                index++;
            }

            try {
                outputStream = new FileOutputStream(filepath);
                workbook.write(outputStream);
                success = true;
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            }
            return success;
        }
    }

    /**
     * 设置格式
     */
    private static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = Maps.newHashMap();

        // 标题样式
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER); // 水平对齐
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直对齐
        titleStyle.setLocked(true); // 样式锁定
        titleStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleFont.setFontName("微软雅黑");
        titleStyle.setFont(titleFont);
        styles.put("title", titleStyle);

        // 文件头样式
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex()); // 前景色
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 颜色填充方式
        headerStyle.setWrapText(true);
        headerStyle.setBorderRight(BorderStyle.THIN); // 设置边界
        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        titleFont.setFontName("微软雅黑");
        headerStyle.setFont(headerFont);
        styles.put("header", headerStyle);

        Font cellStyleFont = wb.createFont();
        cellStyleFont.setFontHeightInPoints((short) 12);
        cellStyleFont.setColor(IndexedColors.BLUE_GREY.getIndex());
        cellStyleFont.setFontName("微软雅黑");

        // 正文样式A
        CellStyle cellStyleA = wb.createCellStyle();
        cellStyleA.setAlignment(HorizontalAlignment.CENTER); // 居中设置
        cellStyleA.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleA.setWrapText(true);
        cellStyleA.setBorderRight(BorderStyle.THIN);
        cellStyleA.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderLeft(BorderStyle.THIN);
        cellStyleA.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderTop(BorderStyle.THIN);
        cellStyleA.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderBottom(BorderStyle.THIN);
        cellStyleA.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setFont(cellStyleFont);
        styles.put("cellA", cellStyleA);

        // 正文样式B:添加前景色为浅黄色
        CellStyle cellStyleB = wb.createCellStyle();
        cellStyleB.setAlignment(HorizontalAlignment.CENTER);
        cellStyleB.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleB.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        cellStyleB.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleB.setWrapText(true);
        cellStyleB.setBorderRight(BorderStyle.THIN);
        cellStyleB.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setBorderLeft(BorderStyle.THIN);
        cellStyleB.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setBorderTop(BorderStyle.THIN);
        cellStyleB.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setBorderBottom(BorderStyle.THIN);
        cellStyleB.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setFont(cellStyleFont);
        styles.put("cellB", cellStyleB);

        return styles;
    }

    /**
     * 将源文件的内容复制到新Excel文件(可供理解Excel使用,使用价值不大)
     * @param srcFilepath 源文件全路径
     * @param desFilepath 目标文件全路径
     */
    public static void writeExcel(String srcFilepath, String desFilepath)
            throws IOException, EncryptedDocumentException, InvalidFormatException {
        FileOutputStream outputStream = null;
        String suffiex = getSuffiex(desFilepath);
        if (StringUtils.isBlank(suffiex)) {
            throw new IllegalArgumentException("文件后缀不能为空");
        }
        Workbook workbook_des;
        if ("xls".equals(suffiex.toLowerCase())) {
            workbook_des = new HSSFWorkbook();
        } else {
            workbook_des = new XSSFWorkbook();
        }

        Workbook workbook = getWorkbook(srcFilepath);
        if (workbook != null) {
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int k = 0; k < numberOfSheets; k++) {
                Sheet sheet = workbook.getSheetAt(k);
                Sheet sheet_des = workbook_des.createSheet(sheet.getSheetName());
                if (sheet != null) {
                    int rowNos = sheet.getLastRowNum();
                    for (int i = 0; i <= rowNos; i++) {
                        Row row = sheet.getRow(i);
                        Row row_des = sheet_des.createRow(i);
                        if(row != null){
                            int columNos = row.getLastCellNum();
                            for (int j = 0; j < columNos; j++) {
                                Cell cell = row.getCell(j);
                                Cell cell_des = row_des.createCell(j);
                                if(cell != null){
                                    cell.setCellType(CellType.STRING);
                                    cell_des.setCellType(CellType.STRING);
                                    
                                    cell_des.setCellValue(cell.getStringCellValue());
                                }
                            }
                        }
                    }
                }
                
            }
        }
        
        try {
            outputStream = new FileOutputStream(desFilepath);
            workbook_des.write(outputStream);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (workbook != null) {
                workbook_des.close();
            }
        }
    }
    
    public static void main(String[] args) {
        
    }
}