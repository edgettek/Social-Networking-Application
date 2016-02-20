package csc296.project02;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import csc296.project02.database.DataAccessObject;
import csc296.project02.model.User;

/**
 * Created by KEdgette1 on 11/14/15.
 */
public class ViewHolderFave extends RecyclerView.ViewHolder{

    public ImageView mImageView;

    public TextView mName;

    public User mUser;

    public ViewHolderFave(View itemView) {

            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.TextView_Name);

            mImageView = (ImageView) itemView.findViewById(R.id.ImageView_Holder);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity c = (AppCompatActivity) v.getContext();
                FragmentManager fm = c.getSupportFragmentManager();

                UnfavoriteFragment frag = UnfavoriteFragment.newInstance(mUser);

                frag.show(fm, "FavoriteDialog");

            }
        });
    }


    public void bind(User user){

        mUser = user;

        Log.i(getClass().toString(), "in bind()");

        mName.setText(user.getFullName());

        mImageView.setImageBitmap(ViewHolder.getScaledBitmap(user.getProfilePic(), mImageView.getMaxWidth(), mImageView.getMaxHeight()));

    }

}
