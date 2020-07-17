package com.sign.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.sign.dao.AdminDao;
import com.sign.entity.*;
import com.sign.service.*;
import com.sign.utils.FilePathUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements IAdminService {

    @Resource
    AdminDao adminDao;
    @Resource
    ISignUpService iSignUpService;
    @Resource
    IDMService idmService;
    @Resource
    private WxPayOrderService payOrderService;
    @Resource
    IRegisterService iRegisterService;

    @Transactional
    @Override
    public Integer updateAdmin(String username,String password) {
        Admin admin = new Admin(username,password);
        return adminDao.updateAdmin(admin);
    }

    @Override
    public boolean findAdmin(String username,String password) {
        Admin admin=adminDao.findAdmin();
        if (admin.getAcc().equals(username) && admin.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Add> adXinxi() {
        List<Add> addStus=iSignUpService.associationFind();
        List<MZDM> mzdms = idmService.findMZDM();
        List<ZZMMDM> zzmmdms = idmService.findZZMMDM();

        for (Add stu :
                addStus) {
            if(stu.getCollect()!=null){
                for (MZDM mzdm :
                        mzdms) {
                    if (stu.getCollect().getNation().equals(mzdm.getMzdm())) {
                        stu.getCollect().setNation(mzdm.getMzmc());
                        break;
                    }
                }
                for (ZZMMDM zzmmdm :
                        zzmmdms) {
                    if (stu.getCollect().getPc().equals(zzmmdm.getZzmmdm())) {
                        stu.getCollect().setPc(zzmmdm.getZzmmmc());
                        break;
                    }
                }
            }
            //System.out.println(stu);
        }
        return addStus;
    }

    @Override
    public void adFileLoad() {
//        String fileName = request.getParameter("fpath");
//        System.out.println(fileName);
        String filepath="\\static\\files\\";
        List<Collect> collects = iSignUpService.findStudent();
        List<Add> addList=iSignUpService.associationFind();
        Map<String,String> map = null ;

        for (Collect collect:
                collects) {
            try {
                map = payOrderService.orderQuery(collect.getDid()) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
            collect.setPay(map.get("trade_state_desc"));
        }
        for (Add add:
                addList) {
            for (Collect collect:
                    collects){
                if (add.getDid().equals(collect.getDid())){
                    collect.setCard(add.getCard());
                    collect.setExam(add.getExam());
                    collect.setSoldier(add.getSoldier());
                }
            }
        }

//        String fileName="C:\\nclg.xls";
        EasyExcel.write(FilePathUtils.getFileName(filepath)+"nclg.xls", CollectExcl.class).sheet("南昌理工考生信息").doWrite(collects);
        //        try {
//
//            FileUtils.fileUnload("nclg.xls",response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public Add xcId(String id) {

//        Collect stu = iSignUpService.selectStudentById(id);
        Add stuAdd=iSignUpService.associationSecFind(id);
//        System.out.println(stuAdd.getCollect());

        List<MZDM> mzdms = idmService.findMZDM();
        List<ZZMMDM> zzmmdms = idmService.findZZMMDM();

        for (MZDM mzdm :
                mzdms) {
            if (stuAdd.getCollect().getNation().toString().equals(mzdm.getMzdm())) {
                stuAdd.getCollect().setNation(mzdm.getMzmc());
                break;
            }
        }
        for (ZZMMDM zzmmdm :
                zzmmdms) {
            if (stuAdd.getCollect().getPc().toString().equals(zzmmdm.getZzmmdm())) {
                stuAdd.getCollect().setPc(zzmmdm.getZzmmmc());
                break;
            }
        }
        return stuAdd;
    }
}
