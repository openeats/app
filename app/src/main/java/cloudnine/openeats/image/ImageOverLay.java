package cloudnine.openeats.image;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by gaurang on 11/10/15.
 */

public class ImageOverlay extends Drawable {
    private final Paint mPaint;
    private final RectF mRect;

    public static ImageOverlay getDrawableForHealthyFood(){
        ImageOverlay healthyFoodOverlay = new ImageOverlay();
        healthyFoodOverlay.mPaint.setARGB(100, 0, 255, 0);

        return healthyFoodOverlay;
    }

    public static ImageOverlay getDrawableForUnHealthyFood(){
        ImageOverlay unHealthyFoodOverlay = new ImageOverlay();
        unHealthyFoodOverlay.mPaint.setARGB(100, 255, 0, 0);

        return unHealthyFoodOverlay;

    }

    public static ImageOverlay getDrawableForUnsureFood(){
        ImageOverlay unSureFoodOverlay = new ImageOverlay();
        unSureFoodOverlay.mPaint.setARGB(100, 255, 255, 102);
        return unSureFoodOverlay;
    }


    public ImageOverlay(){
        mPaint = new Paint();
        mRect = new RectF();
    }

    @Override
    public void draw(Canvas canvas) {
        // Set the correct values in the Paint
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);

        // Adjust the rect
        mRect.left = 0.0f;
        mRect.top = 0.0f;
        mRect.right = 1000.0f;
        mRect.bottom = 1000.0f;

        // Draw it
        canvas.drawRoundRect(mRect, 0.5f, 0.5f, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

}