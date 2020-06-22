package com.ce.cechat.ui.contactlist;

import com.ce.cechat.bean.User;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;


public interface IContactListener {


    void getAllContactSuccess(List<User> pAllContact);


    void getAllContactFailed(HyphenateException e);

}
