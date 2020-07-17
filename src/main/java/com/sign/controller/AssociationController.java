package com.sign.controller;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.sign.dao.SignUpDao;
import com.sign.entity.Add;
import com.sign.entity.Collect;
import com.sign.function.TokenProcessor;
import com.sign.service.ISignUpService;
import com.sign.utils.FilePathUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

@Controller
public class AssociationController {
    @Resource
    SignUpDao signUpDao;

    @Resource
    TokenProcessor tokenProcessor;

//    @GetMapping("/path")
    public String association(@RequestParam(value = "lists") List<String> lists){
        System.out.println(lists);
        FilePathUtils.getFileName();
        return "AdTicket";
    }


}
