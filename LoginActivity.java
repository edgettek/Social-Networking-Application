package csc296.project02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import csc296.project02.database.DataAccessObject;
import csc296.project02.model.User;

public class LoginActivity extends AppCompatActivity {

    public EditText mEditTextEmail, mEditTextPassword;

    public Button mButtonLogin, mButtonNew;

    public DataAccessObject dataAccessObject;

    private final String KEY_EMAIL = "EMAILKEY";
    private final String KEY_PASSWORD = "PASSWORDKEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextEmail = (EditText) findViewById(R.id.EditText_Email);
        mEditTextPassword = (EditText) findViewById(R.id.EditText_Password);

        mButtonLogin = (Button) findViewById(R.id.Button_Login);
        mButtonNew = (Button) findViewById(R.id.Button_NewAccount);

        dataAccessObject = DataAccessObject.get(getApplicationContext());

        if(savedInstanceState != null) {

            mEditTextEmail.setText(savedInstanceState.getString(KEY_EMAIL));
            mEditTextPassword.setText(savedInstanceState.getString(KEY_PASSWORD));

        }

        //Login Button
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(getClass().toString(), "Logging in!");

                String email = mEditTextEmail.getText().toString();
                String password = mEditTextPassword.getText().toString();

                User user = dataAccessObject.getUser(email, password);

                //User exists!
                if(user != null) {

                    User.setCurrentUser(user);

                    Intent intent = new Intent(LoginActivity.this, SocialNetActivity.class);

                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, R.string.Login_ErrorDNE, Toast.LENGTH_LONG).show();
                }


            }
        });

        //Create New Profile Button
        mButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(getClass().toString(), "Creating new profile!");

                String email = mEditTextEmail.getText().toString();
                String password = mEditTextPassword.getText().toString();

                User user = dataAccessObject.getUser(email, password);

                //User does not exist - They must create their profile
                if(user == null) {

                    Intent intent = new Intent(LoginActivity.this, ProfileCreationActivity.class);

                    User currentUser = User.getCurrentUser();

                    currentUser.setEmail(email);
                    currentUser.setPassword(password);

                    startActivity(intent);

                }
                else {
                    Toast.makeText(LoginActivity.this, R.string.Login_ErrorAlreadyExists, Toast.LENGTH_LONG).show();
                }



            }
        });



    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(KEY_EMAIL, mEditTextEmail.getText().toString());
        outState.putString(KEY_PASSWORD, mEditTextPassword.getText().toString());

    }
}
