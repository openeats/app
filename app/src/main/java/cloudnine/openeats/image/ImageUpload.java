package cloudnine.openeats.image;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cloudnine.openeats.util.UtilClass;

/**
 * Created by smajeti on 11/8/15.
 */
public class ImageUpload {
    final private static String TAG = ImageUpload.class.getSimpleName();
    final private static String UPLOAD_URL = "http://api.openeatsproject.com/containers/posts/upload";
    static public void uploadImage(File file, String userId, String rating,
                                   AsyncHttpResponseHandler responseHandler) throws FileNotFoundException {
        rating = rating.toLowerCase();
        RequestParams params = new RequestParams();
        params.put("Image File", file);
        String urlWithGetParams = UPLOAD_URL+"?userId="+userId+"&rating="+rating;
        Log.d(TAG, "URL: "+ urlWithGetParams);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(urlWithGetParams, params, responseHandler);
    }
}
