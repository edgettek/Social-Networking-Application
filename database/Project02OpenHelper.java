package csc296.project02.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by KEdgette1 on 11/13/15.
 */
public class Project02OpenHelper extends SQLiteOpenHelper {
    public Project02OpenHelper(Context context) {
        super(context, DBSchema.DATABASE_NAME, null, DBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i(getClass().toString(), "Creating a Database");

        db.execSQL("CREATE TABLE " + DBSchema.User.NAME
                    + "(_id integer primary key autoincrement, "
                        + DBSchema.User.Cols.email + ", "
                        + DBSchema.User.Cols.password + ", "
                        + DBSchema.User.Cols.fullName + ", "
                        + DBSchema.User.Cols.birthdate + ", "
                        + DBSchema.User.Cols.profilePic + ", "
                        + DBSchema.User.Cols.homeTown + ", "
                        + DBSchema.User.Cols.bio + ")");

        db.execSQL("CREATE TABLE " + DBSchema.FeedItems.NAME
                + "(_id integer primary key autoincrement, "
                + DBSchema.FeedItems.Cols.email + ", "
                + DBSchema.FeedItems.Cols.text + ", "
                + DBSchema.FeedItems.Cols.picture + ", "
                + DBSchema.FeedItems.Cols.datePosted + ")");

        db.execSQL("CREATE TABLE " + DBSchema.Favorites.NAME
                + "(_id integer primary key autoincrement, "
                + DBSchema.Favorites.Cols.email + ", "
                + DBSchema.Favorites.Cols.favorite + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
