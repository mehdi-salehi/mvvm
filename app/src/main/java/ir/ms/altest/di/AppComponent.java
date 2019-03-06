package ir.ms.altest.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import ir.ms.altest.App;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuildersModule.class,
        AppDbModule.class})
public interface AppComponent {
    void inject(App app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
