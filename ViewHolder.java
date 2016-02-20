package csc296.project02;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
public class ViewHolder extends RecyclerView.ViewHolder{

    public ImageView mImageView;

    public TextView mName;

    public DataAccessObject dataAccessObject;

    private User mUser;

    public View mView;

    public ViewHolder(View itemView, Context context) {

        super(itemView);

        mView = itemView;

        mName = (TextView) itemView.findViewById(R.id.TextView_Name);

        dataAccessObject = DataAccessObject.get(context);

        mImageView = (ImageView) itemView.findViewById(R.id.ImageView_Holder);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity c = (AppCompatActivity) v.getContext();
                FragmentManager fm = c.getSupportFragmentManager();

                FavoriteFragment frag = FavoriteFragment.newInstance(mUser);

                frag.show(fm, "FavoriteDialog");

            }
        });

    }


    public void bind(User user){

        mUser = user;

        Log.i(getClass().toString(), "in bind()");

        boolean check = dataAccessObject.checkFave(User.getCurrentUser().getEmail(), user.getEmail());

        if(check == true) {
            mView.setBackgroundColor(Color.rgb(97, 102, 255));
        }

        mName.setText(user.getFullName());

        mImageView.setImageBitmap(getScaledBitmap(user.getProfilePic(), mImageView.getMaxWidth(), mImageView.getMaxHeight()));

    }

    public static Bitmap getScaledBitmap(String path, int width, int height) {

        Log.i("ViewHolder - SN", "in getScaledBitmap()");

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int sampleSize = 1;

        if(srcHeight > height || srcWidth > width) {

            if(srcHeight > height) {
                sampleSize = Math.round(srcHeight/height);
            }
            else {
                sampleSize = Math.round(srcWidth/width);
            }
        }

        BitmapFactory.Options scaledOptions = new BitmapFactory.Options();
        scaledOptions.inSampleSize = sampleSize;

        return BitmapFactory.decodeFile(path, scaledOptions);

    }

}
