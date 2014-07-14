package net.rdrei.android.yummybutter.app;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.rdrei.android.yummybutter.app.dummy.DummyContent;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ItemDetailFragment extends ListFragment {
    public static final String ARG_ITEM_ID = "item_id";
    public static final String TAG = "ITEM_DETAIL";
    private DummyContent.DummyItem mItem;

    @Inject
    RepositoriesAdapter mAdapter;

    private int mMutCounter = 0;

    @InjectView(R.id.item_detail)
    TextView mTextItemDetail;

    @InjectView(R.id.btn_count)
    Button mBtnCount;

    @InjectView(R.id.item_count)
    TextView mTextCounter;

    @InjectView(R.id.avatar)
    ImageView mAvatar;

    @Inject
    SillyUsernameFormatter mFormatter;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new RepositoriesAdapter(getActivity());
        setListAdapter(mAdapter);

        if (mItem != null) {
            buildService().listRepositories(mItem.content)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mAdapter::setItems);

            buildService().getUser(mItem.content)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(user ->
                            Picasso.with(getActivity()).load(user.avatar_url).into(mAvatar));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        if (mItem == null) {
            return rootView;
        }

        ButterKnife.inject(this, rootView);
        ((YummyApplication) getActivity().getApplication()).inject(this);

        mBtnCount.setOnClickListener(v -> {
            mMutCounter += 1;
            updateViews();
        });

        updateViews();

        return rootView;
    }

    public void updateViews() {
        mTextItemDetail.setText(mFormatter.formatName(mItem));
        mTextCounter.setText(String.valueOf(mMutCounter));
    }

    private static GitHubService buildService() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();

        return restAdapter.create(GitHubService.class);
    }

    public interface GitHubService {
        @GET("/users/{user}/repos")
        Observable<List<RepositoriesAdapter.RepositoryEntity>> listRepositories(@Path("user") String user);

        @GET("/users/{user}")
        Observable<User> getUser(@Path("user") String user);
    }

    public class User {
        public String login;
        public String avatar_url;
        public String name;
    }
}
