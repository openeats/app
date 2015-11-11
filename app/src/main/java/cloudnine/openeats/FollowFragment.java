package cloudnine.openeats;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FollowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowFragment extends Fragment {

    private static final String TAG = FollowFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FollowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FollowFragment newInstance(String param1, String param2) {
        FollowFragment fragment = new FollowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FollowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_follow, container, false);

        SearchView searchView = (SearchView) rootView.findViewById(R.id.follow_search_id);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchUserData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.following_recyler_view_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(null);

        return rootView;
    }

    public static class FollowRecyclerViewAdapter
            extends RecyclerView.Adapter<FollowRecyclerViewAdapter.ViewHolder> {

        private List<FetchFollowInfoAsyncTask.HomeUserData> userDataList;

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final ImageView mUsrProfImg;
            public final TextView mUsrName;
            public final TextView mProfDesc;

            public ViewHolder(View itemView) {
                super(itemView);
                this.mView = itemView;
                mUsrProfImg = (ImageView) itemView.findViewById(R.id.follow_itm_prof_img_id);
                mUsrName = (TextView) itemView.findViewById(R.id.follow_itm_name_view_id);
                mProfDesc = (TextView) itemView.findViewById(R.id.follow_itm_info_view_id);
            }
        }

        public FollowRecyclerViewAdapter(List<FetchFollowInfoAsyncTask.HomeUserData> userDataList) {
            this.userDataList = userDataList;
        }

        @Override
        public FollowRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_recycler_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FollowRecyclerViewAdapter.ViewHolder holder, int position) {
            FetchFollowInfoAsyncTask.HomeUserData userData = userDataList.get(position);
            holder.mUsrName.setText(userData.userName);
            holder.mProfDesc.setText(userData.profileDesc);
            Glide.with(holder.mUsrProfImg.getContext())
                    .load(userData.userPicUrl)
                    .fitCenter()
                    .into(holder.mUsrProfImg);
        }

        @Override
        public int getItemCount() {
            return userDataList.size();
        }
    }

    private void fetchUserData(String searchStr) {
        FetchFollowInfoAsyncTask asyncTask = new FetchFollowInfoAsyncTask(this, getActivity(), searchStr);
        asyncTask.execute();
    }


    public class FetchFollowInfoAsyncTask extends AsyncTask<Void, Void, List<FetchFollowInfoAsyncTask.HomeUserData>> {

        public final int NUM_IMAGES_LANDSCAPE = 5;

        public class HomeUserData {
            public String id;
            public String userName;
            public String userPicUrl;
            public String profileDesc;
        }

        private FollowFragment homeFragment;
        private Context context;
        private String searchStr;

        public FetchFollowInfoAsyncTask(FollowFragment homeFragment, Context context, String searchStr)  {
            this.homeFragment = homeFragment;
            this.context = context;
            this.searchStr = searchStr;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(final List<HomeUserData> userDataList) {
            homeFragment.mRecyclerView.setAdapter(new FollowRecyclerViewAdapter(userDataList));
        }

        @Override
        protected List<HomeUserData> doInBackground(Void... voids) {
            String homeJsonData = fetchJsonData();
            List<HomeUserData> userDataList = parseJsonData(homeJsonData);
            return userDataList;
        }

        private String fetchJsonData() {
            Uri homeDataFetchUri = Uri.parse(context.getString(R.string.follow_data_fetch_url)).
                    buildUpon().
                    appendQueryParameter(context.getString(R.string.query_param), searchStr).
                    //appendQueryParameter(context.getString(R.string.userid_param), UtilClass.getUserId(context)).
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
                    Log.d(TAG, "Fetched Following data successfully");
                } else {
                    Log.d(TAG, "Following data fetch failed");
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

                    userData.id = userObj.getString(context.getString(R.string.id_param));
                    userData.userName = userObj.getString(context.getString(R.string.name_attr));
                    JSONObject userProfPicObj = userObj.getJSONObject(context.getString(R.string.picture_attr));
                    userData.userPicUrl = userProfPicObj.getString(context.getString(R.string.small_attr));
                    userData.profileDesc = userObj.getString(context.getString(R.string.bio_param));

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
