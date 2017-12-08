package com.toiletCat.service.impl;

import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.template.ExecTemplate;
import org.slf4j.Logger;

public abstract class BaseServiceImpl {

    /**
     * 公共方法
     * @param callback 回调函数
     * @param method 方法
     * @param params 参数
     * @return
     */
    protected CommonResult exec(Callback callback, String method, String params) {
        return ExecTemplate.exec(callback, getLogger(), method, params);
    }

    protected abstract Logger getLogger();

}
