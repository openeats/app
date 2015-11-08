package cloudnine.openeats.image;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by smajeti on 11/8/15.
 */
public class ImageUpload {

    static public void uploadImage(String url, File file,
                                   AsyncHttpResponseHandler responseHandler) throws FileNotFoundException {
        RequestParams params = new RequestParams();
        params.put("Image File", file);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, responseHandler);
    }
}
