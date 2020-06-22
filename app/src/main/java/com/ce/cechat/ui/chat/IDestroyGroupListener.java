package com.ce.cechat.ui.chat;

import com.hyphenate.exceptions.HyphenateException;

public interface IDestroyGroupListener {


    void destroyGroupSuccess();


    void destroyGroupFailed(HyphenateException e);

}
