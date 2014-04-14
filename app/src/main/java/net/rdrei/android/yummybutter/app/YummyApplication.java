package net.rdrei.android.yummybutter.app;

import android.app.Application;

import dagger.ObjectGraph;

public class YummyApplication extends Application {
    private ObjectGraph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mGraph = ObjectGraph.create(getModules());
    }

    protected Object[] getModules() {
        return new Object[] {
                new ApplicationModule(this)
        };
    }

    public void inject(Object obj) {
        mGraph.inject(obj);
    }
}
