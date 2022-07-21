package com.zqz.service.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: ExcelController
 * @Date: Created in 17:37 2022/3/8
 */
@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelController {

    @PostMapping("/import")
    public void importDetail(@RequestParam(value = "file") MultipartFile serviceFile) throws IOException {
        ExcelReader excelReader = null;
        InputStream in = null;
        try {
            in = serviceFile.getInputStream();
            excelReader = EasyExcel.read(in).build();

            ExcelListener boxServerListener = new ExcelListener();
            ExcelListener platformListener = new ExcelListener();

            //获取sheet对象
            ReadSheet readBoxServerSheet =
                    EasyExcel.readSheet(0).head(SheetModelOne.class).registerReadListener(boxServerListener).build();
            ReadSheet readPlatformSheet =
                    EasyExcel.readSheet(1).head(SheetModelTwo.class).registerReadListener(platformListener).build();

            //读取数据
            excelReader.read(readBoxServerSheet, readPlatformSheet);

            //业务处理
            System.out.println(boxServerListener.getData());
            System.out.println(platformListener.getData());
        } catch (Exception ex) {
            log.error("import excel to db fail", ex);
        } finally {
            in.close();
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

}
