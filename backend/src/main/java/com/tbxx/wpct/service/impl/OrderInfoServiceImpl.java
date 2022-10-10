package com.tbxx.wpct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbxx.wpct.entity.OrderInfo;
import com.tbxx.wpct.mapper.OrderInfoMapper;
import com.tbxx.wpct.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ZXX
 * @ClassName OrderInfoServiceImpl
 * @Description TODO
 * @DATE 2022/10/9 17:58
 */

@Slf4j
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
}
