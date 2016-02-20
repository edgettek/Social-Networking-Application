package csc296.project02.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import csc296.project02.model.FeedItem;
import csc296.project02.model.User;

/**
 * Created by KEdgette1 on 11/13/15.
 */
public class DataAccessObject {

    private final SQLiteDatabase database;

    private static DataAccessObject singleton;

    //private constructor for singleton design
    private DataAccessObject(Context context) {

        Log.i(getClass().toString(), "Creating new DataAccessObject");

        database = new Project02OpenHelper(context).getWritableDatabase();
    }

    //Handles singleton design pattern
    public static DataAccessObject get(Context context) {

        Log.i("DataAccessObject", "get DataAccessObject");

        if(singleton == null) {
            singleton = new DataAccessObject(context);
        }

        return singleton;

    }

    // ***** USER METHODS *****

    public void insertUser(User user) {

        ContentValues contentValues = makeUserContentValues(user);

        Log.i(getClass().toString(), "Inserting User into DB");

        database.insert(DBSchema.User.NAME, null, contentValues);
    }

    //get all users but the current user
    public List<User> getUsers(User doNotInclude) {

        Cursor cursor = database.query(DBSchema.User.NAME, null, null,
                null, null, null, null);

        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);

        List<User> userList = new LinkedList<User>();

        try {

            wrapper.moveToFirst();
            while (wrapper.isAfterLast() == false) {
                User user = wrapper.getUser();

                Log.i(getClass().toString(), "CurrentUser: " + doNotInclude.getEmail() + ", Fave's Email: " + user.getEmail());

                if(doNotInclude.getEmail().equals(user.getEmail()) == false) {
                    userList.add(user);
                }
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }

        return userList;
    }

    //check if a user exists
    public User getUser(String email, String password) {

        Log.i(getClass().toString(), "Checking for User Existence");

        Cursor cursor = database.query(DBSchema.User.NAME, null, "EMAIL = ? AND PASSWORD = ?",
                new String[]{email, password}, null, null, null);

        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);

        User user;

        if(wrapper.getCount() > 0) {

            Log.i(getClass().toString(), "We have found a User");
            wrapper.moveToFirst();

            user = wrapper.getUser();
        }
        else {
            user = null;
        }

        wrapper.close();

