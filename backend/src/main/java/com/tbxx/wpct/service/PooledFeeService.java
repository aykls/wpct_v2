package com.tbxx.wpct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.PooledFee;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ZXX
 * @InterfaceName PooledFeeService
 * @Description TODO
 * @DATE 2022/10/10 19:31
 */

public interface PooledFeeService extends IService<PooledFee> {
    @Transactional
    Result addpooled(PooledFee pooledFee, String control);

    @Transactional
    Result pooledList(int pageNum);

    @Transactional
    Result removePooled(int id);

    @Transactional
    Result updatepooled(PooledFee pooledFee,String control);
}
