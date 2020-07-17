package com.sign.service;

import com.sign.entity.Add;
import com.sign.entity.Admin;
import com.sign.entity.Register;
import org.springframework.ui.Model;

import java.util.List;

public interface IAdminService {
     Integer updateAdmin(String username,String password);

     boolean findAdmin(String username,String password);

     List<Add> adXinxi();

     void adFileLoad();

     Add xcId(String id);
}
