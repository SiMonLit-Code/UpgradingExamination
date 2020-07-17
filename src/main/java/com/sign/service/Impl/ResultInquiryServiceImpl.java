package com.sign.service.Impl;

import com.sign.dao.ResultDao;
import com.sign.entity.Achievement;
import com.sign.service.IRegisterService;
import com.sign.service.IResultInquiryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ResultInquiryServiceImpl implements IResultInquiryService {
    @Resource
    ResultDao resultDao;
    @Override
    public Achievement queryById(String sfzhm) {
        return resultDao.queryById(sfzhm);
    }
}
