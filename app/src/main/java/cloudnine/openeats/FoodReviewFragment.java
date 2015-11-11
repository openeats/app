package cloudnine.openeats;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

    private CardViewAdapter mAdapter;
    // private ArrayList<String> mItems;
    OnItemTouchListener itemTouchListener;
    RecyclerView mRecyclerView;
    //List<FetchFoodAsyncTask.FoodModel> foodModels;


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
        //  mItems = new ArrayList<>(30);
        // for (int i = 0; i < 30; i++) {
        //     mItems.add(String.format("Card number %02d", i));
        //   }

        //   mAdapter = new CardViewAdapter(mItems, itemTouchListener, getContext());

         mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FetchFoodAsyncTask asyncTask = new FetchFoodAsyncTask(this, getActivity());
        asyncTask.execute();

        // recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        public List<FetchFoodAsyncTask.FoodModel> foodModels;
        private OnItemTouchListener onItemTouchListener;
        private Context context;

        public CardViewAdapter(List<FetchFoodAsyncTask.FoodModel> foodModels, OnItemTouchListener onItemTouchListener, Context mContext) {
            this.foodModels = foodModels;
            this.onItemTouchListener = onItemTouchListener;
            this.context = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_food_review, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            //viewHolder.title.setText(cards.get(i));
            FetchFoodAsyncTask.FoodModel foodData = foodModels.get(i);
            viewHolder.name1.setText(foodData.userName);
            Picasso.with(viewHolder.itemView.getContext()).load(foodData.foodImageUrl).into(viewHolder.image1);

            Glide.with(viewHolder.image1.getContext())
                    .load(foodData.foodImageUrl)
                    .fitCenter()
                    .into(viewHolder.image1);

        }

        @Override
        public int getItemCount() {
            return foodModels == null ? 0 : foodModels.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView name1;
            private ImageView image1;

            public ViewHolder(View itemView) {
                super(itemView);
                name1 = (TextView) itemView.findViewById(R.id.name);
                image1 = (ImageView) itemView.findViewById(R.id.image);
            }
        }
    }

    public class FetchFoodAsyncTask extends AsyncTask<Void, Void, List<FetchFoodAsyncTask.FoodModel>> {

        public class FoodModel {
            public String userName;
            public String foodImageUrl;
            public String imgId;

        }

        private FoodReviewFragment reviewFragment;
        private Context context;


        public FetchFoodAsyncTask(FoodReviewFragment reviewFragment, Context context) {
            this.reviewFragment = reviewFragment;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(final List<FoodModel> foodModelList) {


            mAdapter = new CardViewAdapter(foodModelList, itemTouchListener, context);
            reviewFragment.mRecyclerView.setAdapter(mAdapter);

            SwipeableRecyclerViewTouchListener swipeTouchListener =
                    new SwipeableRecyclerViewTouchListener(mRecyclerView,
                            new SwipeableRecyclerViewTouchListener.SwipeListener() {
                                @Override
                                public boolean canSwipe(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                    for (int position : reverseSortedPositions) {
                                       // Toast.makeText(RateActivity.this, mItems.get(position) + " swiped left", Toast.LENGTH_SHORT).show();
                                        mAdapter.foodModels.remove(position);
                                        mAdapter.notifyItemRemoved(position);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                    for (int position : reverseSortedPositions) {
//                                    Toast.makeText(RateActivity.this, mItems.get(position) + " swiped right", Toast.LENGTH_SHORT).show();
                                        mAdapter.foodModels.remove(position);
                                        mAdapter.notifyItemRemoved(position);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            });

            mRecyclerView.addOnItemTouchListener(swipeTouchListener);
        }

        @Override
        protected List<FetchFoodAsyncTask.FoodModel> doInBackground(Void... voids) {
            String foodJsonData = fetchJsonData();
            List<FetchFoodAsyncTask.FoodModel> foodModelList = parseJsonData(foodJsonData);
            return foodModelList;
        }

        private String fetchJsonData() {
            Uri foodDataFetchUri = Uri.parse(context.getString(R.string.food_data_fetch_url)).
                    buildUpon().
                    appendQueryParameter(context.getString(R.string.userid_param), UtilClass.getUserId(context)).
                    appendQueryParameter(context.getString(R.string.limit_param),  "99").
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
                    Log.d(TAG, "Fetched food data successfully");
                } else {
                    Log.d(TAG, "food data fetch failed");
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

        private List<FetchFoodAsyncTask.FoodModel> parseJsonData(String jsonData) {
            try {
                JSONArray rootObj = new JSONArray(jsonData);
                List<FoodReviewFragment.FetchFoodAsyncTask.FoodModel> foodDataList = new ArrayList<>(rootObj.length());
                for (int i = 0; i < rootObj.length(); i++) {
                    JSONObject foodObj = rootObj.getJSONObject(i);
                    FetchFoodAsyncTask.FoodModel foodData = new FetchFoodAsyncTask.FoodModel();


                    foodData.userName = foodObj.getJSONObject("user").getString("name");
                    foodData.foodImageUrl = foodObj.getJSONObject("images").getString("small");
                    foodData.imgId = foodObj.getString("id");

                    foodDataList.add(foodData);
                }

                return foodDataList;
            } catch (JSONException e) {
                Log.d(TAG, "Error ", e);
            }

            return null;
        }

    }
}




