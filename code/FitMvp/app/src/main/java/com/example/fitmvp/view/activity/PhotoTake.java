package com.example.fitmvp.view.activity;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.ImageReader;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import com.example.fitmvp.R;


import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class PhotoTake extends Activity {
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private CameraManager mCameraManager;
    private CameraDevice mCameraDeivce;
    private CameraCaptureSession mCameraSession;
    private CaptureRequest.Builder mRequestBuilder;
    private ImageReader mImageReader;
   // private SavePhotoListener mSaveListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.photo_pass);
        mCameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        mSurfaceView = (SurfaceView) findViewById(R.id.camera);
        mSurfaceHolder = mSurfaceView.getHolder();
//        mSurfaceHolder.addCallback(new SurfaceViewCallBack());
//        mSaveListener = new SavePhotoListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
