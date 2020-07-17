package com.sign.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Spring)实体类
 *
 * @author czz
 * @since 2020-06-09 13:54:07
 */


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Spring implements Serializable {
//    private static final long serialVersionUID = 910504581118860451L;
    
    private String sid;
    
    private String kid;
    
    private String sname;
    
    private String gender;
    
    private String gmajor;
    
    private String did;
    //课程1
    private String pid1;
    
    private String seat1;
    //课程2
    private String pid2;

    private String seat2;
    
    private String cid1;
    
    private String cid2;
    
    private String cid3;
    //考试地点
    private String gp;
    //支付状态
    private String pay;

}