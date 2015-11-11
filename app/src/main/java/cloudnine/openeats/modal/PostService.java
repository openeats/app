package cloudnine.openeats.modal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gaurang on 11/10/15.
 */
public class PostService {

    public static ArrayList<Post> getTestFoodImageArray(int count){
        ArrayList<Post> testData = new ArrayList<Post>();

        for(int i=0;i<count;i++){
            Post fi = new Post();
            if(i%2==0)
                fi.setHealthyRating(1);
            else
                fi.setUnhealthyRating(1);

            testData.add(fi);
        }
        return testData;
    }

    public static ArrayList<Post> getTestFoodImageArray(String jsonObject) throws JSONException {

        ArrayList<Post> postData = new ArrayList<Post>();
        JSONArray postJsonArray = new JSONArray(jsonObject);

        for(int i=0;i<postJsonArray.length();i++){
            JSONObject post = (JSONObject)postJsonArray.get(i);
            Post userPost = new Post(post);
            postData.add(userPost);
        }
        return postData;
    }

}
