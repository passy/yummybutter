package net.rdrei.android.yummybutter.app;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.rdrei.android.yummybutter.app.dummy.DummyContent;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ItemDetailFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<RepositoriesAdapter.RepositoryEntity>> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private static final String KEY_USERNAME = "username";
    public static final String TAG = "ITEM_DETAIL";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private RepositoriesAdapter mAdapter;

    private int mMutCounter = 0;

    @InjectView(R.id.item_detail)
    TextView mTextItemDetail;

    @InjectView(R.id.btn_count)
    Button mBtnCount;

    @InjectView(R.id.item_count)
    TextView mTextCounter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
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

        // YUCK! Dependency on the concrete RepositoriesAdapter and the activity!
        mAdapter = new RepositoriesAdapter(getActivity());
        setListAdapter(mAdapter);

        final Bundle loaderBundle = new Bundle();
        loaderBundle.putString(KEY_USERNAME, "passy");

        assert getLoaderManager() != null;
        getLoaderManager().initLoader(0, loaderBundle, this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        if (mItem == null) {
            return rootView;
        }

        ButterKnife.inject(this, rootView);
        ButterKnife.findById(rootView, R.id.btn_count);

        mBtnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMutCounter += 1;
                updateViews();
            }
        });

        updateViews();

        return rootView;
    }

    public void updateViews() {
        mTextItemDetail.setText(mItem.content);
        mTextCounter.setText(String.valueOf(mMutCounter));
    }

    @Override
    public Loader<List<RepositoriesAdapter.RepositoryEntity>> onCreateLoader(int id, Bundle args) {
        final String username = args.getString(KEY_USERNAME);

        // YUCK! Another reference to the activity and a production service!
        return new RepositoryLoader(getActivity(), username);
    }

    @Override
    public void onLoadFinished(Loader<List<RepositoriesAdapter.RepositoryEntity>> loader, List<RepositoriesAdapter.RepositoryEntity> data) {
        Log.d(TAG, "Data received: " + data.toString());
        mAdapter.setItems(data);
    }

    @Override
    public void onLoaderReset(Loader<List<RepositoriesAdapter.RepositoryEntity>> loader) {
        mAdapter.setItems(null);
    }
}
