package com.tbxx.wpct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author ZXX
 * @ClassName PayInfo
 * @Description TODO
 * @DATE 2022/10/8 21:16
 */

@Data
@TableName("tb_payinfo")
public class PayInfo implements Serializable {
    /**
     * 缴费id
     */
    private String payinfoId;

    /**
     * 小区名称
     */
    private String villageName;

    /**
     * 楼号
     */
    private String buildNo;

    /**
     * 房号
     */
    private String roomNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String pid;

    /**
     * 电话
     */
    private String number;

    /**
     * 车类
     */
    private String car;

    /**
     * 户口
     */
    private String resident;

    /**
     * 符合条件人数
     */
    private String conditionNumber;

    /**
     * 其中低保人数
     */
    private String personNumber;

    /**
     * 缴费状况
     */
    private String payStatus;

    /**
     * 保障类型
     */
    private String guaranteeType;

    /**
     * 缴费开始时间
     */
    private LocalDateTime payBeginTime;

    /**
     * 缴费结束时间
     */
    private LocalDateTime payEndTime;

    /**
     * 后台人员修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 操作人员记录
     */
    private String updateUser;

    /**
     * 订单号
     */
    private String OrderNo;



    /**
     * 计算
     */
    @TableField(exist = false)
    private Consumption consumption;
}
