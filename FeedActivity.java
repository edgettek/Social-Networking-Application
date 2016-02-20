package csc296.project02;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import csc296.project02.database.DataAccessObject;
import csc296.project02.model.FeedItem;
import csc296.project02.model.User;

public class FeedActivity extends AppCompatActivity {

    public Button mPost, mPic;
    public EditText mStatusUpdate;

    private RecyclerView mRecyclerView;

    public final String KEY_TEXT = "TEXTPOST";
    public final String KEY_PATH = "PICTUREPATH";

    File mPicFile;

    public DataAccessObject dataAccessObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        mPic = (Button) findViewById(R.id.Button_FeedAddPic);
        mPost = (Button) findViewById(R.id.Button_PostUpdate);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView_Feed);

        dataAccessObject = DataAccessObject.get(getApplicationContext());

        mStatusUpdate = (EditText) findViewById(R.id.EditText_StatusUpdate);

        if(savedInstanceState != null) {

            mStatusUpdate.setText(savedInstanceState.getString(KEY_TEXT));

            File PicDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            String path = savedInstanceState.getString(KEY_PATH);

            if(path != null) {

                mPicFile = new File(PicDirectory, path);
            }

        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Log.d(getClass().toString(), User.getCurrentUser().getEmail());

        List<FeedItem> feedItems = dataAccessObject.getFeedItems(User.getCurrentUser());
        mRecyclerView.setAdapter(new FeedAdapter(feedItems));

        //Take a picture
        mPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        //Post an update
        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = User.getCurrentUser().getEmail();

                String text = mStatusUpdate.getText().toString();

                String path = null;

                if(mPicFile != null) {
                    path = mPicFile.getAbsolutePath();
                }

                if(text == null && path == null) {
                    Toast.makeText(FeedActivity.this, R.string.Feed_Warning, Toast.LENGTH_LONG).show();
                }
                else {

                    Date date = new Date();

                    FeedItem item = new FeedItem();

                    item.setDatePosted(date);
                    item.setEmail(email);
                    item.setPicture(path);
                    item.setText(text);

                    dataAccessObject.insertFeedItem(item);

                    Toast.makeText(getApplicationContext(), R.string.Feed_Congrats, Toast.LENGTH_LONG).show();

                    mStatusUpdate.setText(null);
                }

            }
        });

    }

    // ***** MENU METHODS *****

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feed, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean success = false;

        if(item.getItemId() == R.id.menu_socialNet_FEED) {
            Intent intent = new Intent(FeedActivity.this, SocialNetActivity.class);

            startActivity(intent);

            success = true;
        }
        else {

            if(item.getItemId() == R.id.menu_profileCreation_FEED) {

                Intent intent = new Intent(FeedActivity.this, ProfileCreationActivity.class);

                startActivity(intent);

                success = true;

            }
            else {

                if(item.getItemId() == R.id.menu_logout_FEED) {

                    User.setCurrentUser(new User());

                    Intent intent = new Intent(FeedActivity.this, LoginActivity.class);

                    startActivity(intent);

                    success = true;

                }

            }

        }
        return success;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(KEY_TEXT, mStatusUpdate.getText().toString());

        if(mPicFile != null) {
            outState.putString(KEY_PATH, mPicFile.getName());
        }

    }

}
