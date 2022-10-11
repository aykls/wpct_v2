package com.tbxx.wpct.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tbxx.wpct.entity.Consumption;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ZXX
 * @InterfaceName ConsumptionMapper
 * @Description TODO
 * @DATE 2022/10/8 21:12
 */

public interface ConsumptionMapper extends BaseMapper<Consumption> {


    void updatePooledToZero();

    void  addPooledFee(Integer Fee,String control,String villageName);

    void updatePooledFee(Integer Fee,String control,String villageName);

    @Transactional
    void updateToNew(Integer Fee,String control,String villageName);
}
