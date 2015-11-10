package cloudnine.openeats.modal;

/**
 * Created by gaurang on 11/10/15.
 */
public class FoodImage
{
    public static final String HEALTHY_FOOD_TYPE="healthy";

    private long id;
    private long healthyRating;
    private long unhealthyRating;
    private long notSureRating;


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    //    public String getFoodType() {
//        return foodType;
//    }
//
//    public void setFoodType(String foodType) {
//        this.foodType = foodType;
//    }
}
