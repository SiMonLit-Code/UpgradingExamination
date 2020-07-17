package com.sign.controller;

import com.alibaba.excel.EasyExcel;
import com.sign.entity.*;
import com.sign.service.*;
import com.sign.utils.FilePathUtils;
import com.sign.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AdmLoginController {
    @Resource
    ISignUpService iSignUpService;

    @Resource
    IDMService idmService;

    @Resource
    IRegisterService iRegisterService;



    @Resource
    IAdminService iAdminService;

    //管理员登陆
    @PostMapping("/loginAdm")
    public ModelAndView login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ModelAndView mv = new ModelAndView();
        if (iAdminService.findAdmin(username,password)){
            //密码正确，设置session
            session.setAttribute("loginAdmMsg", "adminlogin");
            mv.setViewName("redirect:/mainAd");
        }else {
            //密码错误返回登录页面
            mv.addObject("msg", "用户名或密码有误");
            mv.setViewName("admin/adminlogin");
        }
        return mv;
    }

    //管理员密码修改
    @PostMapping("/adminUpdate")
    public ModelAndView adminUpdate(HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
//        System.out.println(admin.getPassword()+"-----"+request.getParameter("password"));
        if (iAdminService.findAdmin("admin",request.getParameter("password")))
        {
            if (iAdminService.updateAdmin("admin",request.getParameter("password1"))==1){
                mv.addObject("Msg","密码修改成功");
            }else {
                mv.addObject("Msg","密码修改失败");
            }
        }else {
            mv.addObject("Msg","密码错误");
        }
        mv.setViewName("admin/adminUpdate");
        return mv;
    }

    //跳转修改页面
    @GetMapping("/adminUpdatePage")
    public String adminUpdatePage(){
        return "admin/adminUpdate";
    }

    @GetMapping("/enter")
    public String enterAdm() {
        return "admin/adminlogin";
    }

    //信息查询
    @GetMapping("/Adxinxi")
    public ModelAndView adXinxi() {
        ModelAndView mv = new ModelAndView();
//        List<Collect> stus = iSignUpService.findStudent();
        List<Add> adds = iAdminService.adXinxi();
        mv.addObject("stus", adds);
        mv.setViewName("emp/list");
        return mv;
    }


    @GetMapping("/file")
    public String adFile(HttpServletRequest request) {
        return "emp/file";
    }

    @GetMapping("/fileload")
    public String adFileLoad(Model model) {
        iAdminService.adFileLoad();
        model.addAttribute("suc","文件导出成功");
        return "emp/file";
    }

    @GetMapping("/cx")
    public String xc() {
        return "emp/listcx";
    }

//    @GetMapping("/zh")
//    public String zh(){
//        return "emp/listcx";
//    }

    @PostMapping("/cxId")
    public String xcId(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
//        Collect stu = iSignUpService.selectStudentById(id);
        Add stuAdd=iAdminService.xcId(id);
//        System.out.println(stuAdd.getCollect());
        if(stuAdd==null){
            model.addAttribute("Msgnull", "查无此人");
            return "emp/listcx";
        }
        Register register = iRegisterService.registerFindById(id);

        model.addAttribute("stu", stuAdd);
        model.addAttribute("zh", register);
        return "emp/listId";
    }

    @PostMapping("/updatezh")
    public String updateZh(Register register, Model model) {
        Integer update = iRegisterService.registerUpdate(register);
        if (update == 1) {
            model.addAttribute("zhMsg", "修改成功");
        } else {
            model.addAttribute("zhMsg", "修改失败");
        }
        return "emp/listcx";
    }

    @GetMapping("/returnAd")
    public String returnAdmin(){
        return "admin/adminlogin";
    }
}
