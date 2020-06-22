package com.ce.cechat.ui.login;

import dagger.Module;
import dagger.Provides;

@Module
public class SignUpModule {

    @Provides
    SignUpBiz provideSignUpBiz() {
        return new SignUpBiz();
    }

}
