package cloudnine.openeats.image;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import cloudnine.openeats.R;
import cloudnine.openeats.modal.Post;

/**
 * Created by gaurang on 11/10/15.
 */
public class RecycleViewImageAdapter extends RecyclerView.Adapter<RecycleViewImageAdapter.RecyclerViewImageHolder> {
    private List<Post> itemList;
    private Context context;

    public RecycleViewImageAdapter(Context context, List<Post> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_food_image,null);
        return new RecyclerViewImageHolder(imageView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewImageHolder holder, int position) {
        holder.foodPhoto.setImageResource(R.drawable.food1);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class RecyclerViewImageHolder extends RecyclerView.ViewHolder{

        ImageView foodPhoto;

        public RecyclerViewImageHolder(View itemView) {
            super(itemView);
            foodPhoto = (ImageView)itemView.findViewById(R.id.food);
        }
    }
}
