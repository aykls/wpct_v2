package com.tbxx.wpct.entity;

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
public class Consumption implements Serializable{

    private int buildId ;

    /** 面积核准单价 */

    private double areaFee ;

    /** 面积 */
    private double area ;

    /** 核准面积 */
    private double limitArea ;

    /** 超出面积 */
    private double overArea ;

    /** 超出面积单价 */
    private double overareaFee ;

    /** 物业单价 */
    private double property ;

    /** 物业费 */
    private double propertyFee ;

    /** 押金 */
    private double deposit ;

    /** 公共电梯费 */
    private double liftFee ;

    /** 水费 */
    private double waterFee ;

    /** 电费 */
    private double electricity ;

    /** 气费 */
    private double gasFee ;

    /** 停车费 */
    private double carFee ;

    /** 收回不符合条件疫情减免金额 */
    private double aFee ;

    /** 应收不符合条件租金 */
    private double bFee ;

    /** 应收应退租金 */
    private double cFee ;

    /** 应收应退物业费 */
    private double dFee ;

    /** 优惠 */
    private double discount ;

    /** 月金额 */
    private double monthCost ;


}
