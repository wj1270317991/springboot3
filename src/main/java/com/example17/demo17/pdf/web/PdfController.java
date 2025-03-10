package com.example17.demo17.pdf.web;

import com.example17.demo17.pdf.util.KeyWordPositionListener;
import com.example17.demo17.pdf.util.WordItem;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;

/**
 * com.example17.demo17.pdf.web
 * User: wangjun
 * Date: 2025/3/5 - 18:27
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("/pdf")
public class PdfController {



    @SuppressWarnings("unchecked")
    @RequestMapping( "aaa")
    public void cgImportPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PdfReader reader = null;
        String filepath = "E:\\aaa.pdf";
        String outputPath = "E:\\bbb.pdf";
        String keyWord = "移动项目经理签字确认";
        int page = 1;
        PdfReader pdfReader = new PdfReader(filepath);

        PdfStamper stamper = new PdfStamper(pdfReader, new FileOutputStream(outputPath));

        //int pageNum = pdfReader.getNumberOfPages(); //循环没页PDF查找
        PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);
        KeyWordPositionListener renderListener = new KeyWordPositionListener();
        renderListener.setKeyWord(keyWord);
        pdfReaderContentParser.processContent(page, renderListener);
        WordItem wordItem = renderListener.getWordItem();
        if (wordItem == null) {
            System.out.println("没找到 " + keyWord);
            return;
        }

        System.out.println("找到了【" + keyWord + "】 " + wordItem.getContent() + " x= " + wordItem.getX() + " y= " + wordItem.getY());
        drawTextMarker(stamper,1,(float)wordItem.getX(), (float)wordItem.getY());
        stamper.close();
        pdfReader.close(); //记得要关闭，否则文件想做其它操作会报被占用
    }


    private static void drawTextMarker(PdfStamper stamper, int pageNum, float x, float y) {
        PdfContentByte canvas = stamper.getOverContent(pageNum);
        // 转换为左下角坐标系
        Rectangle pageSize = stamper.getReader().getPageSize(pageNum);
        float absoluteY = pageSize.getHeight() - y + 300;

        // 绘制绿色实心圆点
        canvas.setColorFill(BaseColor.GREEN);
        canvas.setLineWidth(0.5f);
        canvas.ellipse(x - 20, y + 20, x + 20, y + 20);
        canvas.fillStroke();
    }


}
