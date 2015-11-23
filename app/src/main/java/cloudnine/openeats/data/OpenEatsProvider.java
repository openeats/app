package cloudnine.openeats.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class OpenEatsProvider extends ContentProvider {

    static final int POST = 100;
    static final int POST_WITH_USER_ID = 101;
    static final int REVIEW = 200;
    static final int USER = 300;
    static final int FAVORITE = 400;
    static final int FAVORITES_WITH_USER_ID = 401;
    static final int FOLLOWING = 500;
    static final int FOLLOWING_WITH_USER_ID = 501;

    private OpenEatsDbHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public OpenEatsProvider() {
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = OpenEatsContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, OpenEatsContract.PATH_POST, POST);
        //all posts for a user
        matcher.addURI(authority, OpenEatsContract.PATH_POST + "/#", POST_WITH_USER_ID);

        matcher.addURI(authority, OpenEatsContract.PATH_REVIEW, REVIEW);

        matcher.addURI(authority, OpenEatsContract.PATH_USER, USER);

        matcher.addURI(authority, OpenEatsContract.PATH_USER_FAVORITE, FAVORITE);
        matcher.addURI(authority, OpenEatsContract.PATH_USER_FAVORITE + "/#", FAVORITES_WITH_USER_ID);

        matcher.addURI(authority, OpenEatsContract.PATH_USER_FOLLOWING, FOLLOWING);
        matcher.addURI(authority, OpenEatsContract.PATH_USER_FOLLOWING + "/#", FOLLOWING_WITH_USER_ID);
        return matcher;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case POST:
                return OpenEatsContract.PostsEntry.CONTENT_TYPE;
            case POST_WITH_USER_ID:
                return OpenEatsContract.PostsEntry.CONTENT_ITEM_TYPE;
            case REVIEW:
                return OpenEatsContract.ReviewsEntry.CONTENT_TYPE;
            case USER:
                return OpenEatsContract.UsersEntry.CONTENT_TYPE;
            case FAVORITES_WITH_USER_ID:
                return OpenEatsContract.UserFavoritesEntry.CONTENT_ITEM_TYPE;

        }
        throw new UnsupportedOperationException("Unknown uri: "+ uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case POST: {
                long _id = db.insert(OpenEatsContract.PostsEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = OpenEatsContract.PostsEntry.buildPostsUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case REVIEW: {
                long _id = db.insert(OpenEatsContract.ReviewsEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = OpenEatsContract.ReviewsEntry.buildReviewsUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case USER: {
                long _id = db.insert(OpenEatsContract.UsersEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = OpenEatsContract.UsersEntry.buildUsersUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case FAVORITE: {
                long _id = db.insert(OpenEatsContract.UserFavoritesEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = OpenEatsContract.UserFavoritesEntry.buildFavoritesUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case FOLLOWING: {
                long _id = db.insert(OpenEatsContract.UserFollowingsEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = OpenEatsContract.UserFollowingsEntry.buildFollowingsUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new OpenEatsDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
