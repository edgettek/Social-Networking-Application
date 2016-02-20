package csc296.project02;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import csc296.project02.database.DataAccessObject;
import csc296.project02.model.FeedItem;

/**
 * Created by KEdgette1 on 11/15/15.
 */
public class FeedHolder extends RecyclerView.ViewHolder{

    public ImageView mImageView;
    public TextView mTextPost, mName;

    public DataAccessObject dataAccessObject;

    private FeedItem mItem;

    public FeedHolder(View itemView, Context context) {
        super(itemView);

        mImageView = (ImageView) itemView.findViewById(R.id.ImageView_Holder);

        dataAccessObject = DataAccessObject.get(context);

        mTextPost = (TextView) itemView.findViewById(R.id.TextView_HolderFeedText);
        mName = (TextView) itemView.findViewById(R.id.TextView_PostHolderName);

    }

    public void bind(FeedItem item) {

        Log.i(getClass().toString(), "binding a feed item");

        mItem = item;

        String name = dataAccessObject.getUser(item.getEmail()).getFullName();

        mName.setText(name);

        if(item.getText() != null) {
            mTextPost.setText(item.getText());
        }

        if(item.getPicture() != null) {
            mImageView.setImageBitmap(ViewHolder.getScaledBitmap(item.getPicture(), mImageView.getMaxWidth(), mImageView.getMaxHeight()));
        }

    }


}
