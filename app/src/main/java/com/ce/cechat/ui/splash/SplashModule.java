package com.ce.cechat.ui.splash;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    SplashBiz provideSplashBiz() {
        return new SplashBiz();
    }

}
