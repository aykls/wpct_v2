package com.tbxx.wpct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ZXX
 * @ClassName BuildInfo
 * @Description TODO
 * @DATE 2022/10/8 16:54
 */

@Data
@TableName("tb_build_info")
public class BuildInfo {

    /**
     * id自增
     **/
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    /**
     * 房屋类型
     */
    private String houseType;
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
    /**
     * openId
     */
    private String openid;
    /**
     * 与房屋的关系
     */
    private String relation;
}
