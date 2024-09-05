package com.example17.demo17.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example17.demo17.entity.BigDataUsers;
import com.example17.demo17.service.BigDataUsersService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * com.example17.demo17.web
 * ClassName: EasyExcelController
 * Description:
 * Create by: wangjun
 * Date: 2024/2/5 14:17
 */
@Slf4j
@RequestMapping(value = "excel")
@RestController
public class EasyExcelController {

    @Autowired
    BigDataUsersService bigDataUsersService;


    @Transactional(rollbackFor = Exception.class)
    @GetMapping("saveSleep")
    public String saveSleep() throws Exception{
        BigDataUsers bigDataUsers = new BigDataUsers();
        bigDataUsers.setAge(1000);
        bigDataUsers.setName("aaaa");
        System.out.println("开启睡眠："+ DateUtil.now());
        bigDataUsersService.save(bigDataUsers);
        for (int ii = 0;ii<3600;ii++) {
            ThreadUtil.sleep(10, TimeUnit.NANOSECONDS);
            System.out.println("循环里面："+ DateUtil.now());
        }
        //int i = 1/0;
        System.out.println("结束睡眠："+ DateUtil.now());
        return "";
    }


    @GetMapping("list")
    public String list() {
        List<BigDataUsers> list = bigDataUsersService.list(
                Wrappers.<BigDataUsers>lambdaQuery()
                        .lt(BigDataUsers::getId, 10)
        );
        return JSONObject.toJSONString(list, JSONWriter.Feature.FieldBased);
    }


    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        log.info("*********导出开始**********");
        /**总数量**/
        long totalCount = bigDataUsersService.count();
        int sheetDataRows = 300000;

        String fileName = "downloadBig";
        OutputStream outputStream  = response.getOutputStream();
        long sheetNum = totalCount % sheetDataRows == 0 ? (totalCount / sheetDataRows) : (totalCount / sheetDataRows + 1);
        log.info(String.valueOf(sheetNum));
        //模拟的分页查询数据
        try {
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            for (int i = 1; i <= sheetNum; i++) {

                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class
                // 实际上可以一直变
                WriteSheet writeSheet = EasyExcel.writerSheet(i).head(BigDataUsers.class).build();
                Page<BigDataUsers> p = new Page<>(i,sheetDataRows);
                Page<BigDataUsers> page = bigDataUsersService.page(p, Wrappers.<BigDataUsers>lambdaQuery().orderByAsc(BigDataUsers::getId));
                log.info(page.toString());

                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                excelWriter.write(page.getRecords(), writeSheet);
            }

            // 下载EXCEL，返回给前段stream流
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            excelWriter.finish();
            outputStream.flush();
            outputStream.close();
            log.info("********* 导出结束！**************");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
