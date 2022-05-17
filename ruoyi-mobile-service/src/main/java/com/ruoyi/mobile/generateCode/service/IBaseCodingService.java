package com.ruoyi.mobile.generateCode.service;

public interface IBaseCodingService {
    /**
     * @Author Administrator
     * @Description 操作员给序号表插入一条数据，然后将该数据获取出来
     * @Date 18:13 2022/1/26
     * @Param [createId]
     * @return int
     **/
    public long getLastOrderNumByCodeType(String codeType);
}
