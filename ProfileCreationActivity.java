package csc296.project02;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import csc296.project02.database.DataAccessObject;
import csc296.project02.model.User;

public class ProfileCreationActivity extends AppCompatActivity {

    public Button mButtonSubmit, mButtonTakePic;

    public EditText mFullName, mBirthday, mHometown, mBio;

    public DataAccessObject dataAccessObject;

    private final String KEY_NAME = "FULLNAME";
    private final String KEY_BIRTHDAY = "BIRTHDAY";
    private final String KEY_HOMETOWN = "HOMETOWN";
    private final String KEY_BIO = "BIO";
    private final String KEY_FILE = "PICFILE";

    File mPicFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        dataAccessObject = DataAccessObject.get(getApplicationContext());

        mFullName = (EditText) findViewById(R.id.EditText_FullName);
        mBirthday = (EditText) findViewById(R.id.EditText_Birthday);
        mHometown = (EditText) findViewById(R.id.EditText_Hometown);
        mBio = (EditText) findViewById(R.id.EditText_Bio);

        if(savedInstanceState != null) {

            mFullName.setText(savedInstanceState.getString(KEY_NAME));
            mBirthday.setText(savedInstanceState.getString(KEY_BIRTHDAY));
            mHometown.setText(savedInstanceState.getString(KEY_HOMETOWN));
            mBio.setText(savedInstanceState.getString(KEY_BIO));

            String path = savedInstanceState.getString(KEY_FILE);

            File PicDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            if(path != null) {

                mPicFile = new File(PicDirectory, path);
            }

        }


        mButtonSubmit = (Button) findViewById(R.id.Button_Submit);
        mButtonTakePic = (Button) findViewById(R.id.Button_TakePicture);

        //Submitting new profile
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(getClass().toString(), "Creating new Profile!");

                User user = User.getCurrentUser();

                //user already exists in system then update
                if(user.getProfilePic() != null) {

                    dataAccessObject.deleteUser(user.getEmail());

                }

                user.setFullName(mFullName.getText().toString());

                String date = mBirthday.getText().toString();

                date = date.replace(" ", "-");

                SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
                Date birthday = null;
                try {
                    birthday = f.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long longDate = birthday.getTime();

                user.setBirthdate(new Date(longDate));
                user.setHomeTown(mHometown.getText().toString());
                user.setBio(mBio.getText().toString());
                user.setProfilePic(mPicFile.getAbsolutePath());

                User.setCurrentUser(user);

                dataAccessObject.insertUser(user);

                Intent intent = new Intent(ProfileCreationActivity.this, SocialNetActivity.class);

                startActivity(intent);

            }
        });

        mButtonTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(getClass().toString(), "Taking Picture");

                Intent intent = new Intent();

                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                String fileName = "IMG_" + UUID.randomUUID().toString() + ".jpg";

                File PicDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                mPicFile = new File(PicDirectory, fileName);

                Uri photoURI = Uri.fromFile(mPicFile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(intent, 0);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile_creation_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean success = false;

        if(item.getItemId() == R.id.menu_socialNet) {

            Intent intent = new Intent(ProfileCreationActivity.this, SocialNetActivity.class);

            startActivity(intent);

            success = true;

        }
        else {

            if(item.getItemId() == R.id.menu_feed) {

                Intent intent = new Intent(ProfileCreationActivity.this, FeedActivity.class);

                startActivity(intent);

                success = true;


            }
            else {

                if(item.getItemId() == R.id.menu_logout) {

                    User.setCurrentUser(new User());

                    Intent intent = new Intent(ProfileCreationActivity.this, LoginActivity.class);

                    startActivity(intent);

                    success = true;

                }

            }

        }

        return success;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(KEY_NAME, mFullName.getText().toString());
        outState.putString(KEY_HOMETOWN, mHometown.getText().toString());
        outState.putString(KEY_BIRTHDAY, mBirthday.getText().toString());
        outState.putString(KEY_BIO, mBio.getText().toString());

        if(mPicFile != null) {
            outState.putString(KEY_FILE, mPicFile.getName());
        }

    }
}
