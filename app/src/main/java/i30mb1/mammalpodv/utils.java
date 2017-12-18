package i30mb1.mammalpodv;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class utils {
    private static Bitmap getBitmapFromAssets(Context context, String filepath) {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;

        try {
            istr = assetManager.open(filepath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException ioe) {
            // manage exception
        } finally {
            if (istr != null) {
                try {
                    istr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
    public static void startSpriteAnim(Context context, final ImageView img, String nameSprite, Boolean isLooped, int width, int hight, int duration, int scale) {
        try {
            Bitmap birdBmp = getBitmapFromAssets(context, nameSprite);
            if (birdBmp != null) {
                // cut bitmaps from bird to array of bitmaps
                Bitmap[] bmps;
                int FRAME_W = width;
                int FRAME_H = hight;
                int NB_FRAMES = (birdBmp.getWidth() / width);
                int FRAME_DURATION = duration; // in ms !
                int SCALE_FACTOR = scale;
                int COUNT_X = (birdBmp.getWidth() / width);
                int COUNT_Y = 1;

                bmps = new Bitmap[NB_FRAMES];
                int currentFrame = 0;

                for (int i = 0; i < COUNT_Y; i++) {
                    for (int j = 0; j < COUNT_X; j++) {
                        bmps[currentFrame] = Bitmap.createBitmap(birdBmp, FRAME_W * j, FRAME_H * i, FRAME_W, FRAME_H);
                        // apply scale factor
                        bmps[currentFrame] = Bitmap.createScaledBitmap(bmps[currentFrame], FRAME_W * SCALE_FACTOR, FRAME_H * SCALE_FACTOR, true);
                        if (++currentFrame >= NB_FRAMES) {
                            break;
                        }
                    }
                }
                // create animation programmatically
                final AnimationDrawable animation = new AnimationDrawable();
                animation.setOneShot(isLooped); // repeat animation

                for (int i = 0; i < NB_FRAMES; i++) {
                    animation.addFrame(new BitmapDrawable(context.getResources(), bmps[i]), FRAME_DURATION);
                }
                // load animation on image
                img.setImageDrawable(animation);
                // start animation on image
                img.post(new Runnable() {
                    @Override
                    public void run() {
                        animation.start();
                    }
                });
            }
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
    }
}
