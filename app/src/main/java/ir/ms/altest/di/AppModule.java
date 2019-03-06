package ir.ms.altest.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
