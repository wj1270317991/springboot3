package com.example17.demo17.pdf.util;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * com.example17.demo17.pdf.util
 * User: wangjun
 * Date: 2025/3/6 - 13:29
 * Description:
 */
public class PDFUtil {
    public static List<PDFUtil.KeyWordInfo> getKeyWordLocation(final String keyWords, PdfReader reader) throws IOException {
        PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(reader);
        List<PDFUtil.KeyWordInfo> keyWordInfoList = new ArrayList<>();
        int pageNum = reader.getNumberOfPages();
        for(int page = 1; page <= pageNum; page++ ){
            Rectangle pageSize = reader.getPageSize(page);
            float pdfPageW = pageSize.getWidth();
            float pdfPageH = pageSize.getHeight();
            int finalPage = page;
            pdfReaderContentParser.processContent(page,
                    new RenderListener() {
                        @Override
                        public void renderText(TextRenderInfo textRenderInfo) {
                            final PDFUtil.KeyWordInfo keyWordInfo = new PDFUtil.KeyWordInfo();
                            keyWordInfo.setCoordinatePage(finalPage);
                            String text = textRenderInfo.getText(); // 整页内容

                            if (null != text && text.contains(keyWords)) {
                                Rectangle2D.Float boundingRectange = textRenderInfo
                                        .getBaseline().getBoundingRectange();
                                float leftY = (float) boundingRectange.getMinY() - 1;
                                float rightY = (float) boundingRectange.getMaxY() + 1;

                                System.out.println(boundingRectange.x + "--"
                                        + boundingRectange.y + "---");
                                keyWordInfo.setHeight(rightY - leftY);
                                keyWordInfo.setWidth((rightY - leftY)
                                        * keyWords.length());
                                keyWordInfo.setX(boundingRectange.x);
                                keyWordInfo.setY(boundingRectange.y);
                                keyWordInfo.setPageHeight(pdfPageH);
                                keyWordInfo.setPageWidth(pdfPageW);
                                keyWordInfoList.add(keyWordInfo);
                            }
                        }

                        @Override
                        public void renderImage(ImageRenderInfo arg0) {}

                        @Override
                        public void endTextBlock() {}

                        @Override
                        public void beginTextBlock() {}
                    });

        }
        return keyWordInfoList;
    }

    public static class KeyWordInfo {
        private float x;//在pdf的x坐标
        private float y;//在pdf的y坐标
        private double width;//关键字的宽度
        private double height;//关键字高度
        private int coordinatePage ;//关键字所在页
        private float pageWidth ;//关键字所在页宽度
        private float pageHeight ;//关键字所在页高度
        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public int getCoordinatePage() {
            return coordinatePage;
        }

        public void setCoordinatePage(int coordinatePage) {
            this.coordinatePage = coordinatePage;
        }

        public KeyWordInfo(float x, float y, int coordinatePage) {
            this.x = x;
            this.y = y;
            this.coordinatePage = coordinatePage;
        }

        public float getPageWidth() {
            return pageWidth;
        }

        public void setPageWidth(float pageWidth) {
            this.pageWidth = pageWidth;
        }

        public float getPageHeight() {
            return pageHeight;
        }

        public void setPageHeight(float pageHeight) {
            this.pageHeight = pageHeight;
        }

        public KeyWordInfo() {
        }

        @Override
        public String toString() {
            return "KeyWordInfo{" +
                    "x=" + x +
                    ", y=" + y +
                    ", width=" + width +
                    ", height=" + height +
                    ", coordinatePage=" + coordinatePage +
                    '}';
        }
    }
    public static void main(String arg[]) {

        try {
            PdfReader reader = new PdfReader("E:\\aaa.pdf");
            List<KeyWordInfo> keyWordInfo = PDFUtil.getKeyWordLocation(
                    "移动项目经理签字确认", reader);
            if (keyWordInfo != null && keyWordInfo.size() == 2) {
                //签订时间：  只有两个 取中间位置
                float x = (keyWordInfo.get(0).getX() + keyWordInfo.get(1).getX()) / 2;
                float y = keyWordInfo.get(0).getY();
                float pageW = keyWordInfo.get(0).getPageWidth();
                float pageH = keyWordInfo.get(0).getPageHeight();

                float wScale = x / pageW;
                float hScale = y / pageH;
                System.out.println("横坐标比例：" + wScale);
                System.out.println("纵坐标比例：" + hScale);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
