package com.tbxx.wpct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbxx.wpct.entity.BuildInfo;
import com.tbxx.wpct.mapper.BuildInfoMapper;
import com.tbxx.wpct.service.BuildInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ZXX
 * @ClassName BuildInfoServiceImpl
 * @Description TODO
 * @DATE 2022/10/8 17:26
 */

@Slf4j
@Service
public class BuildInfoServiceImpl extends ServiceImpl<BuildInfoMapper, BuildInfo> implements BuildInfoService {
}
