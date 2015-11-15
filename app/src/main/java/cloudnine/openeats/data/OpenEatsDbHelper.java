package cloudnine.openeats.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cloudnine.openeats.data.OpenEatsContract.*;

/**
 * Created by NICK on 11/15/2015.
 */
public class OpenEatsDbHelper extends SQLiteOpenHelper{

    static final String DATABASE_NAME = "open_eats.db";
    private static final int DATABASE_VERSION = 1;

    public OpenEatsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //reviews are unique by the combination of post id and user id
        final String SQL_CREATE_REVIEW_TABLE =  "CREATE TABLE " + ReviewsEntry.TABLE_NAME + " (" +
                ReviewsEntry.COLUMN_RATING + " TEXT NOT NULL, " +
                ReviewsEntry.COLUMN_POST_ID + " TEXT NOT NULL, " +
                ReviewsEntry.COLUMN_USER_ID + " TEXT NOT NULL, " +
                " UNIQUE (" + ReviewsEntry.COLUMN_USER_ID + " , " +
                ReviewsEntry.COLUMN_POST_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_REVIEW_TABLE);


        final String SQL_CREATE_POST_TABLE =  "CREATE TABLE " + PostsEntry.TABLE_NAME + " (" +
                PostsEntry._ID + " TEXT PRIMARY KEY NOT NULL," +
                PostsEntry.COLUMN_USER_ID + " TEXT NOT NULL," +
                PostsEntry.COLUMN_RATING + " TEXT NOT NULL," +
                PostsEntry.COLUMN_IMAGE_SMALL + " TEXT NULL," +
                PostsEntry.COLUMN_IMAGE_MEDIUM + " TEXT NULL," +
                PostsEntry.COLUMN_IMAGE_LARGE + " TEXT NULL," +
                PostsEntry.COLUMN_CREATED_AT + " INTEGER NOT NULL," +
                PostsEntry.COLUMN_UPDATED_AT + " INTEGER NOT NULL," +
                PostsEntry.COLUMN_REVIEW_HEALTHY_COUNT + " INTEGER DEFAULT 0," +
                PostsEntry.COLUMN_REVIEW_UNHEALTHY_COUNT + " INTEGER DEFAULT 0"
                + ");";
        db.execSQL(SQL_CREATE_POST_TABLE);

        final String SQL_CREATE_USER_TABLE =  "CREATE TABLE " + UsersEntry.TABLE_NAME + " ("
                + ");";
        db.execSQL(SQL_CREATE_USER_TABLE);

        final String SQL_CREATE_USER_FOLLOWING_TABLE =  "CREATE TABLE " + UserFollowingsEntry.TABLE_NAME + " ("
                + ");";
        db.execSQL(SQL_CREATE_USER_FOLLOWING_TABLE);

        final String SQL_CREATE_USER_FAVORITE_TABLE =  "CREATE TABLE " + UserFavoritesEntry.TABLE_NAME + " ("
                + ");";
        db.execSQL(SQL_CREATE_USER_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ReviewsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PostsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UsersEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserFollowingsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserFavoritesEntry.TABLE_NAME);
        onCreate(db);
    }
}
