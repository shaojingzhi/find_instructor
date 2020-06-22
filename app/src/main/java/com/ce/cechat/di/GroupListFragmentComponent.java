package com.ce.cechat.di;

import com.ce.cechat.ui.grouplist.GroupListFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Subcomponent(modules = AndroidSupportInjectionModule.class)
public interface GroupListFragmentComponent extends AndroidInjector<GroupListFragment> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<GroupListFragment> {

    }

}
