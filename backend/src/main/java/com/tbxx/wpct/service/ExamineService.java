package com.tbxx.wpct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.Examine;

/**
 * @author ZXX
 * @InterfaceName ExamineService
 * @Description TODO
 * @DATE 2022/10/10 17:03
 */

public interface ExamineService extends IService<Examine> {
    Result addExamine(Examine examine);
    
    Result userExamineList(String openid);

    Result listExamine();
}
