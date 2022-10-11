package com.tbxx.wpct.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.Consumption;
import com.tbxx.wpct.entity.PayInfo;

import java.util.List;

/**
 * @author ZXX
 * @InterfaceName CheckMapper
 * @Description TODO
 * @DATE 2022/10/10 14:02
 */

public interface CheckMapper extends BaseMapper<PayInfo> {

    List<PayInfo> checksList(String month);


}
