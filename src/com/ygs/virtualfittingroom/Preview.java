package com.ygs.virtualfittingroom;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.Surface;


public class Preview extends Activity implements SurfaceHolder.Callback{
	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	Button buttonStartCameraPreview, buttonStopCameraPreview;
	boolean previewing = false;
	LinearLayout get_more;
	private ImageView image;
	Global global;
	private Button buttonBackCameraPreview;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_preview);
			global = (Global)getApplicationContext();
			buttonStartCameraPreview = (Button) findViewById(R.id.startcamerapreview);
			buttonStopCameraPreview = (Button) findViewById(R.id.stopcamerapreview);
			buttonBackCameraPreview = (Button)findViewById(R.id.backcamerapreview);
			surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
			image = (ImageView) findViewById(R.id.imageView1);
			image.setImageBitmap(global.getPreview());
			surfaceHolder = surfaceView.getHolder();
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		buttonBackCameraPreview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!previewing)
		{
								int cameraCount = 0;
							   
							    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
							    cameraCount = Camera.getNumberOfCameras();
							    for ( int camIdx = 0; camIdx < cameraCount; camIdx++ ) {
							        Camera.getCameraInfo( camIdx, cameraInfo );
							        
							        if ( cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK  ) {
							            try {
							                camera = Camera.open( camIdx );
							                setCameraDisplayOrientation(Preview.this,camIdx,camera);
							            } catch (RuntimeException e) {
							                Log.e("TAG", "Camera failed to open: " + e.getLocalizedMessage());
							            }
							        }
							    }

			//camera = Camera.open();
			if (camera != null)
				{
					try
						{
							camera.setPreviewDisplay(surfaceHolder);
							Camera.Parameters param = camera.getParameters();
							param.set("orientation", "portrait");
							camera.setParameters(param);
							camera.startPreview();
							previewing = true;
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
		}

			}
		});
			buttonStartCameraPreview.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
						{
							// TODO Auto-generated method stub
								if (!previewing)
			{
									int cameraCount = 0;
								   
								    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
								    cameraCount = Camera.getNumberOfCameras();
								    for ( int camIdx = 0; camIdx < cameraCount; camIdx++ ) {
								        Camera.getCameraInfo( camIdx, cameraInfo );
								        if ( cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT  ) {
								            try {
								                camera = Camera.open( camIdx );
								                setCameraDisplayOrientation(Preview.this,camIdx,camera);
								            } catch (RuntimeException e) {
								                Log.e("TAG", "Camera failed to open: " + e.getLocalizedMessage());
								            }
								        }
								    }

				//camera = Camera.open();
				if (camera != null)
					{
						try
							{
								camera.setPreviewDisplay(surfaceHolder);
								camera.startPreview();
								previewing = true;
							} catch (IOException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
			}

						}
				});
			buttonStopCameraPreview.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
						{
							// TODO Auto-generated method stub
							if (camera != null && previewing)
								{
									camera.stopPreview();
									camera.release();
									camera = null;
									previewing = false;
								}

						}
				});
			

		}
		public static void setCameraDisplayOrientation(Activity activity,
	         int cameraId, android.hardware.Camera camera) {
	   android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
	   android.hardware.Camera.getCameraInfo(cameraId, info);
	   int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
	   int degrees = 0;
	   switch (rotation) {
	         case Surface.ROTATION_0: degrees = 0; break;
	         case Surface.ROTATION_90: degrees = 90; break;
	         case Surface.ROTATION_180: degrees = 180; break;
	         case Surface.ROTATION_270: degrees = 270; break;
	   }

	   int result;
	   if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	       result = (info.orientation + degrees) % 360;
	       result = (360 - result) % 360;  // compensate the mirror
	   } else {  // back-facing
	       result = (info.orientation - degrees + 360) % 360;
	   }
	   camera.setDisplayOrientation(result);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
		{
			// TODO Auto-generated method stub

		}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
		{
			// TODO Auto-generated method stub

		}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
		{
			// TODO Auto-generated method stub

		}


}
