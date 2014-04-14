package net.rdrei.android.yummybutter.app;

import android.content.Context;

import javax.inject.Inject;

public class RepositoryLoaderFactory {
    @Inject
    Context mContext;

    @Inject
    public RepositoryLoaderFactory(final Context context) {
        mContext = context;
    }

    public RepositoryLoader create(final String username) {
        return new RepositoryLoader(mContext, username);
    }
}
