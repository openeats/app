package cloudnine.openeats.modal;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gaurang on 11/10/15.
 */
public class OpenEatsImage {
    private String largeUrl;
    private String mediumUrl;
    private String smallUrl;


    public OpenEatsImage(String jsonString){
        try {
            JSONObject imageObject = new JSONObject(jsonString);
            this.setLargeUrl(imageObject.getString("large"));
            this.setMediumUrl(imageObject.getString("medium"));
            this.setSmallUrl(imageObject.getString("small"));
        } catch (JSONException e) {
            e.printStackTrace();
            this.setLargeUrl("");
            this.setMediumUrl("");
            this.setSmallUrl("");

        }
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }
}
