package com.example17.demo17.batch;

import com.alibaba.fastjson2.JSONObject;
import com.example17.demo17.entity.BigDataUsers;
import org.springframework.batch.item.ItemProcessor;

/**
 * com.example17.demo17.batch
 * ClassName: UserItemProcessor
 * Description:
 * Create by: wangjun
 * Date: 2024/3/1 14:03
 */
public class UserItemProcessor implements ItemProcessor<BigDataUsers,BigDataUsers> {

    @Override
    public BigDataUsers process(BigDataUsers item) throws Exception {
        System.out.println(JSONObject.toJSONString(item));
        return item;
    }
}
