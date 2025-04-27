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
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.ext.analyzer.DocumentAnalyzer;
import org.dromara.pdf.pdfbox.core.info.TextInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.util.Set;

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




    @RequestMapping( "bbb")
    public void bbb(HttpServletRequest request, HttpServletResponse response) throws Exception {

                // 加载文档
                Document document = PdfHandler.getDocumentHandler().load("E:\\aaa.pdf");
                // 创建文档分析器
                DocumentAnalyzer analyzer = new DocumentAnalyzer(document);

            // 解析文本信息（文档）
            Set<TextInfo> infoSet = analyzer.analyzeText(document.getPages().size()-1);
            // 输出文本信息
            infoSet.forEach(System.out::println);

            // 构建文本组件（单行，自动换行）
            Textarea textarea = new Textarea(document.getPage(document.getPages().size()-1));
            // 设置文本
            textarea.setText("测试");
            // 设置字体
            textarea.setFontName("宋体");
            // 设置字体大小
            textarea.setFontSize(20F);
            textarea.setBeginX(422.39624f);
            textarea.setBeginY(247.83105f);
            // 绘制
            textarea.render();
            // 保存文档
            document.save("E:\\ccc.pdf");
            // 关闭文档
            document.close();

    }


}
