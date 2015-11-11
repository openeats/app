package cloudnine.openeats.modal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cloudnine.openeats.image.ImageOverlay;

/**
 * Created by gaurang on 11/10/15.
 */
public class Post
{
    public static final String HEALTHY_FOOD_TYPE="healthy";
    public static final String UNHEALTHY_FOOD_TYPE="unhealthy";
    public static final String NOTSURE_FOOD_TYPE="neutral";

    private String id;
    private OpenEatsImage image;
    private long healthyRating;
    private long unhealthyRating;
    private long notSureRating;
    private String finalRating;
    private String createdDate;
    private String updatedDate;

    public Post(JSONObject postJsonObject){
        try {
            this.setId(postJsonObject.getString("id"));
            this.setFinalRating(postJsonObject.getString("rating"));
            this.setCreatedDate(postJsonObject.getString("created_at"));
            this.setUpdatedDate(postJsonObject.getString("updated_at"));
            this.setHealthyRating(postJsonObject.getLong("review_healthy_count"));
            this.setUnhealthyRating(postJsonObject.getLong("review_unhealthy_count"));
            this.setImages(new OpenEatsImage(postJsonObject.getString("images")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Post(){

    }

    public ImageOverlay getImageOverLay(){

        if(this.getFinalRating().equals(HEALTHY_FOOD_TYPE))
            return ImageOverlay.getDrawableForHealthyFood();
        else
            return ImageOverlay.getDrawableForUnHealthyFood();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getHealthyRating() {
        return healthyRating;
    }

    public void setHealthyRating(long healthyRating) {
        this.healthyRating = healthyRating;
    }

    public long getUnhealthyRating() {
        return unhealthyRating;
    }

    public void setUnhealthyRating(long unhealthyRating) {
        this.unhealthyRating = unhealthyRating;
    }

    public long getNotSureRating() {
        return notSureRating;
    }

    public void setNotSureRating(long notSureRating) {
        this.notSureRating = notSureRating;
    }

    public OpenEatsImage getImages() {
        return image;
    }

    public void setImages(OpenEatsImage image) {
        this.image = image;
    }

    public String getFinalRating() {
        return finalRating;
    }

    public void setFinalRating(String finalRating) {
        this.finalRating = finalRating;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
