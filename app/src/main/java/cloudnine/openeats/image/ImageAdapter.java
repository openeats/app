package cloudnine.openeats.image;

import android.app.ActionBar;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Objects;

import cloudnine.openeats.R;
import cloudnine.openeats.modal.FoodImage;

/**
 * Created by gaurang on 11/10/15.
 */
public class ImageAdapter extends ArrayAdapter {

    private Context mContext;
    private FoodImage[] imageArray;

    public ImageAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    public ImageAdapter(Context context, int resource, FoodImage[] objects) {
        super(context, resource, objects);
        imageArray = objects;
        mContext = context;
    }

    public ImageAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }


    @Override
    public int getCount() {
        return imageArray.length;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(Object item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            int dpMeasurement = getDpMeasurements(155);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setLayoutParams(new ViewGroup.LayoutParams(dpMeasurement,dpMeasurement));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.getOverlay().clear();
            imageView.getOverlay().add(imageArray[position].getImageOverLay());
            getNumberOfColumsForLargePreview();
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(R.drawable.food1);
        return imageView;
    }

    private void getNumberOfColumsForLargePreview() {
        float dpWidth = getDpWidth();

    }

    private int getDpMeasurements(int pixelMesurement){
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();

        return pixelMesurement*(displayMetrics.densityDpi/160);
    }

    private float getDpWidth() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();

        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        return dpWidth;
    }
}
