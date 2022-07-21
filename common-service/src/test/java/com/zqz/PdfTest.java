package com.zqz;

import com.zqz.service.ServiceApplication;
import com.zqz.service.pdf.PdfService;
import com.zqz.service.utils.DeleteFileUtil;
import com.zqz.service.utils.FileServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: PdfTest
 * @Date: Created in 14:20 2022/3/3
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceApplication.class})
public class PdfTest {
    public static final String USER_DIR = "user.dir";
    public static final String PDF_SUFFIX = ".pdf";

    @Autowired
    private PdfService pdfService;


    @Test
    public void fillPdf() {
        String dir = null;
        try {
            String pdfDir = ResourceUtils.getFile("classpath:pdf").getPath();
            String path = pdfDir + "\\PdfTestTemplate.pdf";

            //参数
            Map<String, String> param = new HashMap<>();
            param.put("param1", "zqz111111111111111111111");
            param.put("param2", "宜昌市地区222222222");

            byte[] fileBytes = FileServiceUtil.readFile2ByteArrayNIO(path);

            Random rd = new Random();
            // 新建合同号文件夹放置各类阶段性pdf
            dir = System.getProperty(USER_DIR) + File.separator + rd.nextInt(1000);
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            //首先修改pdf
            String modifyPath = dir + File.separator + rd.nextInt(1000)
                    + System.currentTimeMillis()
                    + rd.nextInt(1000)
                    + PDF_SUFFIX;
            pdfService.modifyPdf(modifyPath, param, fileBytes);

        } catch (Exception e) {
            log.error("fill pdf error:{}", e.getMessage(), e);
        } finally {
            if(null != dir){
                DeleteFileUtil.deleteDirectory(dir);
            }
        }
    }
}
