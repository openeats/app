package cloudnine.openeats.modal;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import cloudnine.openeats.OpenEatsUserServiceAgreement;
import cloudnine.openeats.R;
import cloudnine.openeats.util.UtilClass;


/**
 * Created by gaurang on 11/10/15.
 */
public class OpenEatsUserService extends AsyncTask<String,Void, Object>{

    private OpenEatsUserServiceAgreement mParentActivity;
    private static OpenEatsUser currentUser=null;
    private static String USER_PROFILE_URL="http://api.openeatsproject.com/users/posts";
    private static String userIDParam = "userId";
    private Uri uri;
    final private static String TAG = OpenEatsUserService.class.getSimpleName();

    public OpenEatsUserService(OpenEatsUserServiceAgreement parentActivity) {
        mParentActivity = parentActivity;
    }

    @Override
    protected Object doInBackground(String... params) {

        String userId = params[0];
        uri =Uri.parse(USER_PROFILE_URL).
                buildUpon().
                appendQueryParameter(userIDParam,userId).
                build();


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String homeJsonData = null;

        try {
            URL url = new URL(uri.toString());
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            String resmsg = urlConnection.getResponseMessage();
            if (urlConnection.getResponseCode() == 200) {
                Log.d(TAG, "Fetched Home data successfully");
            } else {
                Log.d(TAG, "Home data fetch failed");
            }

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            homeJsonData = buffer.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error ", e);
            return null;
        } catch (ProtocolException e) {
            Log.e(TAG, "Error ", e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

        return homeJsonData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object userProfile) {
        Object oeu = null;
        try {
            oeu = new OpenEatsUser(new JSONObject(userProfile.toString()));
            mParentActivity.onPostExecute(oeu);
        } catch (JSONException e) {
            e.printStackTrace();
            mParentActivity.onPostExecute(new OpenEatsUser());
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
