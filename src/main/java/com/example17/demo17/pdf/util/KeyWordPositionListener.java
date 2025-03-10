package com.example17.demo17.pdf.util;

import cn.hutool.core.util.StrUtil;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.awt.geom.Rectangle2D.Float;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public class KeyWordPositionListener implements RenderListener {

    /**
     * 用来存储页面上所有的词
     * - 排除连续空格
     */
    private List<WordItem> allItems = new ArrayList<WordItem>();

    /**
     * 搜索关键词
     */
    private String keyWord;
    /**
     * 是否是新的词
     */
    private boolean newWord = false;
    /**
     * 记录上一个字符 -- 用于判断是否是一组词
     */
    private WordItem prevItem = new WordItem();

    /**
     * 已找到的词信息
     */
    private WordItem wordItem;

    public WordItem getWordItem() {
        return wordItem;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public void beginTextBlock() {
        // TODO Auto-generated method stub
    }

    /**
     * 方法会在解析文本时被调用，它检查每个文本片段是否包含关键词，并记录其位置。
     *
     * @param renderInfo
     */
    @Override
    public void renderText(TextRenderInfo renderInfo) {
        if (wordItem != null || StrUtil.isEmpty(keyWord)) {
            return;
        }
        // 读取PDF时，有些肉眼看上去是一行的字，可能会被解析为多个，导致找不到满足条件的关键字，这里做了简单的处理
        // 即如果一些词是连续的，前后没有空白字符串，即认为是一个词
        String content = renderInfo.getText().trim();
        Float textRectangle = renderInfo.getBaseline().getBoundingRectange();
        if (StrUtil.isEmpty(content)) {
            // 当前扫出来的是空字符串，视新一个新的词即将开始
            newWord = true;
//            System.out.println("扫出空的，跳过  x=" + textRectangle.getX() + " y=" + textRectangle.getY());
            return;
        }
        if (StrUtil.isEmpty(prevItem.getContent())) {
            // 这段可以不需要
            // prevItem 中还没有存内容的，当前文字也视为新的词
            newWord = true;
//            System.out.println("prevItem 中还没有存内容的，视为新词");
        }
        if (StrUtil.isNotEmpty(prevItem.getContent()) && (Math.abs((int) prevItem.getY() - (int) textRectangle.getY()) > 5)) {
            //Y 正负2内，视为同一行
            System.out.println("不在同一行：prevItem=" + prevItem.getContent() + " x=" + (int) prevItem.getX() + " y=" + (int) prevItem.getY());
            System.out.println("不在同一行：content=" + content + " x=" + (int) textRectangle.getX() + " y=" + (int) textRectangle.getY());
            System.out.println("当前内容和prevItem 不在同一行，视为新词");
            newWord = true;
        }
        if (newWord) {
            //重置
            System.out.println("重置 prevItem: " + prevItem.getContent());
            prevItem = new WordItem();
        }
        System.out.println("已扫到字：content=" + content + " x=" + textRectangle.getX() + " y=" + textRectangle.getY());
        String preContent = StrUtil.isNotEmpty(prevItem.getContent()) ? prevItem.getContent() : "";
        prevItem.setContent(preContent + content);
        prevItem.setX(textRectangle.getX());
        prevItem.setY(textRectangle.getY());
        if (prevItem.getContent().contains(keyWord)) {
            //System.out.println("找到了【" + keyWord + "】 " + prevItem.getContent() + " x= " + prevItem.getX() + " y= " + prevItem.getY());
            wordItem = prevItem;
        }
        newWord = false;
    }

    @Override
    public void endTextBlock() {
        // TODO Auto-generated method stub
    }

    @Override
    public void renderImage(ImageRenderInfo renderInfo) {
        // TODO Auto-generated method stub
    }

}

/**
 * 存储一个词的信息
 */

