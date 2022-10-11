package com.tbxx.wpct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbxx.wpct.entity.OrderInfo;
import com.tbxx.wpct.enums.OrderStatus;
import com.tbxx.wpct.mapper.OrderInfoMapper;
import com.tbxx.wpct.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    /**
     * 根据订单号获取订单状态
     * @param orderNo
     * @return
     */
    @Override
    public String getOrderStatus(String orderNo) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        OrderInfo orderInfo = baseMapper.selectOne(queryWrapper);
        if(orderInfo == null){
            return null;
        }
        return orderInfo.getOrderStatus();
    }


    /**
     * 用户关单 更新订单状态
     * @param orderNo
     */
    @Override
    public void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) {
        log.info("更新订单状态为====>{}", orderStatus.getType());

        //查询条件
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>().eq("order_no", orderNo);
        //订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus(orderStatus.getType());

        baseMapper.update(orderInfo, queryWrapper);
    }


    /**
     * 根据订单号查询订单
     * @param orderNo 订单号
     * @return 订单信息
     */
    @Override
    public OrderInfo getOrderByOrderNo(String orderNo) {
        return query().eq("order_no", orderNo).one();
    }
}