        return user;

    }

    //delete a user
    public void deleteUser(String email) {

        database.delete(DBSchema.User.NAME, "EMAIL = ?", new String[]{email});

    }

    //get a user from email
    public User getUser(String email) {

        Log.i(getClass().toString(), "Checking for User Existence");

        Cursor cursor = database.query(DBSchema.User.NAME, null, "EMAIL = ?",
                new String[]{email}, null, null, null);

        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);

        User user;

        if(wrapper.getCount() > 0) {

            Log.i(getClass().toString(), "We have found a User");
            wrapper.moveToFirst();

            user = wrapper.getUser();
        }
        else {
            user = null;
        }

        wrapper.close();

        return user;

    }


    // ***** FAVORITE METHODS *****

    public void deleteFave(String email, String faveEmail) {

        database.delete(DBSchema.Favorites.NAME,"EMAIL = ? AND FAVORITE = ?", new String[] {email, faveEmail});

    }

    //get all of one user's favorites
    public List<User> getAllFaves(User user) {

        Cursor cursor = database.query(DBSchema.Favorites.NAME, null, "EMAIL = ?",
                new String [] {user.getEmail()}, null, null, null);

        FaveCursorWrapper wrapper = new FaveCursorWrapper(cursor);

        List<User> userList = new LinkedList<User>();

        Cursor cursorUsers;

        UserCursorWrapper wrapperUsers;

        try {

            wrapper.moveToFirst();

            User faveUser;

            while (wrapper.isAfterLast() == false) {
                String faveEmail = wrapper.getFaveEmail();

                cursorUsers = database.query(DBSchema.User.NAME, null, "EMAIL = ?",
                        new String[]{faveEmail}, null, null, null);

                wrapperUsers = new UserCursorWrapper(cursorUsers);

                if(wrapperUsers.getCount() > 0) {
                    wrapperUsers.moveToFirst();

                    faveUser = wrapperUsers.getUser();
                    userList.add(faveUser);
                }

                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }

        return userList;
    }

    //check if two users are favorites
    public boolean checkFave(String email, String faveEmail) {

        Cursor cursor = database.query(DBSchema.Favorites.NAME, null, "EMAIL = ? AND FAVORITE = ?",
                new String[]{email, faveEmail}, null, null, null);

        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);

        if(wrapper.getCount() > 0) {

            wrapper.close();

            return true;

        }

        wrapper.close();

        return false;

    }

    //get a favorite from their profile picture
    public String getFaveEmail(String filePathPhoto) {

        Cursor cursor = database.query(DBSchema.User.NAME, null, "PROFILE_PIC = ?",
                new String[]{filePathPhoto}, null, null, null);

        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);

        User user;

        if(wrapper.getCount() > 0) {

            Log.i(getClass().toString(), "We have found a User");
            wrapper.moveToFirst();

            user = wrapper.getUser();
        }
        else {
            user = null;
        }

        wrapper.close();

        return user.getEmail();


    }

    //insert favorite
    public void insertFavorite(String email, String fave) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBSchema.Favorites.Cols.email, email);
        contentValues.put(DBSchema.Favorites.Cols.favorite, fave);

        database.insert(DBSchema.Favorites.NAME, null,  contentValues);

    }


    // ***** FEED ITEM METHODS *****

    public void insertFeedItem(FeedItem item) {

        ContentValues contentValues = makeFeedItemContentValues(item);

        database.insert(DBSchema.FeedItems.NAME, null, contentValues);
    }

    public List<FeedItem> getFeedItems(User user) {

        //Get all the feed items, regardless of who posted it
        Cursor cursor = database.query(DBSchema.FeedItems.NAME, null, null,
                null, null, null, null);

        FeedCursorWrapper wrapper = new FeedCursorWrapper(cursor);

        List<FeedItem> feedItems = new LinkedList<FeedItem>();

        Cursor cursorFaves;

        FaveCursorWrapper wrapperFaves;

        try {

            wrapper.moveToFirst();

            while (wrapper.isAfterLast() == false) {
                FeedItem item = wrapper.getFeedItem();

                cursorFaves = database.query(DBSchema.Favorites.NAME, null, "EMAIL = ? AND FAVORITE = ?",
                        new String[]{user.getEmail(), item.getEmail()}, null, null, null);

                wrapperFaves = new FaveCursorWrapper(cursorFaves);

                if(wrapperFaves.getCount() > 0) {

                    feedItems.add(item);
                }

                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }

        Collections.sort(feedItems);

        return feedItems;

    }


    // ***** CONTENT VALUE METHODS *****

    public static ContentValues makeFeedItemContentValues(FeedItem item) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBSchema.FeedItems.Cols.email, item.getEmail());
        contentValues.put(DBSchema.FeedItems.Cols.text, item.getText());
        contentValues.put(DBSchema.FeedItems.Cols.picture, item.getPicture());
        contentValues.put(DBSchema.FeedItems.Cols.datePosted, item.getDatePosted().getTime());

        return contentValues;
    }


    public static ContentValues makeUserContentValues(User user) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBSchema.User.Cols.email, user.getEmail());
        contentValues.put(DBSchema.User.Cols.password, user.getPassword());
        contentValues.put(DBSchema.User.Cols.fullName, user.getFullName());
        contentValues.put(DBSchema.User.Cols.birthdate, user.getBirthdate().getTime());
        contentValues.put(DBSchema.User.Cols.profilePic, user.getProfilePic());
        contentValues.put(DBSchema.User.Cols.homeTown, user.getHomeTown());
        contentValues.put(DBSchema.User.Cols.bio, user.getBio());

        return contentValues;
    }

    // ***** CURSOR WRAPPERS *****

    private static class UserCursorWrapper extends CursorWrapper {


        /**
         * Creates a cursor wrapper.
         *
         * @param cursor The underlying cursor to wrap.
         */

        public UserCursorWrapper(Cursor cursor) {
            super(cursor);
        }


        public User getUser() {

            int emailIndex = getColumnIndex(DBSchema.User.Cols.email);
            String email = getString(emailIndex);

            String password = getString(getColumnIndex(DBSchema.User.Cols.password));
            String fullName = getString(getColumnIndex(DBSchema.User.Cols.fullName));
            Date birthdate = new Date(getLong(getColumnIndex(DBSchema.User.Cols.birthdate)));
            String pic = getString(getColumnIndex(DBSchema.User.Cols.profilePic));
            String homeTown = getString(getColumnIndex(DBSchema.User.Cols.homeTown));
            String bio = getString(getColumnIndex(DBSchema.User.Cols.bio));

            User user = new User();

            user.setEmail(email);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setBirthdate(birthdate);
            user.setProfilePic(pic);
            user.setHomeTown(homeTown);
            user.setBio(bio);

            return user;

        }



    }

    private static class FaveCursorWrapper extends CursorWrapper {


        /**
         * Creates a cursor wrapper.
         *
         * @param cursor The underlying cursor to wrap.
         */

        public FaveCursorWrapper(Cursor cursor) {
            super(cursor);
        }


        public String getFaveEmail() {

            int emailIndex = getColumnIndex(DBSchema.Favorites.Cols.favorite);
            String email = getString(emailIndex);

            return email;
        }
    }

    private static class FeedCursorWrapper extends CursorWrapper {


        /**
         * Creates a cursor wrapper.
         *
         * @param cursor The underlying cursor to wrap.
         */
        public FeedCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public FeedItem getFeedItem() {

            String email = getString(getColumnIndex(DBSchema.FeedItems.Cols.email));
            String text = getString(getColumnIndex(DBSchema.FeedItems.Cols.text));
            String path = getString(getColumnIndex(DBSchema.FeedItems.Cols.picture));

            Long date = getLong(getColumnIndex(DBSchema.FeedItems.Cols.datePosted));

            Date newDate = new Date(date);

            FeedItem item = new FeedItem();

            item.setDatePosted(newDate);
            item.setEmail(email);
            item.setPicture(path);
            item.setText(text);

            return item;
        }


    }


}
