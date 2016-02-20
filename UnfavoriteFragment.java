package csc296.project02;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import csc296.project02.database.DataAccessObject;
import csc296.project02.model.User;

/**
 * Created by KEdgette1 on 11/14/15.
 */
public class UnfavoriteFragment extends DialogFragment {

    private static final String KEY_PIC = "PIC";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_BIRTHDAY = "BIRTHDAY";
    private static final String KEY_HOMETOWN = "HOMETOWN";
    private static final String KEY_BIO = "BIO";

    private TextView mName, mBio, mHomeTown, mBirthday;
    private ImageView mPic;

    private DataAccessObject dataAccessObject;


    public UnfavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_favorite, null);

        Log.i(getClass().toString(), "Creating new Unfavorite Dialog");

        mName = (TextView) view.findViewById(R.id.TextView_Frag_Name);
        mBio = (TextView) view.findViewById(R.id.TextView_Frag_Bio);
        mHomeTown = (TextView) view.findViewById(R.id.TextView_Frag_Hometown);
        mBirthday = (TextView) view.findViewById(R.id.TextView_Frag_Birthday);

        mPic = (ImageView) view.findViewById(R.id.ImageView_FragDialog);

        dataAccessObject = DataAccessObject.get(getContext());

        Bundle bundle = getArguments();

        String name = bundle.getString(KEY_NAME);
        final String path = bundle.getString(KEY_PIC);
        String bio = bundle.getString(KEY_BIO);
        String hometown = bundle.getString(KEY_HOMETOWN);
        Long birthday = bundle.getLong(KEY_BIRTHDAY);

        Date bday = new Date(birthday);

        String bdayString = bday.toString();

        String [] split = bdayString.split(" ");

        String finalBDay = split[1] + " " + split[2] + " " + split[5];

        mName.setText(name);
        mBio.setText(bio);
        mHomeTown.setText(hometown);
        mBirthday.setText(finalBDay);

        mPic.setImageBitmap(ViewHolder.getScaledBitmap(path, mPic.getMaxWidth(), mPic.getMaxHeight()));

        return new AlertDialog.Builder(getActivity())
                .setView(view).setTitle(R.string.DialogTitle).setPositiveButton(R.string.UnfavoriteUser,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String faveEmail = dataAccessObject.getFaveEmail(path);

                                dataAccessObject.deleteFave(User.getCurrentUser().getEmail(), faveEmail);
                            }
                        }
                ).setNegativeButton(R.string.Decline, null)
                .create();


    }


    public static UnfavoriteFragment newInstance(User user) {

        UnfavoriteFragment frag = new UnfavoriteFragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY_NAME, user.getFullName());
        bundle.putString(KEY_PIC, user.getProfilePic());
        bundle.putString(KEY_HOMETOWN, user.getHomeTown());
        bundle.putLong(KEY_BIRTHDAY, user.getBirthdate().getTime());
        bundle.putString(KEY_BIO, user.getBio());

        frag.setArguments(bundle);

        return frag;
    }


}
