package com.ce.cechat.di;

import com.ce.cechat.app.BaseActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = AndroidInjectionModule.class)
public interface BaseActivityComponent extends AndroidInjector<BaseActivity> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<BaseActivity> {

    }

}
