package cloudnine.openeats.modal;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gaurang on 11/10/15.
 */
public class OpenEatsUser {

    private String id;
    private String name;
    private String email;
    private String userHealthRating;
    private int reviewCount;
    private OpenEatsImage profilePic;
    private String bio;
    private ArrayList<Post> posts;
    private int postCount;
    private int followingCount;
    private int followersCount;
    private String[] following;
    private Context mContext;
    private String TAG="OpenEatsUser";

    public OpenEatsUser(Context context){
        mContext = context;
    }

    public OpenEatsUser(JSONObject rootObj){
        try {
            this.setId(rootObj.getString("id"));
            this.setName(rootObj.getString("name"));
            this.setEmail(rootObj.getString("email"));
            this.setUserHealthRating(rootObj.getString("rating"));
            this.setPosts(PostService.getTestFoodImageArray(rootObj.getString("posts")));
            this.setProfilePic(new OpenEatsImage(rootObj.getString("picture")));
            this.setBio(rootObj.getString("bio"));
            this.setFollowersCount(rootObj.getInt("followers_count"));
            this.setFollowingCount(rootObj.getInt("following_count"));
            this.setPostCount(rootObj.getInt("post_count"));
            this.setReviewCount(rootObj.getInt("review_count"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public OpenEatsUser(){}

    public boolean getUserInfo(){
        return true;
    }

    public boolean uploadNewImage(){
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public String getUserHealthRating() {
        return userHealthRating;
    }

    public void setUserHealthRating(String userHealthRating) {
        this.userHealthRating = userHealthRating;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public String[] getFollowing() {
        return following;
    }

    public void setFollowing(String[] following) {
        this.following = following;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public OpenEatsImage getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(OpenEatsImage profilePic) {
        this.profilePic = profilePic;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
