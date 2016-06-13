package williamhester.me.breadit2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import static butterknife.ButterKnife.findById;

/**
 * Created by william on 6/12/16.
 */
public abstract class ContentFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    protected static class SubmissionViewHolder extends RecyclerView.ViewHolder {
        protected final TextView mPointsTextView;
        protected final TextView mNsfwTextView;
        protected final TextView mTitleTextView;
        protected final TextView mMetadata1TextView;
        protected final TextView mMetadata2TextView;

        public SubmissionViewHolder(View itemView) {
            super(itemView);

            mPointsTextView = findById(itemView, R.id.points);
            mNsfwTextView = findById(itemView, R.id.nsfw);
            mTitleTextView = findById(itemView, R.id.title);
            mMetadata1TextView = findById(itemView, R.id.metadata_1);
            mMetadata2TextView = findById(itemView, R.id.metadata_2);
        }
    }

    protected static class SubmissionImageViewHolder extends SubmissionViewHolder {
        protected final ImageView mImage;

        public SubmissionImageViewHolder(View itemView) {
            super(itemView);

            mImage = findById(itemView, R.id.image);
        }
    }

    protected static class SubmissionLinkViewHolder extends SubmissionViewHolder {
        protected final TextView mLink;

        public SubmissionLinkViewHolder(View itemView) {
            super(itemView);

            mLink = findById(itemView, R.id.link_text);
        }
    }

}
