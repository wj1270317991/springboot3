package com.example17.demo17.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example17.demo17.entity.BigDataUsers;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * com.example17.demo17.mapper
 * ClassName: BigDataUsersMapper
 * Description:
 * Create by: wangjun
 * Date: 2024/2/5 14:15
 */

public interface BigDataUsersMapper extends BaseMapper<BigDataUsers> {

    List<BigDataUsers> getList(Map<String,Object> params);

}
