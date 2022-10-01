package com.tbxx.wpct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZXX
 * @ClassName WechatUser
 * @Description TODO
 * @DATE 2022/10/1 16:50
 */

@Data
@TableName("wechat_user")
public class WechatUser implements Serializable {

    /**
     * 主键：用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * openid
     */
    private String openid;

    /**
     * 微信用户昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * city
     */
    private String city;

    /**
     * country
     */
    private String country;
}
