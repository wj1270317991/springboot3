package com.example17.demo17.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example17.demo17.entity.Menu;
import com.example17.demo17.service.impl.MenuServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* (menu)表控制层
*
* @author xxxxx
*/

@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {
/**
* 服务对象
*/
@Autowired
private MenuServiceImpl menuServiceImpl;

/**
* 通过主键查询单条数据
*
* @return 单条数据
*/
@GetMapping("save")
public void save() {
    String fileName =  "E://varianceRequireCollection.xlsx";
    Map<String,String> map = new HashMap<>();
    map.put("供应链菜单","数智供应链");
    map.put("ES系统菜单","ES");
    map.put("ES系统结构化-工具菜单","ES");
    map.put("大数据平台菜单","大数据平台");

    for (String key : map.keySet()) {
        EasyExcel.read(fileName, Menu.class, new PageReadListener<Menu>(dataList -> {
            handleItem(dataList, map.get(key),key);
        }, 1000)).sheet(key).doRead();
    }

}

private void handleItem(List<Menu> dataList,String sourceSystem,String sheetName){
    List<Menu> menuList = new ArrayList<>();
    Map<String,Long>  oneMap = new HashMap<>();
    Map<String,Long>  onetwoMap = new HashMap<>();
    Map<String,Long>  onetwothreeMap = new HashMap<>();
    int oneS = 0;
    int twoS = 0;
    int threeS = 0;
    if ("ES系统结构化-工具菜单".equals(sheetName)){
        oneS = 100;
    }


    for (Menu item : dataList){
        String one = item.getOne();
        String two = item.getTwo();
        String three = item.getThree();
        Long oneId = oneMap.get(one);

        if (oneMap.get(one) == null){
            oneId = IdWorker.getId();
            Menu menuOne = new Menu();
            menuOne.setMenuId(oneId);
            menuOne.setTitle(one);
            menuOne.setCode(one);
            menuOne.setEnableFlag("Y");
            menuOne.setParentId(0L);
            menuOne.setDisplayOrder(oneS);
            menuOne.setCreatedDate(DateUtil.date());
            menuOne.setLastUpdate(DateUtil.date());
            menuOne.setSourceSystem(sourceSystem);
            oneS++;
            oneMap.put(one,oneId);
            menuList.add(menuOne);
        }
        Long twoId = onetwoMap.get(one + "-" + two);
        /**保证为同一个一级菜单**/
        if(onetwoMap.get(one + "-" + two) == null) {
            twoId = IdWorker.getId();
            Menu menuTwo = new Menu();
            menuTwo.setMenuId(twoId);
            menuTwo.setTitle(two);
            menuTwo.setCode(two);
            menuTwo.setDisplayOrder(twoS);
            menuTwo.setCreatedDate(DateUtil.date());
            menuTwo.setLastUpdate(DateUtil.date());
            twoS++;
            threeS = 0;
            menuTwo.setEnableFlag("Y");
            menuTwo.setParentId(oneId);
            menuTwo.setSourceSystem(sourceSystem);
            onetwoMap.put(one+"-"+two, twoId);
            menuList.add(menuTwo);
        }

        if (StrUtil.isNotEmpty(item.getThree())) {
            if(onetwothreeMap.get(one + "-" + two + "-" + three) == null) {
                Menu menuThree = new Menu();
                menuThree.setMenuId(IdWorker.getId());
                menuThree.setTitle(item.getThree());
                menuThree.setCode(item.getThree());
                menuThree.setEnableFlag("Y");
                menuThree.setDisplayOrder(threeS);
                menuThree.setParentId(twoId);
                onetwothreeMap.put(one+"-"+two+"-"+three, menuThree.getMenuId());
                menuThree.setCreatedDate(DateUtil.date());
                menuThree.setLastUpdate(DateUtil.date());
                menuThree.setSourceSystem(sourceSystem);
                threeS++;
                menuList.add(menuThree);
            }
        }

    }
    menuServiceImpl.saveBatch(menuList,800);
}

}
