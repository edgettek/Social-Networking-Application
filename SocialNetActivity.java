package csc296.project02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import csc296.project02.database.DataAccessObject;
import csc296.project02.model.User;

public class SocialNetActivity extends AppCompatActivity {

    public RecyclerView mRVAll, mRVFaves;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_net);

        mRVAll = (RecyclerView) findViewById(R.id.RecyclerView_All);
        mRVFaves = (RecyclerView) findViewById(R.id.RecyclerView_Faves);
        mButton = (Button) findViewById(R.id.Button_RefreshFaves);

        mRVAll.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRVFaves.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        final DataAccessObject dataAccessObject = DataAccessObject.get(getApplicationContext());

        List<User> userListAll = dataAccessObject.getUsers(User.getCurrentUser());
        mRVAll.setAdapter(new UserAdapter(userListAll));

        List<User> faves = dataAccessObject.getAllFaves(User.getCurrentUser());
        mRVFaves.setAdapter(new FaveAdapter(faves));

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(getClass().toString(), "Refreshing RecyclerViews!");

                mRVAll = (RecyclerView) findViewById(R.id.RecyclerView_All);
                List<User> userListAll = dataAccessObject.getUsers(User.getCurrentUser());
                mRVAll.setAdapter(new UserAdapter(userListAll));

                RecyclerView mRVFaves = (RecyclerView) findViewById(R.id.RecyclerView_Faves);
                List<User> faves = dataAccessObject.getAllFaves(User.getCurrentUser());
                mRVFaves.setAdapter(new FaveAdapter(faves));

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_social_networking, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean success = false;

        if(item.getItemId() == R.id.menu_feed_SN) {
            Intent intent = new Intent(SocialNetActivity.this, FeedActivity.class);

            startActivity(intent);

            success = true;
        }
        else {

            if(item.getItemId() == R.id.menu_profileCreation_SN) {

                Intent intent = new Intent(SocialNetActivity.this, ProfileCreationActivity.class);

                startActivity(intent);

                success = true;

            }
            else {

                if(item.getItemId() == R.id.menu_logout_SN) {

                    User.setCurrentUser(new User());

                    Intent intent = new Intent(SocialNetActivity.this, LoginActivity.class);

                    startActivity(intent);

                    success = true;

                }

            }

        }
        return success;
    }
}
