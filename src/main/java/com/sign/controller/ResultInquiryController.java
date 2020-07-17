package com.sign.controller;

import com.sign.entity.Achievement;
import com.sign.service.IResultInquiryService;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 成绩查询
 */
@Controller
public class ResultInquiryController {

    @Resource
    IResultInquiryService iResultInquiryService;

    @GetMapping("/res")
    public String resultInquire(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        String sfzhm= (String) session.getAttribute("id");
        System.out.println(sfzhm);
        Achievement achievement=iResultInquiryService.queryById(sfzhm);
        model.addAttribute("ach",achievement);
        return "table/result";
    }
}
