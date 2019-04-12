package com.team.mamba.atlas.dependencyInjection.builder;

import com.team.mamba.atlas.service.AddContactsCrmService;
import com.team.mamba.atlas.service.IncompleteProfileInfoService;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceBuilder {

    @ContributesAndroidInjector
    abstract AddContactsCrmService bindAddContactsCrmService();

    @ContributesAndroidInjector
    abstract IncompleteProfileInfoService bindIncompleteProfileInfoService();
}
