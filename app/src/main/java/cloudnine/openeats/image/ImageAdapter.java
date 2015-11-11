package cloudnine.openeats.image;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cloudnine.openeats.R;
import cloudnine.openeats.modal.OpenEatsImage;
import cloudnine.openeats.modal.Post;

/**
 * Created by gaurang on 11/10/15.
 */
public class ImageAdapter extends ArrayAdapter<Post> {

    private Context mContext;
    private Post[] imageArray;

    public ImageAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    public ImageAdapter(Context context, int resource, Post[] objects) {
        super(context, resource, objects);
        imageArray = objects;
        mContext = context;
    }

//    public ImageAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
//        super(context, resource, textViewResourceId, objects);
//    }


    @Override
    public int getCount() {
        return imageArray.length;
    }

//    @Override
//    public Object getItem(int position) {
//        return super.getItem(position);
//    }
//
//    @Override
//    public int getPosition(Object item) {
//        return super.getPosition(item);
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        OpenEatsImage image = imageArray[position].getImages();

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);

            if(image != null){
                Glide.with(mContext)
                        .load(image.getLargeUrl())
                        .fitCenter()
                        .into(imageView);

//                int dpMeasurement = getDpMeasurements(155);
//                imageView.setLayoutParams(new ViewGroup.LayoutParams(dpMeasurement, dpMeasurement));
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.getOverlay().clear();
                imageView.getOverlay().add(imageArray[position].getImageOverLay());

            }
            else{
                imageView.setImageResource(R.drawable.food1);
            }

        } else {
            imageView = (ImageView) convertView;

            if(image != null){
                Glide.with(mContext)
                        .load(image.getLargeUrl())
                        .fitCenter()
                        .into(imageView);
                imageView.getOverlay().clear();
                imageView.getOverlay().add(imageArray[position].getImageOverLay());
            }
            else
                imageView.setImageResource(R.drawable.food1);


        }


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
