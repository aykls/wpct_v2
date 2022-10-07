package com.tbxx.wpct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserRegister * @Description TODO
 * @Author ZQB
 * @Date 13:59 2022/10/7
 * @Version 1.0
 **/
@Data
@TableName(value = "tb_user_register")
public class UserRegister implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //房屋类型
    private  String houseType;
    //小区名
    private  String villageName;
    //楼号
    private  String buildNo;
    //房号
    private  String roomNo;
    //与房屋的关系
    private  String relation;
    //姓名
    private  String name;
    //手机号
    private  String number;
}
