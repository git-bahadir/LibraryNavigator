package for_bilkent_students.librarynavigator;


import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;


import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouch.OnImageViewTouchDoubleTapListener;
import it.sephiroth.android.library.imagezoom.ImageViewTouch.OnImageViewTouchSingleTapListener;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.OnDrawableChangeListener;
import it.sephiroth.android.library.imagezoom.utils.DecodeUtils;


import static for_bilkent_students.librarynavigator.R.drawable.kat2a;

public class FloorView extends Activity {

    private static final String LOG_TAG = "image-test";

    ImageViewTouch mImage;
    Button mButton1;
    Button mButton2;
    Point marker;
    Shelf[] shelves  = {new Shelf("PN130", "PS145", new Point(1000,1000)), new Shelf("PS146","PQ167",new Point(142,155))
            , new Shelf("SA132","SS", new Point(26,10))};
    // CheckBox mCheckBox;
   // Floor floor2 = new Floor("B", "CS");
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  Library demo = new Library();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_floor_view);
        Bundle callNumberData = getIntent().getExtras();
        String callNumber = callNumberData.getString("callNumber");
       marker = new Point(processCallNumber(callNumber));

        Toast.makeText(this, "ImageViewTouch.VERSION: " + ImageViewTouch.VERSION, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "call Number " + callNumber, Toast.LENGTH_LONG).show();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mImage = (ImageViewTouch) findViewById(R.id.image);

        // set the default image display type
        mImage.setDisplayType(DisplayType.FIT_IF_BIGGER);

        mButton1 = (Button) findViewById(R.id.button);
        mButton2 = (Button) findViewById(R.id.button2);
        // mCheckBox = (CheckBox) findViewById(R.id.checkbox1);

        mButton1.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            selectRandomImage(false); //mCheckBox.isChecked()
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        mButton2.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        int current = mImage.getDisplayType().ordinal() + 1;
                        if (current >= DisplayType.values().length) {
                            current = 0;
                        }

                        mImage.setDisplayType(DisplayType.values()[current]);
                    }
                }
        );

        mImage.setSingleTapListener(
                new OnImageViewTouchSingleTapListener() {

                    @Override
                    public void onSingleTapConfirmed() {
                        Log.d(LOG_TAG, "onSingleTapConfirmed");
                    }
                }
        );

        mImage.setDoubleTapListener(
                new OnImageViewTouchDoubleTapListener() {

                    @Override
                    public void onDoubleTap() {
                        Log.d(LOG_TAG, "onDoubleTap");
                    }
                }
        );

        mImage.setOnDrawableChangedListener(
                new OnDrawableChangeListener() {

                    @Override
                    public void onDrawableChanged(Drawable drawable) {
                        Log.i(LOG_TAG, "onBitmapChanged: " + drawable);
                    }
                }
        );
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    Matrix imageMatrix;

    public void selectRandomImage(boolean small) throws IOException {
        //Cursor c = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        // if (c != null) {
        // int count = c.getCount();
        // int position = (int) (Math.random() * count);
        // int position = 0;
        //  if (c.moveToPosition(position)) {
        //  long id = c.getLong(c.getColumnIndex(MediaStore.Images.Media._ID));
        // Uri imageUri2 = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + id);
        // mImage.setAlpha(127);

        Uri imageUri2 = Uri.parse("android.resource://for_bilkent_students.librarynavigator/drawable/kat2a");
        // BitmapFactory.Options options = DecodeUtils.getDefaultOptions();
        //  InputStream stream = new InputStream() {
        //   }DecodeUtils.openInputStream( this, imageUri2);
        // BitmapFactory.decodeStream(stream, null, options);
        // stream.reset();
        // Bitmap bitmap = BitmapFactory.decodeStream( stream, null, options );
        Drawable myIcon = getResources().getDrawable(R.drawable.kat2a);
        Bitmap bitmap = drawableToBitmap(myIcon);
        // mark(bitmap, "here", new Point(5,5), 255, 2, 5, false);
        bitmap = onDraw(bitmap);
        //  bitmap.recycle(); // ben ekledim

        //  Bitmap bitmap = ((BitmapDrawable)d).getBitmap();

        Log.d("image", imageUri2.toString());

        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        int size = (int) (Math.min(metrics.widthPixels, metrics.heightPixels) / 0.55);

        if (small) {
            size /= 3;
        }

        //  Bitmap bitmap = DecodeUtils.decode(this, imageUri2, size, size);
        Toast.makeText(this, imageUri2.toString() + "       " + bitmap, Toast.LENGTH_LONG).show();
        //  Bitmap overlay = getOverlayBitmap("circle-black-medium.png");

        if (null != bitmap) {
            Log.d(LOG_TAG, "screen size: " + metrics.widthPixels + "x" + metrics.heightPixels);
            Log.d(LOG_TAG, "bitmap size: " + bitmap.getWidth() + "x" + bitmap.getHeight());

            mImage.setOnDrawableChangedListener(
                    new OnDrawableChangeListener() {
                        @Override
                        public void onDrawableChanged(final Drawable drawable) {
                            Log.v(LOG_TAG, "image scale: " + mImage.getScale() + "/" + mImage.getMinScale());
                            Log.v(LOG_TAG, "scale type: " + mImage.getDisplayType() + "/" + mImage.getScaleType());

                        }
                    }
            );
            mImage.setImageBitmap(bitmap, null, -1, -1);

        } else {
            Toast.makeText(this, "Failed to load the image", Toast.LENGTH_LONG).show();
        }

        //  c.close();
        return;

    }
/*
    private Bitmap getOverlayBitmap(String name) {
        String file = null;

        if (TextUtils.isEmpty(name)) {
            try {
                String[] files = getAssets().list("images");

                if (null != files && files.length > 0) {
                    int position = (int) (Math.random() * files.length);
                    file = files[position];
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            file = name;
        }

        try {
            InputStream stream = getAssets().open("images/" + file);
            try {
                return BitmapFactory.decodeStream(stream);
            } finally {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    */

    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());


        // Code to add marker delete this part
        Bitmap marker = BitmapFactory.decodeResource(getResources(),
                R.drawable.pin);
        //  canvas.drawBitmap(marker, 50,50, null);
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        canvas.drawCircle(60, 60, 500, mPaint);


        canvas.restore();

/*
    if (drawLock.availablePermits() <= 0) {
        drawLock.release();
    }
*/
        drawable.draw(canvas);

        return bitmap;
    }


/*
    public static Bitmap mark(Bitmap src, String watermark, Point location, int color, int alpha, int size, boolean underline) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAlpha(alpha);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setUnderlineText(underline);
        canvas.drawText(watermark, location.x, location.y, paint);

        return result;
    }
    */

    public Bitmap onDraw(Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();
        BitmapFactory.Options opts = new BitmapFactory.Options(); //
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inDither = true; // for the out of memory error
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());

        Canvas canvas = new Canvas(result);
        // super.onDraw(canvas);

        // Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.kat2a);
        canvas.drawBitmap(src, 0, 0, null);

        // Bitmap marker = BitmapFactory.decodeResource(getResources(), R.drawable.pin);
        // canvas.drawBitmap(marker, w/2, h/2, null);
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);

        canvas.drawCircle(marker.x, marker.y, 50, mPaint);
        // canvas.drawBitmap(marker, xPositionFor2ndMarker, yPositionFor2ndMarker, null);
        return result;
    }



    public Point processCallNumber(String callNumber) {
      //  Library demo = new Library();
        for(int i = 0; i < 4; i++ ) {
            if(shelves[i].checkShelf(callNumber))
                return shelves[i].getShelfLocation();
        }
            return null;
    }

}
