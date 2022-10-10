package com.tbxx.wpct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ZXX
 * @ClassName OrderInfo
 * @Description TODO
 * @DATE 2022/10/9 17:53
 */

@Data
@TableName("tb_order_info")
public class OrderInfo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private int id; //主键

    private String title;//订单标题

    private String orderNo;//商户订单编号

    private int totalFee;//订单金额(分)

    private String prepayId;//订单连接

    private String orderStatus;//订单状态

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 小区名
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


    @TableField(exist = false)
    private List<String> openid;

    @TableField(exist = false)
    private String relation;


}
