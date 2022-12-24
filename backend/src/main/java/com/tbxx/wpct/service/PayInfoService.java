package com.tbxx.wpct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tbxx.wpct.dto.PayInfoVo;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.PayInfo;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * @Author ZXX
 * @InterfaceName PayInfoService
 * @Description TODO
 * @DATE 2022/10/9 19:19
 */

public interface PayInfoService extends IService<PayInfo> {
    List<PayInfo> selectCondition(PayInfoVo vo);

}
