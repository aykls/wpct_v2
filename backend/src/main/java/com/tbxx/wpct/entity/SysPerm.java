package com.tbxx.wpct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName SysPerm * @Description TODO
 * @Author ZQB
 * @Date 12:39 2022/9/30
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@TableName("sys_permission")
public class SysPerm {
    @TableId(type = IdType.AUTO)
    private Integer Id;

    /**
     * 菜单模块类型
     */
    private String menuNode;
    /**
     * 该类型中功能名字
     */
    private String menuName;
    /**
     * 权限码
     */
    private String permission_code;
    /**
     * 权限名字
     */
    private String permission_name;
    /**
     * 必选标识
     */
    private String required_permission;



}
