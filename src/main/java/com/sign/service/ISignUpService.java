package com.sign.service;

import com.sign.entity.Add;
import com.sign.entity.Collect;
import com.sign.vo.CollectVo;
import org.apache.ibatis.annotations.Insert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISignUpService {
    public boolean insertStudent(CollectVo collect);
    public boolean insertSecStudent(CollectVo collectVo);

    public List<Collect> findStudent();
    public Collect selectStudentById(String did);
    public Integer updateStudent(CollectVo collect);
    public Integer updateSecStudent(CollectVo collect);
    public List<Collect> findStudentdId();
    public List<Add> associationFind();
    public Add associationSecFind(String did);
    String upload(MultipartFile file,String id);
}
