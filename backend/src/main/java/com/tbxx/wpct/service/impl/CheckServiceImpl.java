package com.tbxx.wpct.service.impl;

import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.Consumption;
import com.tbxx.wpct.entity.OrderInfo;
import com.tbxx.wpct.entity.PayInfo;
import com.tbxx.wpct.enums.OrderStatus;
import com.tbxx.wpct.mapper.ConsumptionMapper;
import com.tbxx.wpct.mapper.OrderInfoMapper;
import com.tbxx.wpct.mapper.PayInfoMapper;
import com.tbxx.wpct.service.CheckService;
import com.tbxx.wpct.util.OrderNoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author ZXX
 * @ClassName CheckServiceImpl
 * @Description TODO
 * @DATE 2022/10/9 18:40
 */
@Slf4j
@Service
public class CheckServiceImpl implements CheckService {

    @Resource
    OrderInfoMapper orderInfoMapper;

    @Resource
    ConsumptionMapper consumptionMapper;

    @Resource
    PayInfoMapper payfoMapper;


    @Override
    public Result addCheck(PayInfo payinfo) {
        Consumption consumption = payinfo.getConsumption();
        OrderInfo orderInfo = new OrderInfo();
        String orderNo = OrderNoUtils.getOrderNo();  //生成

        Runnable runnable1 = () -> {
            consumptionMapper.insert(consumption);
        };
        Runnable runnable2 = () -> {
            payfoMapper.insert(payinfo);
        };

        //多线程执行SQL
        runnable1.run();
        runnable2.run();


        orderInfo.setTitle("武平城投缴费业务");   //商品描述
        orderInfo.setOrderNo(orderNo);  //商家订单号
        orderInfo.setVillageName(payinfo.getVillageName());  //小区名
        orderInfo.setBuildNo(payinfo.getBuildNo());         //楼号
        orderInfo.setRoomNo(payinfo.getRoomNo());           //房号
        orderInfo.setCreateTime(LocalDateTime.now());       //创建时间
        orderInfo.setTotalFee(consumption.getMonthCost());  //月缴费
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());//默认 未付款

        orderInfoMapper.insert(orderInfo);


        return Result.ok("添加成功");

    }
}
