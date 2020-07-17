package com.sign.controller;

import com.sign.entity.Register;
import com.sign.service.IAdminService;
import com.sign.service.IRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 */
@Controller
public class LoginController {
    @Resource
    IRegisterService iRegisterService;


    @Resource
    IAdminService iAdminService;


    @PostMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
//        session.setMaxInactiveInterval(30);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (iAdminService.findAdmin(username,password)) {
            //密码正确，设置session
            session.setAttribute("loginMsg", "success");
            mv.setViewName("redirect:/main");
        } else {
            //密码错误返回登录页面
            mv.addObject("msg", "用户名或密码有误");
            mv.setViewName("index");
        }
        return mv;
    }

    @GetMapping("/exit")
    public String exit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginMsg");
        session.removeAttribute("loginAdmMsg");
        return "/index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    //学生登陆
    @PostMapping("/loginStu")
    public ModelAndView loginStudent(Register register,HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
//        session.setMaxInactiveInterval(30);
        if (iRegisterService.registerFind(register) == 1) {
            //密码正确，设置session
            String id=register.getAccount();
            session.setAttribute("id",id);
            session.setAttribute("loginMsg", "success") ;
            mv.setViewName("redirect:/main");
        } else {
            //密码错误返回登录页面
            mv.addObject("msg", "身份证或密码有误");
            mv.setViewName("index");
        }
        return mv;
    }

    //注册
    @PostMapping("/register")
    public String registerInf(Register register, Model model,HttpServletRequest request) {
//        System.out.println("----------注册请求：----------");
        String password=request.getParameter("password");
        if (!password.equals(register.getPwd())){
            model.addAttribute("pwdmsgnum", "密码输入不一致");
            return "register";
        }
        if (password.length()<6){
            model.addAttribute("pwdmsglen", "密码长度6-12位");
            return "register";
        }
//        session.setMaxInactiveInterval(30);
        if (register.getAccount().length()!= 18) {
            model.addAttribute("acountmsgnum", "身份证位数有误");
            return "register";
        }
//        System.out.println(register);
//        System.out.println(iRegisterService.registerFind(register));
        if (iRegisterService.registerFindAc(register) == 0) {
            iRegisterService.registerInsert(register);
            return "index";
        } else {
            model.addAttribute("acountmsgcf", "身份证已被注册，有问题请联系管理员");
//            System.out.println("----------注册请求结束：----------");
            return "register";
        }
    }
    public int LengthNum(long num) {
        int count = 0; //计数
        while (num >= 1) {
            num /= 10;
            count++;
        }
        return count;
    }
}


