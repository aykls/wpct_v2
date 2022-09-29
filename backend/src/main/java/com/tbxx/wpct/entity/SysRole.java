package com.tbxx.wpct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName SysRole * @Description TODO
 * @Author ZQB
 * @Date 20:24 2022/9/29
 * @Version 1.0
 **/
@Data
@TableName("sys_role")
@NoArgsConstructor
@AllArgsConstructor
public class SysRole {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int  id;

    private String roleName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}
