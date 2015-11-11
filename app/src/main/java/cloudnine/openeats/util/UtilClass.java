package cloudnine.openeats.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cloudnine.openeats.R;

/**
 * Created by smajeti on 11/8/15.
 */
public class UtilClass {

    private static final String LOG_TAG = UtilClass.class.getSimpleName();

    static public String getUserId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(context.getString(R.string.user_id_key), null);
    }

    static public void setUserId(Context context, String userId) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(context.getString(R.string.user_id_key), userId);
        spe.apply();
    }


    static public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
    }

    public static String getUserIdFromJsonData(String userJsonData) {
        try {
            JSONObject userJsonObj = new JSONObject(userJsonData);
            return userJsonObj.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getRatingResourceColor(String rating) {
        int ratingResource = 0;
        if (rating.contentEquals("healthy")) {
            ratingResource = R.color.ratingHealthy;
        } else if (rating.contentEquals("unhealthy")) {
            ratingResource = R.color.ratingUnhealthy;
        } else {
            ratingResource = R.color.ratingNeutral;
        }

        return ratingResource;
    }

    public static int getRatingResourceOpaqueColor(String rating) {
        int ratingResource = 0;
        if (rating.contentEquals("healthy")) {
            ratingResource = R.color.ratingHealthyOpaque;
        } else if (rating.contentEquals("unhealthy")) {
            ratingResource = R.color.ratingUnhealthyOpaque;
        } else {
            ratingResource = R.color.ratingNeutralOpaque;
        }

        return ratingResource;
    }

    public static int getRatingRGBColor(String rating) {
        Log.v(LOG_TAG, "======= " + rating);
        int ratingResource = 0;
        if (rating.contentEquals("healthy")) {
            ratingResource = Color.argb(150, 44, 169, 79);
        } else if (rating.contentEquals("unhealthy")) {
            ratingResource = Color.argb(150, 236, 65, 44);
        } else {
            ratingResource = Color.argb(150, 253, 189, 0);
        }

        return ratingResource;
    }

}
