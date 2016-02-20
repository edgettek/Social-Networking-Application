package csc296.project02;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import csc296.project02.model.User;

/**
 * Created by KEdgette1 on 11/14/15.
 */
public class UserAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<User> userList;

    public UserAdapter(List<User> list) {
        userList = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.view_holder_sn, parent, false);

        ViewHolder holder = new ViewHolder(view, parent.getContext());

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.i(getClass().toString(), "in onBindViewHolder");
        User user = userList.get(position);

        holder.bind(user);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
