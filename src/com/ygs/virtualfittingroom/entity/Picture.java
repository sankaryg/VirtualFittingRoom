package com.ygs.virtualfittingroom.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;

public class Picture {
	/**
	 * @return the bitmap
	 */
	public Bitmap getBitmap() {
		return bitmap;
	}

	Bitmap selArrowLeft;
	Bitmap selArrowRight;
	Bitmap selArrowBottom;
	Bitmap selArrowTop;
	boolean doingRotation;
	 /**
	 * @return the doingRotation
	 */
	public boolean isDoingRotation() {
		return doingRotation;
	}

	/**
	 * @param doingRotation the doingRotation to set
	 */
	public void setDoingRotation(boolean doingRotation) {
		 this.doingRotation = doingRotation;
		
	}
	public void  toggleRotation()
	{
		 
			doingRotation=!doingRotation;
	}

	int paddingLeft=70;//left &right
	 int paddingTop=70;//top & bottom
	 int topLineLength=30;
	 int padding=3;
		
	 int screenWidth, screenHeight;
	 float rotationAngle;
	/**
	 * @return the rotationAngle
	 */
	public float getRotationAngle() {
		return rotationAngle;
	}

	/**
	 * @param rotationAngle the rotationAngle to set
	 */
	public void setRotationAngle(float rotationAngle) {
		this.rotationAngle = rotationAngle;
	}

	public Rect getSrcRect(Canvas canvas) {

		int h = (int) (y + getHeight());

		return new Rect((int) x, (int) y, (int) (x + getWidth()), h);

	}

	public Rect getDestRect(Canvas canvas) {

		if (scaleFactor == 1) {
			int h = (int) (y + getHeight());

			return new Rect((int) x, (int) y, (int) (x + getWidth()), h);
		}
		int width = (int) (getWidth() * scaleFactor);
		int height = (int) (getHeight() * scaleFactor);

		int newX = (int) (getCenterX() - width / 2);
		int newY = (int) (getCenterY() - height / 2);

		return new Rect(newX, newY, newX + width, newY + height);

	}

