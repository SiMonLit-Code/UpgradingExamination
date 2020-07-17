package com.sign.service.Impl;

import com.sign.dao.SignUpDao;
import com.sign.entity.Add;
import com.sign.entity.Collect;
import com.sign.function.FunctionApplication;
import com.sign.service.ISignUpService;
import com.sign.utils.FilePathUtils;
import com.sign.vo.CollectVo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service

public class SignUpServiceImpl implements ISignUpService {
    @Resource
    SignUpDao signUpDao;

    @Resource
    FunctionApplication functionApplication;


    @Override
    public boolean insertStudent(CollectVo collect) {
        boolean flag =false ;
        synchronized (this) {
            //检测名字中的空格
            collect.setSname(collect.getSname().replace(" ",""));
//            System.out.println(collect.getGid());
                if (!functionApplication.toStringGid(collect.getGid()) || !functionApplication.posLength(collect.getPos().toString()) ||
                !functionApplication.posLength(collect.getNid().toString()) || !functionApplication.posLength(collect.getParent())  ||
                        !functionApplication.posLength(collect.getPerson())){
                    return false;
                }
                flag =  signUpDao.insertStudent(collect);
            }
//        return signUpDao.insertStudent(collect);
//        return true;
        return flag ;

    }

    @Override
    public boolean insertSecStudent(CollectVo collectVo) {
        return signUpDao.insertSecStudent(collectVo);
    }

    @Override
    public List<Collect> findStudent() {
        return signUpDao.findStudent();
    }

    @Override
    public Collect selectStudentById(String dId) {
        return signUpDao.selectStudentById(dId);
    }

    @Override
    public Integer updateStudent(CollectVo collect) {
        collect.setSname(collect.getSname().replace(" ",""));
        if (!functionApplication.toStringGid(collect.getGid()) || !functionApplication.posLength(collect.getPos().toString()) ||
                !functionApplication.posLength(collect.getNid().toString()) || !functionApplication.posLength(collect.getParent())  ||
                !functionApplication.posLength(collect.getPerson())){
            return 0;
        }
        return signUpDao.updateStudent(collect);
    }

    @Transactional
    @Override
    public Integer updateSecStudent(CollectVo collect) {
        return signUpDao.updateSecStudent(collect);
    }

    @Override
    public List<Collect> findStudentdId() {
        return signUpDao.findStudentdId();
    }

    @Override
    public List<Add> associationFind() {
        return signUpDao.associationFind();
    }

    @Override
    public Add associationSecFind(String did) {
        return signUpDao.associationSecFind(did);
    }

    @Override
    public String upload(MultipartFile file,String id) {
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.indexOf(".") + 1);
        if ("jpg".equals(ext)||"JPG".equals(ext)){
            // 定义上传文件保存路径//static//sfz//
            String path= FilePathUtils.getFileName("//imagesSFZ//");
//            String path = filePath + "rotPhoto/";
            // 新建文件
            String pName = id + "." + ext;
            File filepath = new File(path, pName);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            try {
                // 写入文件
//                Thumbnails.of((File) file).size(480,640).toFile((File) file);
                file.transferTo(new File(path + File.separator + pName));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return path + File.separator + pName;
        }else {
            return "zpMsg";
        }
    }

}
