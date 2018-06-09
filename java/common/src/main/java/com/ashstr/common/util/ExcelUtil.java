package com.ashstr.common.util;

import com.google.common.collect.Lists;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2017-12-15 下午2:06
 * @Description
 */
public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * @param workbook   需要导入的excel，用于多sheet的设计，为空为新建
     * @param dataSource 要导入的数据源列表
     * @param columnInfo Map<列名，数据源中的字段名>
     * @param sheetName  sheet名称
     * @return HSSFWorkbook
     */
    public static <T> HSSFWorkbook createExcel(HSSFWorkbook workbook, List<T> dataSource, Map<String, String> columnInfo, String sheetName) {
        if (workbook == null) {
            workbook = new HSSFWorkbook();
        }
        if (dataSource == null) {
            dataSource = new ArrayList<>();
        }
        try {
            HSSFSheet sheet = workbook.createSheet(sheetName);
            List<String> titleColumns = Lists.newArrayList(columnInfo.keySet());
            //标题行
            HSSFRow titleRow = sheet.createRow(0);
            for (int i = 0; i < titleColumns.size(); i++) {
                String columnName = titleColumns.get(i);
                HSSFCell cell = titleRow.createCell(i);
                cell.setCellValue(columnName);
            }
            //内容行
            for (int i = 0; i < dataSource.size(); i++) {
                HSSFRow dataRow = sheet.createRow(i + 1);
                T rowData = dataSource.get(i);
                for (int j = 0; j < titleColumns.size(); j++) {
                    HSSFCell cell = dataRow.createCell(j);
                    Field field = rowData.getClass().getDeclaredField(columnInfo.get(titleColumns.get(j)));
                    field.setAccessible(true);
                    if (field.get(rowData) == null) {
                        cell.setCellValue("");
                    } else {
                        Object object = field.get(rowData);
                        if (object instanceof Date) {
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            String date = DateUtils.formatDate((Date) object);
                            cell.setCellValue(date);
                        } else if (object instanceof Double) {
                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                            cell.setCellValue((Double) object);
                        } else if (object instanceof Long) {
                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                            cell.setCellValue((Long) object);
                        } else if (object instanceof Integer) {
                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                            cell.setCellValue((Integer) object);
                        } else if (object instanceof Float) {
                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                            cell.setCellValue((Float) object);
                        } else if (object instanceof BigDecimal) {
                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                            cell.setCellValue(((BigDecimal) object).floatValue());
                        } else {
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cell.setCellValue(object.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Excel导入错误", e);
        }
        return workbook;
    }
}
