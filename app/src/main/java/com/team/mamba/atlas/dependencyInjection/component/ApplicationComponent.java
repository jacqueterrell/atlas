package com.team.mamba.atlas.dependencyInjection.component;


import android.app.Application;
import com.team.mamba.atlas.dependencyInjection.builder.ServiceBuilder;
import com.team.mamba.atlas.dependencyInjection.module.AppModule;
import com.team.mamba.atlas.userInterface.AtlasApplication;
import dagger.BindsInstance;
import dagger.Component;
import javax.inject.Singleton;

        @Singleton
        @Component(modules = {AppModule.class, ServiceBuilder.class})

        public interface ApplicationComponent {

        void inject(AtlasApplication app);

        @Component.Builder
        interface Builder{

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
        }
        }
