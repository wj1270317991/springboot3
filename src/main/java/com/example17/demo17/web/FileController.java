package com.example17.demo17.web;

import com.example17.demo17.entity.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.example17.demo17.web
 * ClassName: RBloomController
 * Description: 文件
 * Create by: wangjun
 * Date: 2024/1/15 17:59
 */
@Slf4j
@RestController
@RequestMapping(value = "file")
public class FileController {

    public static final String UPLOAD_PATH = "/tmp/";

    @RequestMapping("upload")
    public String upload1(@RequestBody FileDTO file) {
        //文件存放位置
        String dstFile = String.format("%s/%s.%s", UPLOAD_PATH, file.getMd5(),  StringUtils.getFilenameExtension(file.getFile().getOriginalFilename()));
        //上传分片信息存放位置
        String confFile = String.format("%s\\%s\\%s.conf", UPLOAD_PATH, file.getMd5(), file.getMd5());

        return "success";
    }

}
