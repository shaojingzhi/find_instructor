package com.ce.cechat.ui.login;

import android.util.Log;
import android.widget.Toast;

import com.ce.cechat.app.BasePresenter;
import com.hyphenate.EMCallBack;

import javax.inject.Inject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.ce.cechat.ui.Values;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 * @author CE Chen
 *
 * Login presenter
 */
public class LoginPresenter extends BasePresenter<ILoginContract.ILoginView, LoginBiz> implements ILoginContract.IPresenter {

    private static final String TAG = "LoginPresenter";

    @Inject
    public LoginPresenter() {
    }

    /**
     * 用户 id 是否是空的
     * @return 是返回true 否返回false
     */
    @Override
    public boolean isEmptyUserId() {
        boolean isEmpty = mBiz.isUserIdEmpty(mView.getUserId());
        if (isEmpty) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mView != null && mView.isActive()) {
                        mView.emptyUserId();
                    }
                }
            });
        }
        return isEmpty;
    }

    /**
     * 密码是否是空的
     * @return 是返回true 否返回false
     */
    @Override
    public boolean isEmptyPassword() {
        boolean isEmpty = mBiz.isPasswordEmpty(mView.getPassword());
        if (isEmpty) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mView != null && mView.isActive()) {
                        mView.emptyPassword();
                    }
                }
            });
        }
        return isEmpty;
    }

    /**
     * 是否是有效的用户 id
     * @return 是返回true 否返回false
     */
    @Override
    public boolean isUserIdValid() {
        boolean isValid = mBiz.isUserIdValid(mView.getUserId());
        if (!isValid) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mView != null && mView.isActive()) {
                        mView.invalidUserId();
                    }
                }
            });
        }
        return isValid;
    }

    /**
     * 登录
     */
    @Override
    public void login() {

        mBiz.onLogin(mView.getUserId(), mView.getPassword(), new EMCallBack() {
            @Override
            public void onSuccess() {
                mBiz.addAccount2Db(mView.getUserId());
                mBiz.initDb(mView.getUserId());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    OkHttpClient client = new OkHttpClient();
                                    RequestBody requestBody = new FormBody.Builder().add("user_id", mView.getUserId()).add("user_password", mView.getPassword()).build();
                                    Request request = new Request.Builder().url(Values.rootIP + "/user/Login").post(requestBody).addHeader("Connection", "application/json").build();
                                    Response response = client.newCall(request).execute();
                                    String responseData = response.body().string();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        //登录成功
                        if (mView != null) {
                            mView.onSuccess();
                        }
                    }
                });
            }

            @Override
            public void onError(final int code, final String error) {
                Log.v(TAG, "code = " + code + ", error = " + error);
                //登录失败
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onFailed(code, error);
                        }
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }
}
