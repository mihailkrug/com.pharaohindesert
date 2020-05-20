package com.pharaohindesert;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class DesertActivity extends AppCompatActivity  implements ViewSwitcher.ViewFactory, GestureDetector.OnGestureListener{
    private ImageSwitcher mImageSwitcher;
    private static final int REQUEST_CAMERA = 0;
    ImageView share, download, idesktop;
    int position = 0;
    private int[] mImageIds = { R.drawable.img1, R.drawable.img2,
            R.drawable.img3,  R.drawable.img4,
            R.drawable.img5,  R.drawable.img6,  R.drawable.img7,  R.drawable.img8,  R.drawable.img9};
    private String[] mImageName = {"pic_1", "pic_2", "pic_3", "pic_4", "pic_5", "pic_6", "pic_7", "pic_8", "pic_9"};

    private GestureDetector mGestureDetector;
    private static final int SWIPE_MIN_DISTANCE = 130;
    private static final int SWIPE_MAX_OFF_PATH = 260;
    private static final int SWIPE_THRESHOLD_VELOCITY = 101;

    Bitmap bitmap;
    String imagePath;
    Uri URI;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desert_activity);
        unloadIm();
    }
    void unloadIm(){
        bitmap = BitmapFactory.decodeResource(getResources(), mImageIds[0]);


        share = findViewById(R.id.share);
        download = findViewById(R.id.download);
        idesktop = findViewById(R.id.idesktop);
        share.setOnClickListener((View v) -> {

            share();

        });
        download.setOnClickListener((View v) -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestCameraPermission();
            } else {
                img();
            }

        });
        idesktop.setOnClickListener((View v) -> {

            wallpaper();
        });

        mImageSwitcher = findViewById(R.id.imSwitch);
        mImageSwitcher.setFactory(this);


        animateIm();
        mGestureDetector = new GestureDetector(this, this);
    }

    void animateIm(){
        Animation inAnimation = new AlphaAnimation(0, 1);
        inAnimation.setDuration(1200);
        Animation outAnimation = new AlphaAnimation(1, 0);
        outAnimation.setDuration(1200);

        mImageSwitcher.setInAnimation(inAnimation);
        mImageSwitcher.setOutAnimation(outAnimation);

        mImageSwitcher.setImageResource(mImageIds[0]);
    }

    void requestCameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(DesertActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA);
        }
    }
    public void share() {

        Intent shareIntent = new Intent("android.intent.action.MAIN");
        shareIntent.setAction(Intent.ACTION_SEND);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/mipmap/" + mImageName[position]);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "image share"));

    }
    public void wallpaper() {
        bitmap = BitmapFactory.decodeResource(getResources(), mImageIds[position]);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Toast.makeText(getApplicationContext(), "Wallpaper set", Toast.LENGTH_SHORT).show();
        }
    }
    public void img(){

        bitmap = BitmapFactory.decodeResource(getResources(), mImageIds[position]);

        imagePath = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                mImageName[position],
                mImageName[position]
        );

        URI = Uri.parse(imagePath);

        Toast.makeText(DesertActivity.this, "Saved" , Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    public void setPositionNext() {
        position++;
        if (position > mImageIds.length - 1) {
            position = 0;
        }
    }

    public void setPositionPrev() {
        position--;
        if (position < 0) {
            position = mImageIds.length - 1;
        }
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new
                ImageSwitcher.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundColor(0xFF000000);
        return imageView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }



    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // справа налево
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                setPositionNext();
                mImageSwitcher.setImageResource(mImageIds[position]);
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // слева направо
                setPositionPrev();
                mImageSwitcher.setImageResource(mImageIds[position]);
            }
        } catch (Exception e) {
            // nothing
            return true;
        }
        return true;
    }
}
