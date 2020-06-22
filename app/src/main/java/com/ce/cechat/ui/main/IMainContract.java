package com.ce.cechat.ui.main;

import com.ce.cechat.app.IBaseContact;
import com.hyphenate.EMCallBack;

public interface IMainContract {

    public interface IMainView extends IBaseContact.IBaseView {

        /**
         * 设置用户名称
         *
         * @param pName 名称
         */
        void setName(String pName);

        /**
         * 退出登录成功
         */
        void logoutSuccess();


        /**
         * 退出登录失败
         */
        void logoutFailed(int code, String error);

    }


    public interface IMainBiz extends IBaseContact.IBaseBiz {

        /**
         * 退出登录
         */
        void logout(EMCallBack callBack);

        /**
         * 获取当前用户
         *
         * @return 当前用户
         */
        String getCurrentUser();

        /**
         * 关闭数据库
         */
        void closeDb();

    }


    public interface IPresenter {

        /**
         * 退出当前账号
         */
        public void logout();

        /**
         * 获取当前账号的名称并设置
         */
        public void setName();

        public void initDb();

    }

}
