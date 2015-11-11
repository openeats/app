package cloudnine.openeats;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cloudnine.openeats.util.UtilClass;


public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView mRecyclerView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this fragment can be refreshed!
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_refresh, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recyler_view_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(null);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchUserData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    public static class HomeRecyclerViewAdapter
            extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

        private List<FetchHomeInfoAsyncTask.HomeUserData> userDataList;

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView mUserName;
            public final ImageView mUserProfImgView;
            public final ImageView mImageView0;
            public final ImageView mImageView1;
            public final ImageView mImageView2;
            public final ImageView mImageView3;
            public final ImageView mImageView4;

            public ViewHolder(View itemView) {
                super(itemView);
                this.mView = itemView;
                mUserName = (TextView) itemView.findViewById(R.id.home_profile_name_id);
                mUserProfImgView = (ImageView) itemView.findViewById(R.id.home_profile_img_id);
                mImageView0 = (ImageView) itemView.findViewById(R.id.home_profile_eats_img1_id);
                mImageView1 = (ImageView) itemView.findViewById(R.id.home_profile_eats_img2_id);
                mImageView2 = (ImageView) itemView.findViewById(R.id.home_profile_eats_img3_id);
                mImageView3 = (ImageView) itemView.findViewById(R.id.home_profile_eats_img4_id);
                mImageView4 = (ImageView) itemView.findViewById(R.id.home_profile_eats_img5_id);
            }
        }

        public HomeRecyclerViewAdapter(List<FetchHomeInfoAsyncTask.HomeUserData> userDataList) {
            this.userDataList = userDataList;
        }

        @Override
        public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HomeRecyclerViewAdapter.ViewHolder holder, int position) {
            FetchHomeInfoAsyncTask.HomeUserData userData = userDataList.get(position);
            holder.mUserName.setText(userData.userName);
            Glide.with(holder.mUserProfImgView.getContext())
                    .load(userData.userPicUrl)
                    .fitCenter()
                    .into(holder.mUserProfImgView);
            Glide.with(holder.mImageView0.getContext())
                    .load(userData.userPostImgUrls[0])
                    .fitCenter()
                    .into(holder.mImageView0);
            Glide.with(holder.mImageView0.getContext())
                    .load(userData.userPostImgUrls[1])
                    .fitCenter()
                    .into(holder.mImageView1);
            Glide.with(holder.mImageView0.getContext())
                    .load(userData.userPostImgUrls[2])
                    .fitCenter()
                    .into(holder.mImageView2);
            if ((holder.mImageView3 != null) && (userData.userPostImgUrls[3] != null)) {
                Glide.with(holder.mImageView3.getContext())
                        .load(userData.userPostImgUrls[3])
                        .fitCenter()
                        .into(holder.mImageView3);
            }
            if ((holder.mImageView4 != null) && (userData.userPostImgUrls[4] != null)) {
                Glide.with(holder.mImageView4.getContext())
                        .load(userData.userPostImgUrls[4])
                        .fitCenter()
                        .into(holder.mImageView4);
            }
        }

        @Override
        public int getItemCount() {
            return userDataList.size();
        }
    }

    private void fetchUserData() {
        FetchHomeInfoAsyncTask asyncTask = new FetchHomeInfoAsyncTask(this, getActivity());
        asyncTask.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.refresh) {
            fetchUserData();
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchHomeInfoAsyncTask extends AsyncTask<Void, Void, List<FetchHomeInfoAsyncTask.HomeUserData>> {

        public final int NUM_IMAGES_LANDSCAPE = 5;

        public class HomeUserData {
            public String userName;
            public String userPicUrl;
            public String[] userPostImgUrls = new String[NUM_IMAGES_LANDSCAPE];
        }

        private HomeFragment homeFragment;
        private Context context;

        public FetchHomeInfoAsyncTask(HomeFragment homeFragment, Context context)  {
            this.homeFragment = homeFragment;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(final List<HomeUserData> userDataList) {
            homeFragment.mRecyclerView.setAdapter(new HomeRecyclerViewAdapter(userDataList));
        }

        @Override
        protected List<HomeUserData> doInBackground(Void... voids) {
            String homeJsonData = fetchJsonData();
            List<HomeUserData> userDataList = parseJsonData(homeJsonData);
            return userDataList;
        }

        private String fetchJsonData() {
            Uri homeDataFetchUri = Uri.parse(context.getString(R.string.home_data_fetch_url)).
                    buildUpon().
                    appendQueryParameter(context.getString(R.string.userid_param), UtilClass.getUserId(context)).
                    build();

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String homeJsonData = null;

            try {
                URL url = new URL(homeDataFetchUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                int responseCode = urlConnection.getResponseCode();
                String resmsg = urlConnection.getResponseMessage();
                if (urlConnection.getResponseCode() == 200) {
                    Log.d(TAG, "Fetched Home data successfully");
                } else {
                    Log.d(TAG, "Home data fetch failed");
                }

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                homeJsonData = buffer.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "Error ", e);
                return null;
            } catch (ProtocolException e) {
                Log.e(TAG, "Error ", e);
                return null;
            } catch (IOException e) {
                Log.e(TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }

            return homeJsonData;
        }

        private List<HomeUserData> parseJsonData(String jsonData) {
            try {
                JSONArray rootObj = new JSONArray(jsonData);
                List<HomeUserData> homeDataList = new ArrayList<>(rootObj.length());
                for (int uindx = 0; uindx < rootObj.length(); ++uindx) {
                    JSONObject userObj = rootObj.getJSONObject(uindx);
                    HomeUserData userData = new HomeUserData();

                    userData.userName = userObj.getString(context.getString(R.string.name_attr));
                    JSONObject userProfPicObj = userObj.getJSONObject(context.getString(R.string.picture_attr));
                    userData.userPicUrl = userProfPicObj.getString(context.getString(R.string.small_attr));

                    JSONArray postsArray = userObj.getJSONArray(context.getString(R.string.posts_attr));
                    for (int indx = 0; indx < postsArray.length(); ++indx) {
                        if (indx == NUM_IMAGES_LANDSCAPE) {
                            break;
                        }
                        userData.userPostImgUrls[indx] = postsArray.getJSONObject(indx).
                                getJSONObject(context.getString(R.string.images_attr)).
                                getString(context.getString(R.string.small_attr));
                    }
                    homeDataList.add(userData);
                }

                return homeDataList;
            } catch (JSONException e) {
                Log.d(TAG, "Error ", e);
            }

            return null;
        }

    }

}
