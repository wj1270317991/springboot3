package com.example17.demo17.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example17.demo17.entity.Menu;
import com.example17.demo17.mapper.MenuMapper;
import com.example17.demo17.service.MenuService;
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService{

}