	/**
	 * @param bitmap
	 *            the bitmap to set
	 */
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	/**
	 * @return the x
	 */
	public float getX() {

		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @param bitmap
	 * @param x
	 * @param y
	 */
	public Picture(Bitmap bitmap, int x, int y,int screenWidth, int screenHeight) {
		super();
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.screenWidth=screenWidth;
		this.screenHeight=screenHeight;	
	}
	
	

	Bitmap bitmap;
	float x = 0;
	float y = 0;
	float scaleFactor = .5f;
	private Bitmap rotateIcn;
	private Bitmap rotateHighlightedIcn;
	
	private Bitmap deleteIcn;

	/**
	 * @return the scaleFactor
	 */
	public float getScaleFactor() {
		return scaleFactor;
	}

	/**
	 * @param scaleFactor
	 *            the scaleFactor to set
	 */
	public void setScaleFactor(float scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	public float getCenterX() {

		float centerX = x + bitmap.getWidth() * scaleFactor / 2;
		if (centerX < 0) {
			centerX = 0;
		}
		if(centerX>screenWidth)
		{
			centerX=screenWidth;
			
		}
		return centerX;

	}

	public float getCenterY() {
		float centerY = y + bitmap.getHeight() * scaleFactor / 2;
		if (centerY < 0) {
			centerY = 0;
		}
		/*if(centerY>=screenHeight-bitmap.getHeight()* scaleFactor/2)
			centerY=screenHeight-bitmap.getHeight()* scaleFactor/2 ;
		return centerY;*/
		if(centerY>screenHeight)
		{
			centerY=screenHeight;
			
		}
		return centerY;
	}

	
	public void setCenter(float x2, float y2)
	{
		x=x2-bitmap.getWidth() * scaleFactor / 2;
		y=y2 - bitmap.getHeight() * scaleFactor / 2;
		
		float yEdge=y2 + bitmap.getHeight() * scaleFactor / 2;
		if(x<0)
			 x=0;
		if(y<0)
			y=0;
		
		if(x>screenWidth)
		{
			x=screenWidth;
			
		}
		if(y>screenHeight)
			
			y=screenHeight ;
	}
	public float getWidth() {
		return bitmap.getWidth();
	}

	public float getHeight() {
		return bitmap.getHeight();
	}

	public boolean isTouched(float x2, float y2) {
		// TODO Auto-generated method stub

		float width = getWidth();
		float height = getHeight();
		// if(scaleFactor>1)
		width = getWidth() * scaleFactor;
		// if(scaleFactor>1)
		height = getHeight() * scaleFactor;
		int newX = (int) (getCenterX() - width / 2);
		int newY = (int) (getCenterY() - height / 2);

		if (x2 >= getX()-paddingLeft && x2 < (getX() + width+paddingLeft+deleteIcn.getWidth()) && y2 >= getY()-paddingTop//-topLineLength-rotateIcn.getHeight()
				&& y2 < (getY() + height+paddingTop))

			return true;
		else
			return false;
	}
 
	
	public boolean isPictureTouched(float x2, float y2) {
		// TODO Auto-generated method stub

		float width = getWidth();
		float height = getHeight();
		// if(scaleFactor>1)
		width = getWidth() * scaleFactor;
		// if(scaleFactor>1)
		height = getHeight() * scaleFactor;
		int newX = (int) (getCenterX() - width / 2);
		int newY = (int) (getCenterY() - height / 2);

		if (x2 >= getX()  && x2 < (getX() + width) && y2 >= getY() 
				&& y2 < (getY() + height ))

			return true;
		else
			return false;
	}
 
	

	
	public boolean isDeleteTouched(float x2, float y2) {
		// TODO Auto-generated method stub

		float width = getWidth();
		float height = getHeight();
		// if(scaleFactor>1)
		width = getWidth() * scaleFactor;
		// if(scaleFactor>1)
		height = getHeight() * scaleFactor;
		int newX = (int) (getCenterX() - width / 2);
		int newY = (int) (getCenterY() - height / 2);

		if (x2 >= getX()+width+paddingLeft-padding && x2 < (getX() + width+paddingLeft+deleteIcn.getWidth()) && y2 < getY()+paddingTop-deleteIcn.getHeight() && y2>getY()-paddingTop-topLineLength
			 )

			return true;
		else
			return false;
		 
	}
	 
	public boolean isRotateTouched(float x2, float y2) {
		// TODO Auto-generated method stub

		float width = getWidth();
		float height = getHeight();
		// if(scaleFactor>1)
		width = getWidth() * scaleFactor;
		// if(scaleFactor>1)
		height = getHeight() * scaleFactor;
		int newX = (int) (getCenterX() - width / 2);
		int newY = (int) (getCenterY() - height / 2);

		if (x2 >= getX()+width/2-padding && x2 < (getX() + width/2+padding) && y2 > getY()-paddingTop-topLineLength -rotateIcn.getHeight() && y2< getY()-paddingTop-topLineLength
			 )

			return true;
		else
			return false;
		 
	}
	public RectF getBoundingRect()
	{
		float width = getWidth();
		float height = getHeight();
		// if(scaleFactor>1)
		width = getWidth() * scaleFactor;
		// if(scaleFactor>1)
		height = getHeight() * scaleFactor;
		return new RectF(getX(),getY(),getX() + width,getY() + height);
	}
	public RectF getCenterRect()
	{
		 
		return new RectF(getCenterX(),getCenterY(),getCenterX() + 2, getCenterY()+2);
	}
	
	public void drawSelectionBox(Canvas canvas, Matrix matrix, Paint mBitmapPaint)
	{
		float xPos1=getX();
		float yPos1=getY();
		float width = getWidth()*scaleFactor;
		float height = getHeight()*scaleFactor;
		 int paddingOnJoint=3;
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		//mPaint.setStrokeJoin(Paint.Join.ROUND);
		//mPaint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(2);
	
		paint.setColor(Color.WHITE);

		canvas.drawLine(getX()-paddingLeft,getY()-paddingTop+paddingOnJoint,getX()-paddingLeft,getY()+ height/2-selArrowLeft.getHeight()/2-padding , paint);
		canvas.drawBitmap(selArrowLeft, getX()-paddingLeft-selArrowLeft.getWidth()/2, getY()+ height/2-selArrowLeft.getHeight()/2, mBitmapPaint);
		canvas.drawLine(getX()-paddingLeft,getY()+ height/2+selArrowLeft.getHeight()/2-padding ,getX()-paddingLeft,getY()+ height+paddingTop-paddingOnJoint, paint);
		
		//top
	 canvas.drawLine(getX()-paddingLeft+paddingOnJoint,getY()-paddingTop,getX() +width/2-selArrowTop.getWidth()/2-padding ,getY()-paddingTop, paint);
		
//--top vertical line
		//if(doingRotation)
		//	canvas.drawBitmap(rotateHighlightedIcn, getX()+width/2 -selArrowTop.getWidth()/2+padding, getY()-paddingTop-topLineLength-padding-rotateIcn.getHeight(), mBitmapPaint);
		
		//else
			
	//	canvas.drawBitmap(rotateIcn, getX()+width/2 -selArrowTop.getWidth()/2+padding, getY()-paddingTop-topLineLength-padding-rotateIcn.getHeight(), mBitmapPaint);
		
		//canvas.drawLine(  getX()+width/2-padding , getY()-paddingTop-topLineLength, getX()+width/2-padding  ,getY()-paddingTop-padding*2,paint);
		
		canvas.drawBitmap(selArrowTop, getX()+width/2-padding-selArrowTop.getWidth()/2, getY()-paddingTop-selArrowTop.getHeight()/2, mBitmapPaint);
		
		canvas.drawBitmap(deleteIcn, getX()+width+paddingLeft -padding-deleteIcn.getWidth()/2, getY()-paddingTop-deleteIcn.getHeight()/2, mBitmapPaint);
		
		
		canvas.drawLine(getX()+width/2+selArrowTop.getWidth()/2-padding ,getY()-paddingTop,getX()+paddingLeft+width-deleteIcn.getWidth()/2-padding,getY()-paddingTop, paint);
		
		//bottom
		canvas.drawLine(getX()-paddingLeft+paddingOnJoint,getY()+paddingTop+height,getX() +width/2-selArrowTop.getWidth()/2-padding ,getY()+paddingTop+height, paint);
		canvas.drawBitmap(selArrowBottom, getX()+width/2-padding-selArrowTop.getWidth()/2, getY()+paddingTop-selArrowTop.getHeight()/2+height, mBitmapPaint);
		canvas.drawLine(getX()+width/2+selArrowTop.getWidth()/2-padding ,getY()+paddingTop+height,getX()+paddingLeft+width-paddingOnJoint,getY()+paddingTop+height, paint);
		
	//-----right
		canvas.drawLine(getX()+paddingLeft+width,getY()-paddingTop+deleteIcn.getHeight()/2+paddingOnJoint,getX()+paddingLeft+width,getY()+ height/2-selArrowLeft.getHeight()/2-padding , paint);
		canvas.drawBitmap(selArrowRight, getX()+paddingLeft+width-selArrowLeft.getWidth()/2+padding, getY()+ height/2-selArrowLeft.getHeight()/2, mBitmapPaint);
		canvas.drawLine(getX()+paddingLeft+width,getY()+ height/2+selArrowLeft.getHeight()/2-padding ,getX()+paddingLeft+width,getY()+ height+paddingTop-paddingOnJoint, paint);
		
	}

	public void setSelectionArrows(Bitmap selArrowLeft2, Bitmap selArrowTop2,
			Bitmap selArrowRight2, Bitmap selArrowBottom2) {
		// TODO Auto-generated method stub
		
		selArrowLeft=selArrowLeft2;
		selArrowTop=selArrowTop2;
		selArrowRight=selArrowRight2;
		selArrowBottom=selArrowBottom2;
		
	}

	public void setRotateIcn(Bitmap rotateIcn) {
		// TODO Auto-generated method stub
		this.rotateIcn=rotateIcn;
	}
	public void setRotateHighlightedIcn(Bitmap rotateIcn) {
		// TODO Auto-generated method stub
		this.rotateHighlightedIcn=rotateIcn;
	}
	public void setDeleteIcn(Bitmap deleteIcn) {
		// TODO Auto-generated method stub
		this.deleteIcn=deleteIcn;
	}

	public void setPositionInbox(float x2, float y2, float pictureTouchXOffset, float pictureTouchYOffset) {
		// TODO Auto-generated method stub
		 
		  
		
		
		x=x2+pictureTouchXOffset;
		y=y2+pictureTouchYOffset;
		if(x<0)
			 x=0;
		if(y<0)
			y=0;
		
		if(x>screenWidth)
		{
			x=screenWidth;
			
		}
		if(y>screenHeight)
			
			y=screenHeight ;

	}

	public float getPictureTouchXOffset(float pictureTouchX) {
		// TODO Auto-generated method stub
		return getX()-pictureTouchX;
	}

	public float getPictureTouchYOffset(float pictureTouchY) {
		// TODO Auto-generated method stub
		return getY()-pictureTouchY;
	}
	
}
