package net.rdrei.android.yummybutter.app;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module(injects = ItemDetailFragment.class)
public class ApplicationModule {
    private final Context mApplicationContext;

    public ApplicationModule(Application application) {
        mApplicationContext = application;
    }

    @Provides
    public Context provideApplicationContext() {
        return mApplicationContext;
    }
}
