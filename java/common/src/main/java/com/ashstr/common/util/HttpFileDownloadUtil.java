package com.ashstr.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;


/**
 * @author keven
 * @date 2017-12-15 下午2:06
 * @Description excel 下载 工具
 */
public class HttpFileDownloadUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpFileDownloadUtil.class);

    /**
     * Excel下载
     */
    public static void downloadExcel(HttpServletResponse response, HSSFWorkbook hssfWorkbook, String fileName) {
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO_8859_1"));
            response.setContentType("application/vnd.ms-excel");
            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            hssfWorkbook.write(os);
            byte[] content = os.toByteArray();
            outputStream.write(content);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("Excel下载错误", e);
        }
    }
}
