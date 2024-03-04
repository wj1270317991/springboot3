package com.example17.demo17.batch;

import com.example17.demo17.entity.BigDataUsers;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * com.example17.demo17.batch
 * ClassName: UserItemWriter
 * Description:
 * Create by: wangjun
 * Date: 2024/3/1 11:53
 */
public class UserItemWriter implements ItemWriter<BigDataUsers> {

    @Override
    public void write(Chunk<? extends BigDataUsers> chunk) throws Exception {
        List<? extends BigDataUsers> items = chunk.getItems();
    }
}
