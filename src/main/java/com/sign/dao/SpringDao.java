package com.sign.dao;

import com.sign.entity.Spring;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * (Spring)表数据库访问层
 *
 * @author czz
 * @since 2020-06-09 13:54:07
 */
@Mapper
public interface SpringDao {


    /**
     * 通过ID查询单条数据
     *
     * @param  did
     * @return 实例对象
     */
    @Select("select DISTINCT * from spring where did=#{did}")
    Spring queryById(String did);

    /**
     * 更新支付状态
     * @param spring
     */
    @Update("update spring set pay=#{pay} where did=#{did}")
    void updatePay(Spring spring);


}