package com.example17.demo17.pdf.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * com.example17.demo17.pdf.util
 * User: wangjun
 * Date: 2025/3/6 - 14:57
 * Description:
 */
public class BookmarkMarker {

    @SneakyThrows
    public static void main(String[] args) {
        BookmarkMarker.addBookmarkCircles("E:\\aaa.pdf","E:\\bbb.pdf");
    }


    public static void addBookmarkCircles(String inputPath, String outputPath) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(inputPath);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputPath));

        // 获取所有书签
        List<HashMap<String, Object>> bookmarks = SimpleBookmark.getBookmark(reader);

        for (HashMap<String, Object> bookmark : bookmarks) {
            String title = (String) bookmark.get("Title");
            PdfObject dest = (PdfObject) bookmark.get("Dest");

            if (dest instanceof PdfArray) {
                PdfArray destArray = (PdfArray) dest;
                PdfNumber pageNumber = (PdfNumber) destArray.getPdfObject(0);
                PdfArray position = (PdfArray) destArray.getPdfObject(1);

                float x = position.getAsNumber(0).floatValue();
                float y = position.getAsNumber(1).floatValue();

                // 获取对应页面
                PdfContentByte canvas = stamper.getOverContent(pageNumber.intValue() + 1);

                // 画红色圆圈标记坐标
                canvas.setColorStroke(BaseColor.RED);
                canvas.setLineWidth(1f);
                canvas.ellipse(x - 5, y - 5, x + 5, y + 5);
                canvas.stroke();

                // 添加文字标注
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                        new Phrase(title, new Font(Font.FontFamily.HELVETICA, 8)),
                        x + 8, y, 0);
            }
        }

        stamper.close();
        reader.close();
    }
}
