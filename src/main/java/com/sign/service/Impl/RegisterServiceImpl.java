package com.sign.service.Impl;

import com.sign.dao.RegisterDao;
import com.sign.entity.Register;
import com.sign.service.IRegisterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RegisterServiceImpl implements IRegisterService {
    @Resource
    RegisterDao registerDao;
    @Override
    public boolean registerInsert(Register register) {
        return registerDao.registerInsert(register);
    }

    @Override
    public Integer registerFind(Register register) {
        return registerDao.registerFind(register);
    }

    @Override
    public Integer registerFindAc(Register register) {
        return registerDao.registerFindAc(register);
    }

    @Override
    public List<Register> registerFindAll() {
        return registerDao.registerFindAll();
    }

    @Override
    public Register registerFindById(String account) {
        return registerDao.registerFindById(account);
    }

    @Override
    public Integer registerUpdate(Register register) {
        return registerDao.registerFindUpdate(register);
    }


}
