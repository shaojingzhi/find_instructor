package com.ce.cechat.ui.login;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    LoginBiz provideLoginBiz() {
        return new LoginBiz();
    }

}
