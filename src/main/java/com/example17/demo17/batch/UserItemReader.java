package com.example17.demo17.batch;

import com.example17.demo17.entity.BigDataUsers;
import com.example17.demo17.mapper.BigDataUsersMapper;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * com.example17.demo17.service.impl
 * ClassName: UserItemWriter
 * Description:
 * Create by: wangjun
 * Date: 2024/2/29 16:25
 */
@Component
public class UserItemReader implements ItemReader<BigDataUsers> {

    @Autowired
    BigDataUsersMapper bigDataUsersMapper;

    @Override
    public BigDataUsers read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
