package net.rdrei.android.yummybutter.app;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

public class RepositoryLoader extends AsyncTaskLoader<List<RepositoriesAdapter.RepositoryEntity>> {
    private final GitHubService mService;
    private final String mUsername;

    public RepositoryLoader(Context context, String username) {
        super(context);

        mUsername = username;
        mService = buildService();
    }

    @Override
    protected void onStartLoading() {
        // Hey, thought about caching?
        // I wonder if Square has a nice library for that ...
        forceLoad();
    }

    private GitHubService buildService() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();

        return restAdapter.create(GitHubService.class);
    }

    @Override
    public List<RepositoriesAdapter.RepositoryEntity> loadInBackground() {
        return mService.listRepositories(mUsername);
    }

    public interface GitHubService {
        @GET("/users/{user}/repos")
        List<RepositoriesAdapter.RepositoryEntity> listRepositories(@Path("user") String user);
    }
}
