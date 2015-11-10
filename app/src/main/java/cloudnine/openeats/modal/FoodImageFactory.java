package cloudnine.openeats.modal;

import java.util.ArrayList;

/**
 * Created by gaurang on 11/10/15.
 */
public class FoodImageFactory {

    public static ArrayList<FoodImage> getTestFoodImageArray(int count){
        ArrayList<FoodImage> testData = new ArrayList<FoodImage>();

        for(int i=0;i<count;i++){
            FoodImage fi = new FoodImage();
            if(i%2==0)
                fi.setHealthyRating(1);
            else
                fi.setUnhealthyRating(1);

            testData.add(fi);
        }
        return testData;
    }

}
