package com.tbxx.wpct.service;


import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.PayInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ZXX
 * @InterfaceName CheckService
 * @Description TODO
 * @DATE 2022/10/9 18:39
 */

public interface CheckService  {

    @Transactional
    Result addCheck(PayInfo payinfo);
}
