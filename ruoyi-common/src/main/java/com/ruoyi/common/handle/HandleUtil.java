package com.ruoyi.common.handle;

import com.ruoyi.common.exception.ServiceException;

/**
 * @Description：
 * @Author：yff
 * @Date：2022/4/26 17:20
 */
public class HandleUtil {

    public static HandleBranchService handleBranch(boolean isHandle) {
        return ((trueHandle, falseHandle) -> {
            if (isHandle) {
                return trueHandle.get();
            } else {
                return falseHandle.get();
            }
        });
    }

    /**
     * 业务层判断抛出异常
     * @param expression
     * @param msg
     * @param code
     */
    public static void serviceExceptionAssert(boolean expression, String msg, Integer code){
        if(expression){
            throw new ServiceException(msg, code);
        }
    }
}
