package com.example17.demo17.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * com.example17.demo17.entity
 * ClassName: FileDTO
 * Description: 文件实体
 * Create by: wangjun
 * Date: 2024/5/24 15:53
 */
@Data
public class FileDTO {

    private String md5;

    private MultipartFile file;

    private Long chunkSize;

    private Integer totalNumber;

    private Long chunkNumber;
}
