package com.team.mamba.atlas.dependencyInjection.component;


        import android.app.Application;
        import com.team.mamba.atlas.dependencyInjection.builder.ActivityBuilder;
        import com.team.mamba.atlas.dependencyInjection.builder.FragmentBuilder;
        import com.team.mamba.atlas.dependencyInjection.module.AppModule;
        import com.team.mamba.atlas.dependencyInjection.module.DatabaseModule;
        import com.team.mamba.atlas.dependencyInjection.module.NetworkModule;
        import com.team.mamba.atlas.userInterface.AtlasApplication;
        import dagger.BindsInstance;
        import dagger.Component;
        import dagger.android.AndroidInjectionModule;
        import dagger.android.support.AndroidSupportInjectionModule;
        import javax.inject.Singleton;

        @Singleton
        @Component(modules = {AndroidSupportInjectionModule.class, AppModule.class,
        DatabaseModule.class, NetworkModule.class, ActivityBuilder.class, FragmentBuilder.class})

        public interface ApplicationComponent {

        void inject(AtlasApplication app);

        @Component.Builder
        interface Builder{

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
        }
        }
