package com.ce.cechat.ui.login;

import com.hyphenate.exceptions.HyphenateException;

public interface ISignUpListener {

    /**
     * 注册成功
     */
    void onSuccess();

    /**
     * 注册失败
     *
     * @param e 异常
     */
    void onFailed(HyphenateException e);

}
