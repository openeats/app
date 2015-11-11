package cloudnine.openeats;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


import cloudnine.openeats.image.ImageAdapter;
import cloudnine.openeats.modal.OpenEatsUser;
import cloudnine.openeats.modal.Post;
import cloudnine.openeats.modal.PostService;
import cloudnine.openeats.modal.OpenEatsUserService;
import cloudnine.openeats.util.UtilClass;


public class ProfileFragment extends Fragment
implements OpenEatsUserServiceAgreement
{
    private final String LOG_TAG = ProfileFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //TODO Remove this after testing
    private ArrayList<Post> postList = new ArrayList<Post>();
    private ImageAdapter postsImageAdapter;
    private GridView mGridView;
    private TextView mPostCount;
    private TextView mReviewCount;
    private TextView mFollowersCount;
    private TextView mUserMoto;
    private TextView mUserName;
    private ImageView mProfilePicture;
    private ImageView mProfileRating;
    private OpenEatsUser mUser;

    private GridLayoutManager manager;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreInstanceState(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentProfile = inflater.inflate(R.layout.fragment_profile, container, false);


        LinearLayout ll = (LinearLayout)fragmentProfile.findViewById(R.id.profile_details);
        mPostCount = (TextView) ll.findViewById(R.id.post_count);
        mReviewCount = (TextView) ll.findViewById(R.id.review_count);
        mFollowersCount = (TextView) ll.findViewById(R.id.followers_count);
        mUserMoto = (TextView) ll.findViewById(R.id.user_moto);
        mUserName = (TextView) ll.findViewById(R.id.user_name);
        mProfilePicture = (ImageView)ll.findViewById(R.id.profile_picture);
        mProfileRating = (ImageView) ll.findViewById(R.id.profile_picture_rating);

        mGridView = (GridView) fragmentProfile.findViewById(R.id.image_grid);


        //get user data from server
        if(mUser == null){
            String userID = UtilClass.getUserId(getContext());
            String[] params = {userID};
            new OpenEatsUserService(this).execute(params);

            Post[] testData = postList.toArray(new Post[postList.size()]);
            postsImageAdapter = new ImageAdapter(fragmentProfile.getContext(),0, testData);
            mGridView.setAdapter(postsImageAdapter);
        }
        else{
            updateUI(mUser);
        }



//        int dpMeasurement = getDpMeasurements(120);

//        mGridView.setColumnWidth(dpMeasurement);


        return fragmentProfile;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mUser != null)
            outState.putParcelable("CURRENT_USER_FOR_PROFILE",mUser);

    }

    public void restoreInstanceState(Bundle savedInstanceState){
        if(savedInstanceState != null && savedInstanceState.containsKey("CURRENT_USER_FOR_PROFILE")){
            mUser = savedInstanceState.getParcelable("CURRENT_USER_FOR_PROFILE");
        }
    }

    private int getDpMeasurements(int pixelMesurement){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

        return pixelMesurement*(displayMetrics.densityDpi/160);
    }

    private int getGridColumnCount() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return 0;
    }

    private void updateUI(OpenEatsUser user){
        mUserName.setText(user.getName());

        String name = user.getPostCount() + "\nposts";
        mPostCount.setText(name);

        String followers = user.getFollowersCount()+ "\nfollowers";
        mFollowersCount.setText(followers);

        String review = user.getReviewCount() + "\nreviews";
        mReviewCount.setText(review);

        mUserMoto.setText(user.getBio());
        mProfileRating.setBackgroundResource(UtilClass.getRatingResourceColor(user.getUserHealthRating()));

        Glide.with(getContext())
                .load(user.getProfilePic().getLargeUrl())
                .fitCenter()
                .into(mProfilePicture);

        Post[] testData = user.getPosts().toArray(new Post[user.getPosts().size()]);
        postsImageAdapter = new ImageAdapter(getContext(),0, testData);
        mGridView.setAdapter(postsImageAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPostExecute(Object returnObject) {
        mUser = (OpenEatsUser)returnObject;
        updateUI(mUser);
    }
}
