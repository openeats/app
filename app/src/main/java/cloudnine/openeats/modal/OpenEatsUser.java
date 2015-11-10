package cloudnine.openeats.modal;

import java.util.ArrayList;

/**
 * Created by gaurang on 11/10/15.
 */
public class OpenEatsUser {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<FoodImage> foodImages;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<FoodImage> getFoodImages() {
        return foodImages;
    }

    public void setFoodImages(ArrayList<FoodImage> foodImages) {
        this.foodImages = foodImages;
    }
}
