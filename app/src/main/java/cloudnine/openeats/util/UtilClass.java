package cloudnine.openeats.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import cloudnine.openeats.R;

/**
 * Created by smajeti on 11/8/15.
 */
public class UtilClass {

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


}
