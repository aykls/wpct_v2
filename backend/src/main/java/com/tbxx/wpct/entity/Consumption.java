package com.tbxx.wpct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ConsumptionService * @Description
 * @Author ZQB
 * @Date 20:43 2022/10/8
 * @Version 1.0
 **/

@Data
@TableName(value = "tb_consumption")
public class Consumption implements Serializable {
    /**
     * 房屋id
     **/
    @TableId(value = "id", type = IdType.AUTO)
    private int buildId;

    /**
     * 面积核准单价
     */
    private int areaFee;

    /**
     * 面积
     */
    private double area;

    /**
     * 核准面积
     */
    private double limitArea;

    /**
     * 超出面积
     */
    private double overArea;

    /**
     * 超出面积单价
     */
    private int overareaFee;

    /**
     * 物业单价
     */
    private int property;

    /**
     * 物业费
     */
    private int propertyFee;

    /**
     * 押金
     */
    private int deposit;

    /**
     * 公共电梯费
     */
    private int liftFee;

    /**
     * 水费
     */
    private int waterFee;

    /**
     * 电费
     */
    private int electricity;

    /**
     * 气费
     */
    private int gasFee;

    /**
     * 停车费
     */
    private int carFee;

    /**
     * 收回不符合条件疫情减免金额
     */
    private int aFee;

    /**
     * 应收不符合条件租金
     */
    private int bFee;

    /**
     * 应收应退租金
     */
    private int cFee;

    /**
     * 应收应退物业费
     */
    private int dFee;

    /**
     * 优惠
     */
    private int discount;


    /**
     * 月金额
     */
    @TableField(exist = false)
    private int monthCost;


}
