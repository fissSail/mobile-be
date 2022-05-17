package com.ruoyi.mobile.generateCode.service.impl;


import com.ruoyi.common.mobile.entity.BaseCodingDO;

import com.ruoyi.mobile.generateCode.mapper.BaseCodingMapper;
import com.ruoyi.mobile.generateCode.service.IBaseCodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName BaseCodingServiceImpl

 **/
@Service
public class BaseCodingServiceImpl implements IBaseCodingService {
    @Autowired
    private BaseCodingMapper baseCodingMapper;
    // 添加一个排他锁，防止同一个账号同一时间生成编码
    private Lock lock = new ReentrantLock();

    @Override
    public synchronized long getLastOrderNumByCodeType(String codeType) {

        BaseCodingDO baseCoding = new BaseCodingDO();
        baseCoding.setCreateTime(new Date());

        // 获取上次的最大序号
        Long orderNum = baseCodingMapper.getLastOrderNumByCodeType(codeType);
        if (orderNum == null) {
            orderNum = 1L;
        } else {
            // 获取本次的序号
            orderNum++;
        }

        baseCoding.setOrderNum(orderNum);
        baseCoding.setCodeType(codeType);
        // 将本次的序号添加到数据库中
        baseCodingMapper.insert(baseCoding);

        return orderNum;
    }
}
