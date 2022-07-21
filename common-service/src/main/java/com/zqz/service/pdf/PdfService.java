package com.zqz.service.pdf;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: PdfService
 * @Date: Created in 14:15 2022/3/3
 */
@Service
@Slf4j
public class PdfService {


    public void modifyPdf(String dstFile, Map<String, String> contractMap, byte[] bytes) {
        try {
            // 读入pdf表单
            PdfReader reader = new PdfReader(bytes);
            // 根据表单生成一个新的pdf
            PdfStamper ps = new PdfStamper(reader, new FileOutputStream(dstFile));
            // 获取pdf表单
            AcroFields s = ps.getAcroFields();
            // 给表单添加中文字体, 不设置的话中文无法显示
            BaseFont bf = BaseFont.createFont("font/simsun.ttc" + ",1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            s.addSubstitutionFont(bf);

            Iterator<Map.Entry<String, String>> entries = contractMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                s.setField(entry.getKey(), entry.getValue());
            }
            // 如果为false那么生成的PDF文件还能编辑, 一定要设为true
            ps.setFormFlattening(true);
            ps.close();
            reader.close();
        } catch (Exception e) {
            log.error("--修改PDF合同异常:[{}]", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
