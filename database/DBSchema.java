package csc296.project02.database;

import java.util.Date;

/**
 * Created by KEdgette1 on 11/13/15.
 */


public class DBSchema {

    public static final String DATABASE_NAME = "Project02.SocialNetwork.DB";
    public static final int VERSION = 1;

    public static class User {

        public static final String NAME = "USERS";

        public static class Cols {

            public static final String email = "EMAIL";
            public static final String password = "PASSWORD";
            public static final String fullName = "FULL_NAME";
            public static final String birthdate = "BIRTHDATE";
            public static final String profilePic = "PROFILE_PIC";
            public static final String homeTown = "HOME_TOWN";
            public static final String bio = "BIO";

        }
    }

    public static class FeedItems {

        public static final String NAME = "FEED_ITEMS";

        public static class Cols {

            public static final String email = "EMAIL";
            public static final String text = "TEXT";
            public static final String picture = "PICTURE";
            public static final String datePosted = "DATE_POSTED";

        }
    }

    public static class Favorites {

        public static final String NAME = "FAVORITES";

        public static class Cols {

            public static final String email = "EMAIL";
            public static final String favorite = "FAVORITE";

        }

    }

}
