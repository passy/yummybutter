package net.rdrei.android.yummybutter.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import net.rdrei.android.yummybutter.app.dummy.DummyContent;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

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

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        if (mItem == null) {
            return rootView;
        }

        // Much better!
        ButterKnife.inject(this, rootView);

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
}
