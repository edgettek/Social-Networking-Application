package csc296.project02;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import csc296.project02.model.FeedItem;

/**
 * Created by KEdgette1 on 11/15/15.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedHolder> {

    private List<FeedItem> feedItems;

    public FeedAdapter(List<FeedItem> items) {
        feedItems = items;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.i(getClass().toString(), "in onCreateViewHolder to Feed");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.holder_feed, parent, false);

        FeedHolder holder = new FeedHolder(view, parent.getContext());

        return holder;
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {

        FeedItem item = feedItems.get(position);

        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }
}
