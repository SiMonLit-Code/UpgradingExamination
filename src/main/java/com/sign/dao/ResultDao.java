package com.sign.dao;

import com.sign.entity.Achievement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ResultDao {
    @Select("select * from result where sfzhm=#{sfzhm}")
    Achievement queryById(String sfzhm);
}
