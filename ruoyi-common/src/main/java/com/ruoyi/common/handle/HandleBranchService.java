package com.ruoyi.common.handle;

import java.util.function.Supplier;

/**
 * @Description：
 * @Author：yff
 * @Date：2022/4/26 17:18
 */
@FunctionalInterface
public interface HandleBranchService<T extends Object> {

    T handleBranch(Supplier<T> trueHandle, Supplier<T> falseHandle);

}
