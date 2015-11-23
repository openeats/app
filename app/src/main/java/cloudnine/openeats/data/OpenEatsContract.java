package cloudnine.openeats.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by NICK on 11/15/2015.
 *
 * example user id 564392849f72ed395dcfbb50 (Nick)
 * example post id 564392849f72ed395dcfbec2
 */
public class OpenEatsContract {
    public static final String CONTENT_AUTHORITY = "cloudnine.openeats";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_REVIEW = "review";
    public static final String PATH_POST = "post";
    public static final String PATH_USER = "user";
    public static final String PATH_USER_FOLLOWING = "user_following";
    public static final String PATH_USER_FAVORITE = "user_favorite";

    /**
     * Example Review result from API
     * {
     * "id": "56431657b70b150532281302",
     * "rating": "healthy",
     * "post_id": "5642f22ff358a2f31d6524de",
     * "user_id": "5642f22ff358a2f31d6524db"
     * }
     */
    public static final class ReviewsEntry{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        public static final String TABLE_NAME = "reviews";

        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_POST_ID = "post_id";
        public static final String COLUMN_USER_ID = "user_id";

        public static Uri buildReviewsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /**
     * Example Post result from API
     *
     * {
     * "id": "564803f3a6c9697033b60f15",
     * "user_id": "564392849f72ed395dcfbb4e",
     * "rating": "healthy",
     * "images": {
     * "small": "http://api.openeatsproject.com/containers/posts/download/IMG_20151114_200248.jpg",
     * "medium": "http://api.openeatsproject.com/containers/posts/download/IMG_20151114_200248.jpg",
     * "large": "http://api.openeatsproject.com/containers/posts/download/IMG_20151114_200248.jpg"
     * },
     * "created_at": 1447560179,
     * "updated_at": 1447560179,
     * "review_healthy_count": 0,
     * "review_unhealthy_count": 0
     * }
     */
    public static final class PostsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POST).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POST;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POST;

        public static final String TABLE_NAME = "posts";

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_IMAGE_SMALL = "image_small";
        public static final String COLUMN_IMAGE_MEDIUM = "image_medium";
        public static final String COLUMN_IMAGE_LARGE = "image_large";
        public static final String COLUMN_CREATED_AT = "created_at";
        public static final String COLUMN_UPDATED_AT = "updated_at";
        public static final String COLUMN_REVIEW_HEALTHY_COUNT = "review_healthy_count";
        public static final String COLUMN_REVIEW_UNHEALTHY_COUNT = "review_unhealthy_count";

        public static Uri buildPostsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /**
     * Example User result from API
     *
     *   {
     "id": "564392849f72ed395dcfbb50",
     "name": "Nick Ostrowski",
     "email": "no",
     "bio": "",
     "password": "a4a3448baeb07e0636ead0006d963520",
     "rating": "healthy",
     "picture": {
     "large": "https://s3.amazonaws.com/openeatsproject/users/nick-ostrowski.jpg",
     "medium": "https://s3.amazonaws.com/openeatsproject/users/nick-ostrowski.jpg",
     "small": "https://s3.amazonaws.com/openeatsproject/users/nick-ostrowski.jpg"
     },
     "joined_on": 1447268996,
     "updated_on": 1447268996,
     "following": [
     "564392849f72ed395dcfbb4a",
     "564392849f72ed395dcfbb49",
     "564392849f72ed395dcfbb4c",
     "564392849f72ed395dcfbb4d",
     "564392849f72ed395dcfbb50",
     "564392849f72ed395dcfbb4f",
     "564392849f72ed395dcfbb4e"
     ],
     "favorites": [],
     "posts": [],
     "updated": 1447268996
     },
     */
    public static final class UsersEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String TABLE_NAME = "users";

        public static final String COLUMN_NAME              = "name";
        public static final String COLUMN_EMAIL             = "email";
        public static final String COLUMN_BIO               = "bio";
        public static final String COLUMN_PASSWORD          = "password";
        public static final String COLUMN_RATING            = "rating";
        public static final String COLUMN_PICTURE_LARGE     = "picture_large";
        public static final String COLUMN_PICTURE_MEDIUM    = "picture_medium";
        public static final String COLUMN_PICTURE_SMALL     = "picture_small";
        public static final String COLUMN_JOINED_ON         = "joined_on";
        public static final String COLUMN_UPDATED_ON        = "updated_on";
        public static final String COLUMN_UPDATED           = "updated";

        //note: "following", "favorites", and "posts" go in separate tables in SQLite
        public static Uri buildUsersUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    //a list of people the user is following
    //*does not implement base columns; should not have a separate id, each row should be unique.
    public static final class UserFollowingsEntry {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER_FOLLOWING).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER_FOLLOWING;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER_FOLLOWING;

        public static final String TABLE_NAME = "user_followings";

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_FOLLOWING_ID = "following_id"; //a user_id

        public static Uri buildFollowingsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    //a list of meals the user has favorited
    //*does not implement base columns; should not have a separate id, each row should be unique.
    public static final class UserFavoritesEntry {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER_FAVORITE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER_FAVORITE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER_FAVORITE;

        public static final String TABLE_NAME = "user_favorites";

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_POST_ID = "post_id";

        public static Uri buildFavoritesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
