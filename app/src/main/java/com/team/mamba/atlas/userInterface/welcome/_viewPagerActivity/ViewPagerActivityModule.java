package com.team.mamba.atlas.userInterface.welcome._viewPagerActivity;


import dagger.Module;
import dagger.Provides;

@Module
public class ViewPagerActivityModule {

    @Provides
    ViewPagerViewModel providesViewPagerViewModel(){

        return new ViewPagerViewModel();
    }
}
