package com.ygs.virtualfittingroom;

import java.util.ArrayList;
import java.util.List;

import com.ygs.virtualfittingroom.entity.Picture;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Global extends Application {

	private Bitmap bg;
	private List<Picture> pictureList = null;
	private List<String> textList = new ArrayList<String>();
	private Bitmap preview;
	public Global() {
		// TODO Auto-generated constructor stub
	
		textList.add("HoT!");
		textList.add("FUZZ!");
		textList.add("WON!");
		textList.add("Singer");
		
	}
	public void setPreview(Bitmap preview1,int width,int height) {
		if (preview != null)
			preview.recycle();
		preview = null;
		preview = decodeSampledBitmap(preview1, width, height);
	}
	public Bitmap getPreview() {
		return preview;
	}
public List<String> getTextList() {
	return textList;
}
	public List<Picture> getPictureList() {
		if(pictureList == null)
			pictureList = new ArrayList<Picture>();
		return pictureList;
	}
	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}
	public void loadBitmapFromFile(String filename,int width,int height){
		bg = decodeSampledBitmapFromFile(filename, width, height);
	}
	public void loadBitmap(int id,int width,int height) {
		bg = decodeSampledBitmapFromResource(getResources(), id, width, height);

	}
	public Bitmap getBg() {
		return bg;
	}
	public void unloadBitmap() {
		if (bg != null)
			bg.recycle();
		bg = null;
		
	}

	public void setImage(Bitmap bmp,int width,int height){
		unloadBitmap();
		loadBitmap(bmp,width,height);
	}
	private void loadBitmap(Bitmap bmp, int width, int height) {
		bg = decodeSampledBitmap(bmp, width, height);
	}
	private Bitmap decodeSampledBitmap(Bitmap bmp, int width, int height) {
		// TODO Auto-generated method stub
		Bitmap mBitmap = Bitmap.createScaledBitmap(bmp, width,
				height, true);
		return mBitmap;
	}
	public void setImage(int sourceid,int width,int height) {
		unloadBitmap();
		loadBitmap(sourceid,width,height);
	}
	public void setFileImage(String filename,int width,int height) {
		//unloadBitmap();
		loadBitmapFromFile(filename,width,height);
	}
	public static Bitmap decodeSampledBitmapFromFile(String filename,
            int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

       
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }

	 public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	            int reqWidth, int reqHeight) {

	        // First decode with inJustDecodeBounds=true to check dimensions
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeResource(res, resId, options);

	        // Calculate inSampleSize
	        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	        
	        // Decode bitmap with inSampleSize set
	        options.inJustDecodeBounds = false;
	        return BitmapFactory.decodeResource(res, resId, options);
	    }
	 public static int calculateInSampleSize(BitmapFactory.Options options,
	            int reqWidth, int reqHeight) {
	        // Raw height and width of image
	        final int height = options.outHeight;
	        final int width = options.outWidth;
	        int inSampleSize = 1;

	        if (height > reqHeight || width > reqWidth) {

	            // Calculate ratios of height and width to requested height and width
	            final int heightRatio = Math.round((float) height / (float) reqHeight);
	            final int widthRatio = Math.round((float) width / (float) reqWidth);

	            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
	            // with both dimensions larger than or equal to the requested height and width.
	            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

	            // This offers some additional logic in case the image has a strange
	            // aspect ratio. For example, a panorama may have a much larger
	            // width than height. In these cases the total pixels might still
	            // end up being too large to fit comfortably in memory, so we should
	            // be more aggressive with sample down the image (=larger inSampleSize).

	            final float totalPixels = width * height;

	            // Anything more than 2x the requested pixels we'll sample down further
	            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

	            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
	                inSampleSize++;
	            }
	        }
	        return inSampleSize;
	    }

}
