package com.sign.controller;

import com.sign.entity.Add;
import com.sign.entity.Spring;
import com.sign.service.ISignUpService;
import com.sign.service.SpringService;
import com.sign.service.WxPayOrderService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * (Spring)表控制层
 *
 * @author czz
 * @since 2020-06-09 13:54:07
 */
@Controller
public class SpringController {
    /**
     * 服务对象
     */
    @Resource
    private SpringService springService;

    @Resource
    private ISignUpService iSignUpService;

    @Resource
    private WxPayOrderService payOrderService ;

    /**
     * 通过主键查询单条数据
     *
     *
     * @return 单条数据
     */
    @GetMapping("/AdTicket")
    public String selectOne(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Add addstudent = iSignUpService.associationSecFind((String) session.getAttribute("id"));
        if (addstudent == null) {
            System.out.println("请先报名");
            return "emp/updatefalse";
        } else {
            Spring spring = springService.queryById(addstudent.getDid());
            if (("线下支付成功").equals(spring.getPay())){
                return "read";
            }
            Map<String,String> map = null ;
            try {
                map = payOrderService.orderQuery(spring.getDid());
            } catch (Exception e) {
                e.printStackTrace();
            }
            spring.setPay(map.get("trade_state_desc"));
//            System.out.println(map.get("trade_state_desc"));
//            System.out.println(spring);
            springService.updatePay(spring);
            if(!("支付成功").equals(spring.getPay())){
                System.out.println("请先支付，如有问题，请拨打咨询电话");
                model.addAttribute("errorMsg","请先支付，如有问题，请拨打咨询电话:0791-82137568");
                return "dashboard";
            }
            return "read";
        }
    }

    @PostMapping("/ticket")
    public String adTicketPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String did = (String) session.getAttribute("id");
        String pName = did + ".jpg";
        model.addAttribute("zp", "/imagesSFZ/" + pName);
        model.addAttribute("stu", springService.queryById(did));
        return "AdTicket";
    }

}