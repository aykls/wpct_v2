package com.tbxx.wpct.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tbxx.wpct.dto.PayInfoVo;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.PayInfo;
import org.apache.ibatis.annotations.Mapper;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * @Author ZXX
 * @InterfaceName PayInfoMapper
 * @Description TODO
 * @DATE 2022/10/9 19:18
 */

@Mapper
public interface PayInfoMapper extends BaseMapper<PayInfo> {
    List<PayInfo> selectCondition(PayInfoVo vo);

}
