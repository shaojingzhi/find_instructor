package com.ce.cechat.ui.groupdetail;

import com.hyphenate.exceptions.HyphenateException;


public interface ILeaveGroupListener {


    void leaveGroupSuccess();


    void leaveGroupFailed(HyphenateException e);

}
