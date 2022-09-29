package com.tbxx.wpct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.catalina.User;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZXX
 * @ClassName SysUser
 * @Description TODO
 * @DATE 2022/9/29 18:37
 */

@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    /**
     * 主键：用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer ID;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户对应的角色id
     */
    private Integer roleId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
