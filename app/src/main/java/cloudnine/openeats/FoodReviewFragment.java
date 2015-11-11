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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.squareup.picasso.Picasso;

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

import cloudnine.openeats.util.UtilClass;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FoodReviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoodReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodReviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = FoodReviewFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView mRecyclerView;

    private CardViewAdapter mAdapter;

    private ArrayList<String> mItems;
    List<FetchFoodDataTask.FoodData> mfoodDataList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodReviewFragment newInstance(String param1, String param2) {
        FoodReviewFragment fragment = new FoodReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FoodReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_review, container, false);
        // Inflate the layout for this fragment
        mItems = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            mItems.add(String.format("Card number %02d", i));
        }


        mAdapter = new CardViewAdapter(mfoodDataList, getContext());

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    //Toast.makeText(RateActivity.this, mItems.get(position) + " swiped left", Toast.LENGTH_SHORT).show();
                                    mItems.remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    Toast.makeText(RateActivity.this, mItems.get(position) + " swiped right", Toast.LENGTH_SHORT).show();
                                    mItems.remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });
        recyclerView.addOnItemTouchListener(swipeTouchListener);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
        public List<FetchFoodDataTask.FoodData> foodDataList;
        private Context context;

        public CardViewAdapter(List<FetchFoodDataTask.FoodData> mfoodDataList, Context mContext) {
            this.foodDataList = mfoodDataList;
            this.context = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_food_review, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            FetchFoodDataTask.FoodData userData = foodDataList.get(i);
            viewHolder.title.setText("Food");
            ImageView image1;
            Picasso.with(viewHolder.itemView.getContext()).load(userData.userfoodImagesUrl[2]).into(viewHolder.image1);

        }

        private void setupRecyclerView(RecyclerView rv) {
            FetchFoodDataTask asyncTask = new FetchFoodDataTask();
            asyncTask.execute();
            rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));



        }

        @Override
        public int getItemCount() {
            return foodDataList == null ? 0 : foodDataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView title;
            private TextView name;
            private ImageView image1;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.card_view_title);

                //image1 = (Image) itemView.findViewById());
                name = (TextView) itemView.findViewById(R.id.name);
                image1 = (ImageView) itemView.findViewById(R.id.image);

                // image1 = (ImageView) findViewById(R.id.image);
                // Picasso.with(this).load("https://goo.gl/R6lRbg").into(image1);
            }
        }
    }

    public class FetchFoodDataTask extends AsyncTask<Void, Void, List<FetchFoodDataTask.FoodData>> {


        public class FoodData {
            public String userName;
            public String[] userfoodImagesUrl = new String[10];
        }

        private FoodReviewFragment reviewFragment;
        private Context context;


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(final List<FoodData> foodDataList) {
            reviewFragment.mRecyclerView.setAdapter(new CardViewAdapter(foodDataList, context));

        }

        @Override
        protected List<FoodData> doInBackground(Void... voids) {
            String foodJsonData = fetchJsonData();
            List<FoodData> foodDataList = parseJsonData(foodJsonData);
            return foodDataList;
        }

        private String fetchJsonData() {
            Uri foodDataFetchUri = Uri.parse(context.getString(R.string.home_data_fetch_url)).
                    buildUpon().
                    appendQueryParameter(context.getString(R.string.userid_param), UtilClass.getUserId(context)).
                    build();

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String homeJsonData = null;

            try {
                URL url = new URL(foodDataFetchUri.toString());
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

        private List<FoodData> parseJsonData(String jsonData) {
            try {
                JSONArray rootObj = new JSONArray(jsonData);
                List<FoodData> homeDataList = new ArrayList<>(rootObj.length());
                for (int i = 0; i < rootObj.length(); i++) {
                    JSONObject userObj = rootObj.getJSONObject(i);
                    FoodData foodData = new FoodData();

                    foodData.userName = userObj.getString(context.getString(R.string.name_attr));
                    // JSONObject userProfPicObj = userObj.getJSONObject(context.getString(R.string.picture_attr));
                    // foodData.userfoodImagesUrl = userProfPicObj.getString(context.getString(R.string.small_attr));

                    JSONArray postsArray = userObj.getJSONArray(context.getString(R.string.posts_attr));
                    for (i = 0; i< postsArray.length(); i++) {
                        if (i == 10) {
                            break;
                        }
                        foodData.userfoodImagesUrl[i] = postsArray.getJSONObject(i).
                                getJSONObject(context.getString(R.string.images_attr)).
                                getString(context.getString(R.string.small_attr));
                    }
                    homeDataList.add(foodData);
                }

                return homeDataList;
            } catch (JSONException e) {
                Log.d(TAG, "Error ", e);
            }

            return null;
        }
    }
}