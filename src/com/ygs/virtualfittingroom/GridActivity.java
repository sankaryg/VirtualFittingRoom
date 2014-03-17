package com.ygs.virtualfittingroom;

import java.util.ArrayList;
import java.util.Stack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.ygs.virtualfittingroom.adapter.ImageAdapter;
import com.ygs.virtualfittingroom.drawing.Action;
import com.ygs.virtualfittingroom.drawing.AddPictureAction;
import com.ygs.virtualfittingroom.drawing.MovePictureAction;
import com.ygs.virtualfittingroom.drawing.RemovePictureAction;
import com.ygs.virtualfittingroom.drawing.ResizePictureAction;
import com.ygs.virtualfittingroom.drawing.RotateGestureDetector;
import com.ygs.virtualfittingroom.drawing.RotatePictureAction;
import com.ygs.virtualfittingroom.entity.Picture;
import com.ygs.virtualfittingroom.util.ConnectionDetector;
import com.ygs.virtualfittingroom.util.ShowAlert;

public class GridActivity extends BaseActivity{

	private FrameLayout layoutImage;
	private ImageView modelImage;
	private ShowAlert alert;
	private ConnectionDetector connection;
	private GridView grid;
	String img;
	Global global;
	private DrawingPanel panel;
	private Bitmap bitmap;
	private ImageLoader imageLoader;
	private String type;
	private TextView textEnter;
	private EditText edit;
	private Button addBtn;
	protected ImageAdapter adapter;
	private Button next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		global = (Global)getApplicationContext();
		panel = new DrawingPanel(this);//, getResources().getColor(R.color.white));
		panel.setDrawingCacheEnabled(true);
		panel.buildDrawingCache(true);
		setContentView(R.layout.activity_grid);
		
		init();
		
		generateViews();
	}
	
	private void generateViews() {
		
		
	}
	private void init() {
		// TODO Auto-generated method stub
		imageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
		imageLoader.init(config);
		img = getIntent().getExtras().getString("img");
		grid = (GridView)findViewById(R.id.grid_view);
		layoutImage = (FrameLayout) findViewById(R.id.linearLayout2);
		modelImage = (ImageView) findViewById(R.id.imageView1);
		layoutImage.addView(panel);
		textEnter = (TextView)findViewById(R.id.textEnter);
		edit = (EditText)findViewById(R.id.editText1);
		addBtn = (Button)findViewById(R.id.add);
		addBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					global.getTextList().add(edit.getText().toString());
					adapter.notifyDataSetChanged();
			}
		});
		next = (Button)findViewById(R.id.button1);
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bitmap mp = Bitmap.createBitmap(panel.getDrawingCache());
				global.setPreview(mp, canvasWidth, canvasHeight);
				Intent imgIntent = new Intent(GridActivity.this, Preview.class);
				
				startActivity(imgIntent);
				
			}
		});
		type = "img";
		alert = new ShowAlert(this);
		connection = new ConnectionDetector(this);
		grid.setAdapter(new ImageAdapter(this,type));
		if (connection.isConnectingToInternet())
	    {
			aq().id(R.id.imageView1).progress(R.id.progress).image(img, true, true, 200, 0);

	    }
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				if(type.equals("img")){
					type="text";
				final ImageView imageView = (ImageView) view.findViewById(R.id.image);
	            final BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
	            final Bitmap yourBitmap = bitmapDrawable.getBitmap();
	            

	    		
	    		DisplayMetrics displaymetrics = new DisplayMetrics();
	    		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	    		int height = displaymetrics.heightPixels;
	    		int width = displaymetrics.widthPixels;
	    		com.ygs.virtualfittingroom.entity.Picture picture = new com.ygs.virtualfittingroom.entity.Picture(yourBitmap, panel.getWidth()/2,
	    				panel.getHeight()/2, width, height );
	    		picture.setCenter(panel.getWidth()/2, panel.getHeight()/2);
	    		Bitmap selArrowLeft = BitmapFactory.decodeResource(getResources(),
	    				R.drawable.u_sel_arrow_left);
	    		Bitmap selArrowRight = BitmapFactory.decodeResource(getResources(),
	    				R.drawable.u_sel_arrow_right);
	    		Bitmap selArrowBottom = BitmapFactory.decodeResource(getResources(),
	    				R.drawable.u_sel_arrow_down);
	    		Bitmap selArrowTop = BitmapFactory.decodeResource(getResources(),
	    				R.drawable.u_sel_arrow_top);

	    		Bitmap rotateIcn = BitmapFactory.decodeResource(getResources(),
	    				R.drawable.u_sel_rotate_icn);
	    		Bitmap deleteIcn = BitmapFactory.decodeResource(getResources(),
	    				R.drawable.u_sel_delete_icn);
	    		Bitmap rotateHighlightIcn = BitmapFactory.decodeResource(
	    				getResources(), R.drawable.u_sel_rotate_whiteicon);
	    		if(global.getPictureList().size()>0){
	    			Picture lastSelPic  = global.getPictureList().get(0);
	    		RemovePictureAction removePictureAction = new RemovePictureAction(
						lastSelPic);
				global.getPictureList().remove(lastSelPic);
				addToActionList(removePictureAction);
				//selectedPictureId = -1;
				panel.invalidate();
	    		}
	    		global.getPictureList().add(picture);
	    		AddPictureAction pictureAddAction = new AddPictureAction(picture);
	    		addToActionList(pictureAddAction);
	    		picture.setSelectionArrows(selArrowLeft, selArrowTop, selArrowRight,
	    				selArrowBottom);
	    		picture.setRotateIcn(rotateIcn);
	    		picture.setRotateHighlightedIcn(rotateHighlightIcn);
	    		picture.setDeleteIcn(deleteIcn);
	    		
	    		panel.invalidate();
	    		textEnter.setVisibility(View.VISIBLE);
	    		edit.setVisibility(View.VISIBLE);
	    		addBtn.setVisibility(View.VISIBLE);
	    		adapter = new ImageAdapter(GridActivity.this,type);
	    		grid.setAdapter(adapter);
	    		adapter.notifyDataSetChanged();
				}else{
					final TextView imageView = (TextView) view.findViewById(R.id.textView1);
		            imageView.setDrawingCacheEnabled(true);
		            final Bitmap yourBitmap = imageView.getDrawingCache();

		    		DisplayMetrics displaymetrics = new DisplayMetrics();
		    		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		    		int height = displaymetrics.heightPixels;
		    		int width = displaymetrics.widthPixels;
		    		com.ygs.virtualfittingroom.entity.Picture picture = new com.ygs.virtualfittingroom.entity.Picture(yourBitmap, panel.getWidth()/2,
		    				panel.getHeight()/2, width, height );
		    		picture.setCenter(panel.getWidth()/2, panel.getHeight()/2);
		    		Bitmap selArrowLeft = BitmapFactory.decodeResource(getResources(),
		    				R.drawable.u_sel_arrow_left);
		    		Bitmap selArrowRight = BitmapFactory.decodeResource(getResources(),
		    				R.drawable.u_sel_arrow_right);
		    		Bitmap selArrowBottom = BitmapFactory.decodeResource(getResources(),
		    				R.drawable.u_sel_arrow_down);
		    		Bitmap selArrowTop = BitmapFactory.decodeResource(getResources(),
		    				R.drawable.u_sel_arrow_top);

		    		Bitmap rotateIcn = BitmapFactory.decodeResource(getResources(),
		    				R.drawable.u_sel_rotate_icn);
		    		Bitmap deleteIcn = BitmapFactory.decodeResource(getResources(),
		    				R.drawable.u_sel_delete_icn);
		    		Bitmap rotateHighlightIcn = BitmapFactory.decodeResource(
		    				getResources(), R.drawable.u_sel_rotate_whiteicon);
		    		if(global.getPictureList().size()>1){
		    			Picture lastSelPic  = global.getPictureList().get(1);
		    		RemovePictureAction removePictureAction = new RemovePictureAction(
							lastSelPic);
					global.getPictureList().remove(lastSelPic);
					addToActionList(removePictureAction);
					//selectedPictureId = -1;
					panel.invalidate();
		    		}	
		    		global.getPictureList().add(picture);
		    		AddPictureAction pictureAddAction = new AddPictureAction(picture);
		    		addToActionList(pictureAddAction);
		    		picture.setSelectionArrows(selArrowLeft, selArrowTop, selArrowRight,
		    				selArrowBottom);
		    		picture.setRotateIcn(rotateIcn);
		    		picture.setRotateHighlightedIcn(rotateHighlightIcn);
		    		picture.setDeleteIcn(deleteIcn);
		    		
		    		panel.invalidate();

				}
			}
		});
	}
	Stack<Action> actionList = new Stack<Action>();
	Stack<Action> undoneActionList = new Stack<Action>();
	public int canvasWidth;
	public int canvasHeight;
	public ScaleGestureDetector mScaleDetector;
	public RotateGestureDetector mRotateDetector;
	public boolean deleteOnPictureTouched;
	public float pictureTouchX;
	public float pictureTouchY;
	public float pictureTouchXOffset;
	public float pictureTouchYOffset;
	public int selectedPictureId;
	public float mLastTouchX;
	public float mLastTouchY;
	public int mActivePointerId;
	public float mPosX;
	public float mPosY;
	
	public void addToActionList(Action action) {

		if (actionList.size() == 15)
			actionList.removeElementAt(0);
		actionList.add(action);

		//undoImg.setImageResource(R.drawable.u_undo_active);

	}
	class PathPoints {
		private Path path;
		// private Paint mPaint;
		private int color;
		private String textToDraw;
		private boolean isTextToDraw;
		private int x, y;

		public PathPoints(Path path, int color, boolean isTextToDraw) {
			this.path = path;
			this.color = color;
			this.isTextToDraw = isTextToDraw;
		}

		public PathPoints(int color, String textToDraw, boolean isTextToDraw,
				int x, int y) {
			this.color = color;
			this.textToDraw = textToDraw;
			this.isTextToDraw = isTextToDraw;
			this.x = x;
			this.y = y;
		}

		public Path getPath() {
			return path;
		}

		public void setPath(Path path) {
			this.path = path;
		}

		/*
		 * private Paint getPaint() { mPaint = new Paint();
		 * mPaint.setAntiAlias(true); mPaint.setColor(color);
		 * mPaint.setStyle(Paint.Style.STROKE);
		 * mPaint.setStrokeJoin(Paint.Join.ROUND);
		 * mPaint.setStrokeCap(Paint.Cap.ROUND); mPaint.setStrokeWidth(6);
		 * return mPaint; }
		 */

		public int getColor() {
			return color;
		}

		public void setColor(int color) {
			this.color = color;
		}

		public String getTextToDraw() {
			return textToDraw;
		}

		public void setTextToDraw(String textToDraw) {
			this.textToDraw = textToDraw;
		}

		public boolean isTextToDraw() {
			return isTextToDraw;
		}

		public void setTextToDraw(boolean isTextToDraw) {
			this.isTextToDraw = isTextToDraw;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

	}

	public class DrawingPanel extends View {

		private Canvas mCanvas;
		private Path mPath;
		private ArrayList<PathPoints> paths = new ArrayList<PathPoints>();
		private ArrayList<PathPoints> undonePaths = new ArrayList<PathPoints>();

		private Bitmap mBitmap;

		private Bitmap mBitmap2;

		private Bitmap mBitmap3, mBitmap4;

		private int color;
		// private int x, y;
		int highlightColor = getResources().getColor(
				R.color.white);
		PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(
				highlightColor, PorterDuff.Mode.SRC_ATOP);
		public Action currentAction;
		public MovePictureAction currentMoveAction;
		public RotatePictureAction currentRotateAction;

		public DrawingPanel(Context context,AttributeSet attr) {
			super(context,attr);
		}
		public DrawingPanel(Context context) {
			super(context);
			this.color = getResources().getColor(R.color.white);
			setFocusable(true);
			setFocusableInTouchMode(true);

			// this.setOnTouchListener(this);

			mBitmapPaint = new Paint();// Paint.DITHER_FLAG);
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			mPaint.setColor(color);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeWidth(6);
			mPaint.setTextSize(30);

			mPath = new Path();
			paths.add(new PathPoints(mPath, color, false));
			mCanvas = new Canvas();
			eraserPaint = new Paint();

			eraserPaint.setAlpha(0);
			eraserPaint.setXfermode(new PorterDuffXfermode(
					PorterDuff.Mode.DST_IN));
			eraserPaint.setAntiAlias(true);

		}

		public void colorChanged(int color) {
			this.color = color;
			mPaint.setColor(color);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			// mBitmap = AddReportItemActivity.mPhoto;
			// mBitmap =
			// getIntent().getExtras().getParcelable(ChooseActivity.BITMAP);
			/* added by sankar  for some screen shows out of memory error so each time reclyethe bitmap*/
			//global.setImage(R.drawable.screen,w,h);
			/* added by sankar */
			mBitmap =global.getBg();// BitmapFactory.decodeResource(getResources(),
					//R.drawable.u_background);
			canvasWidth = w;
			canvasHeight = h;
			float xscale = (float) w / (float) mBitmap.getWidth();
			float yscale = (float) h / (float) mBitmap.getHeight();
			if (xscale > yscale) // make sure both dimensions fit (use the
									// smaller scale)
				xscale = yscale;
			float newx = w * xscale;
			float newy = h * xscale; // use the same scale for both
										// dimensions
			// if you want it centered on the display (black borders)
			mBitmap = Bitmap.createScaledBitmap(mBitmap, this.getWidth(),
					this.getHeight(), true);
			// mCanvas = new Canvas(mBitmap);
			mScaleDetector = new ScaleGestureDetector(GridActivity.this,
					new ScaleListener());
			mRotateDetector = new RotateGestureDetector(
					getApplicationContext(), new RotateListener());

		}
		private class RotateListener extends
	RotateGestureDetector.SimpleOnRotateGestureListener {
	@Override
	public boolean onRotate(RotateGestureDetector detector) {
		Picture picture = null;
		try {

			picture = global.getPictureList().get(selectedPictureId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (picture != null) {
			picture.setRotationAngle(picture.getRotationAngle()
					- detector.getRotationDegreesDelta());

		}
		return true;
	}

	public boolean onRotateBegin(RotateGestureDetector detector) {
		if (selectedPictureId >= 0) {

			Picture picture = global.getPictureList().get(selectedPictureId);

			float mAngle = picture.getRotationAngle();

			RotatePictureAction action = new RotatePictureAction(picture);
			action.setRotateAngleStart(mAngle);
			currentRotateAction = action;
			addToActionList(currentRotateAction);
		}
		panel.invalidate();
		return true;
	}

	public void onRotateEnd(RotateGestureDetector detector) {
		// Do nothing, overridden implementation may be used
		if (selectedPictureId >= 0) {

			Picture picture = global.getPictureList().get(selectedPictureId);
			/*
			 * if(currentMoveAction!=null && currentMoveAction.getEndX()==0
			 * &&currentMoveAction.getEndY()==0 ) {
			 * currentMoveAction.setEndX(picture.getCenterX()) ;
			 * currentMoveAction.setEndY(picture.getCenterY()) ; }
			 */
			float angle = picture.getRotationAngle();

			currentRotateAction.setRotateAngleEnd(angle);

		}

		panel.invalidate();

	}
}

		private class ScaleListener extends
				ScaleGestureDetector.SimpleOnScaleGestureListener {

			@Override
			public boolean onScaleBegin(ScaleGestureDetector detector) {

				if (currentAction != null
						&& !actionList.contains(currentAction)) {
					addToActionList(currentAction);
				}
				//if (selectedPictureId >= 0) {
					Log.d("Draw", "scale begin");

					Picture picture = global.getPictureList().get(global.getPictureList().size()-1);

					float mScaleFactor = picture.getScaleFactor();

					ResizePictureAction resizeAction = new ResizePictureAction(
							picture);
					resizeAction.setScaleFactorStart(mScaleFactor);
					currentAction = resizeAction;
					addToActionList(currentAction);
				//}
				invalidate();
				return true;
			}

			@Override
			public void onScaleEnd(ScaleGestureDetector detector) {

				if (currentAction instanceof ResizePictureAction) {
					//if (selectedPictureId >= 0) {
						Log.d("Draw", "scale begin");

						Picture picture = global.getPictureList().get(global.getPictureList().size()-1);
						if (currentMoveAction != null
								&& currentMoveAction.getEndX() == 0
								&& currentMoveAction.getEndY() == 0) {
							currentMoveAction.setEndX(picture.getCenterX());
							currentMoveAction.setEndY(picture.getCenterY());
						}
						float mScaleFactor = picture.getScaleFactor();

						((ResizePictureAction) currentAction)
								.setScaleFactorEnd(mScaleFactor);

				//	}
				}
				invalidate();

			}

			@Override
			public boolean onScale(ScaleGestureDetector detector) {

				//if (selectedTool == TOOL_SELECTION) {
					//if (selectedPictureId >= 0) {
						Picture picture = global.getPictureList().get(global.getPictureList().size()-1);

						float mScaleFactor = picture.getScaleFactor();

						mScaleFactor *= detector.getScaleFactor();

						// Don't let the object get too small or too large.
						mScaleFactor = Math.max(1 / 3,
								Math.min(mScaleFactor, 1));
						if (mScaleFactor > 1)
							mScaleFactor = 1;
						if (mScaleFactor < .33f)

							mScaleFactor = .33f;
						// setPicturePositionOnscale(picture,
						// detector.getScaleFactor() );
						if (picture != null)
							picture.setScaleFactor(mScaleFactor);
					//}
				//}
				invalidate();
				return true;
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent ev) {
			// Let the ScaleGestureDetector inspect all events.
			/*if (selectedCategory > 0) {
				return true;
			}*/
			/*
			 * float x = ev.getX(); float y = ev.getY();
			 * 
			 * switch (event.getAction()) { case MotionEvent.ACTION_DOWN: if
			 * (!isTextModeOn) { touch_start(x, y); invalidate(); } break; case
			 * MotionEvent.ACTION_MOVE: if (!isTextModeOn) { touch_move(x, y);
			 * invalidate(); } break; case MotionEvent.ACTION_UP: if
			 * (isTextModeOn) { drawText((int) x, (int) y); invalidate(); } else
			 * { touch_up(); invalidate(); } break;
			 */
			// if(mScaleDetector.isInProgress())
			{
				// if (selectedTool == TOOL_SELECTION) {

				// mRotationDetector.onTouchEvent(ev);
			}

			// if(rotationInAction)
			{
				mRotateDetector.onTouchEvent(ev);
				// return true;
			}

			mScaleDetector.onTouchEvent(ev);
			// }
			final int action = ev.getAction();
			switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN: {
				final float x = ev.getX();
				final float y = ev.getY();
				currentMoveAction = null;
				deleteOnPictureTouched = false;
				// if (!isTextModeOn) {[
				//if (selectedPictureId > -1) {
					//Log.i("Test", pictures.size()+"_ "+selectedPictureId);
					final Picture lastSelPic = global.getPictureList().get(global.getPictureList().size()-1);
					if (lastSelPic != null && lastSelPic.isTouched(x, y)) {

						pictureTouchX = x;
						pictureTouchY = y;

						pictureTouchXOffset = lastSelPic
								.getPictureTouchXOffset(pictureTouchX);
						pictureTouchYOffset = lastSelPic
								.getPictureTouchYOffset(pictureTouchY);
						/*
						 * if(lastSelPic.isRotateTouched(x, y)) {
						 * lastSelPic.toggleRotation() ;
						 * if(lastSelPic.isDoingRotation()) {
						 * rotationInAction=true; } else rotationInAction=false;
						 * }
						 */
						if (lastSelPic.isDeleteTouched(x, y)) {
							deleteOnPictureTouched = true;
							AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
									GridActivity.this);
							myAlertDialog.setTitle("VFR");
							myAlertDialog
									.setMessage("Do you want to remove this picture?");
							myAlertDialog.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface arg0, int arg1) {
											try {
												RemovePictureAction removePictureAction = new RemovePictureAction(
														lastSelPic);
												global.getPictureList().remove(lastSelPic);
												addToActionList(removePictureAction);
												//selectedPictureId = -1;
												DrawingPanel.this.invalidate();

											} catch (Exception e) {
												e.printStackTrace();
											}

										}
									});
							myAlertDialog.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface arg0, int arg1) {
											// do something when the Cancel
											// button is clicked
										}
									});
							myAlertDialog.show();

						}

						invalidate();
						break;
					}
				//}

				int pictureTouched = -1;
				Picture picture = null;
				for (int i = global.getPictureList().size() - 1; i >= 0; i--) {
					// check if selection on image
					picture = global.getPictureList().get(i);
					if (picture.isPictureTouched(x, y))

					{
						pictureTouched = i;
						/*
						 * currentMoveAction = new MovePictureAction(picture);
						 * currentMoveAction.setStartX(x);
						 * currentMoveAction.setStartY(y);
						 * actionList.add(currentMoveAction);
						 */
						break;
					}
				}

				if (pictureTouched > -1) {
					if (selectedPictureId == -1) {
					global.getPictureList().remove(picture);
					global.getPictureList().add(picture);
						pictureTouchX = x;
						pictureTouchY = y;
						pictureTouchXOffset = picture
								.getPictureTouchXOffset(pictureTouchX);
						pictureTouchYOffset = picture
								.getPictureTouchYOffset(pictureTouchY);

						selectedPictureId = global.getPictureList().size() - 1;

						// selectedPictureId=pictureTouched;
					} else
						selectedPictureId = -1;
				}

				else
					selectedPictureId = -1;// ==-1;

				mLastTouchX = x;
				mLastTouchY = y;
				mActivePointerId = ev.getPointerId(0);
			}
				invalidate();
				break;
			// }

			case MotionEvent.ACTION_MOVE: {
				float x = ev.getX();
				float y = ev.getY();

				// if (!isTextModeOn) {
				/*
				 * if (selectedTool == TOOL_BRUSH) {
				 * 
				 * touch_move(x, y); invalidate(); }
				 */
				// Only move if the ScaleGestureDetector isn't processing a
				// gesture.
				// if (selectedTool == TOOL_SELECTION) {
				if (deleteOnPictureTouched) {
					// if (!mScaleDetector.isInProgress())
					break;
				}
				if (selectedPictureId >= 0) {
					Picture picture = global.getPictureList().get(selectedPictureId);
					if (picture != null) {

						if (pictureTouchX != x || pictureTouchY != y) {
							if (mScaleDetector.isInProgress())
								setPicturePosition(picture,
										mScaleDetector.getFocusX(),
										mScaleDetector.getFocusY());
							else
								setPicturePositionInBox(picture, x, y);
						}
						if (currentMoveAction == null)// &&
														// !mScaleDetector.isInProgress())
						{

							currentMoveAction = new MovePictureAction(picture);

							currentMoveAction.setStartX(picture.getCenterX());
							currentMoveAction.setStartY(picture.getCenterY());
							addToActionList(currentMoveAction);

						}
					}
					// }

					final int pointerIndex = ev
							.findPointerIndex(mActivePointerId);
					// x = ev.getX(pointerIndex);
					// y = ev.getY(pointerIndex);

					if (!mScaleDetector.isInProgress()) {
						final float dx = x - mLastTouchX;
						final float dy = y - mLastTouchY;

						mPosX += dx;
						mPosY += dy;

						invalidate();
					}

					mLastTouchX = x;
					mLastTouchY = y;
				}
				invalidate();
				break;
			}

			case MotionEvent.ACTION_UP: {
				float x = ev.getX();
				float y = ev.getY();

				int pictureIn = -1;
				Picture picture = null;
				for (int i = global.getPictureList().size() - 1; i >= 0; i--) {
					// check if selection on image
					picture = global.getPictureList().get(i);
					if (picture.isTouched(x, y))

					{
						// selectedPictureId=i;
						// Toast.makeText(DrawingCanvas.this,
						// "Picture touched:"+i, Toast.LENGTH_SHORT).show();
						pictureIn = i;
						break;
					}
				}
				if (pictureIn == -1) {
					// selectedPictureId = -1;

				} else if (currentMoveAction != null) {
					if (picture != null) {
						currentMoveAction.setEndX(picture.getCenterX());
						currentMoveAction.setEndY(picture.getCenterY());
					}
				}

				/*
				 * else if (selectedTool == TOOL_BRUSH) { touch_up();
				 * invalidate(); }
				 */

				/*else if (selectedTool == TOOL_SELECTION) {
					mActivePointerId = INVALID_POINTER_ID;
				}*/
				invalidate();
				break;
			}

			case MotionEvent.ACTION_CANCEL: {
				//mActivePointerId = INVALID_POINTER_ID;
				break;
			}

			case MotionEvent.ACTION_POINTER_UP: {
				final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
				final int pointerId = ev.getPointerId(pointerIndex);
				if (pointerId == mActivePointerId) {
					// This was our active pointer going up. Choose a new
					// active pointer and adjust accordingly.
					final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
					mLastTouchX = ev.getX(newPointerIndex);
					mLastTouchY = ev.getY(newPointerIndex);
					mActivePointerId = ev.getPointerId(newPointerIndex);
				}
				break;
			}
			}

			return true;
		}

		private void setPicturePosition(Picture picture, float x, float y) {
			// TODO Auto-generated method stub
			/*
			 * float xPos = x - (picture.getWidth() * picture.getScaleFactor()
			 * ); if (xPos < 0) xPos = 0; float yPos = y - (picture.getHeight()
			 * * picture.getScaleFactor() ); if (yPos < 0) yPos = 0;
			 */
			picture.setCenter(x, y);
			// picture.setX(x);
			// picture.setY(y);
		}

		private void setPicturePositionInBox(Picture picture, float x, float y) {
			// TODO Auto-generated method stub
			/*
			 * float xPos = x - (picture.getWidth() * picture.getScaleFactor()
			 * ); if (xPos < 0) xPos = 0; float yPos = y - (picture.getHeight()
			 * * picture.getScaleFactor() ); if (yPos < 0) yPos = 0;
			 */
			picture.setPositionInbox(x, y, pictureTouchXOffset,
					pictureTouchYOffset);
			// picture.setX(x);
			// picture.setY(y);
		}

		private void setPicturePositionOnscale(Picture picture, float scaleDiff) {
			// TODO Auto-generated method stub
			/*
			 * float xPos = x - (picture.getWidth() * picture.getScaleFactor()
			 * ); if (xPos < 0) xPos = 0; float yPos = y - (picture.getHeight()
			 * * picture.getScaleFactor() ); if (yPos < 0) yPos = 0;
			 */
			float xFact = scaleDiff * picture.getWidth() / 2;
			float yFact = scaleDiff * picture.getHeight() / 2;

			picture.setX(picture.getX() + xFact);
			picture.setY(picture.getY() + yFact);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

			for (int i = 0; i < global.getPictureList().size(); i++) {
				com.ygs.virtualfittingroom.entity.Picture picture = global.getPictureList().get(i);

				// canvas.scale(picture.getScaleFactor(),
				// picture.getScaleFactor(), picture.getCenterX()
				// ,picture.getCenterY());

				Matrix matrix = new Matrix();

				// rotate around (0,0)
				//
				// rotator.postScale(picture.getScaleFactor(),
				// picture.getScaleFactor());
				// canvas.drawBitmap(picture.getBitmap(), null,
				// picture.getDestRect(canvas), mBitmapPaint);

				matrix.postTranslate(picture.getX(), picture.getY());
				matrix.postScale(picture.getScaleFactor(),
						picture.getScaleFactor(), picture.getX(),
						picture.getY());

				matrix.postRotate(picture.getRotationAngle(),
						picture.getCenterX(), picture.getCenterY());

				if (i == selectedPictureId) {
					// mBitmapPaint.setColorFilter(colorFilter);
					// mBitmapPaint.setAlpha(190);
					// mBitmapPaint.setXfermode(new
					// PorterDuffXfermode(Mode.DARKEN));

					// The original image to use

					// extract the alpha from the source image
					Bitmap alpha = picture.getBitmap().extractAlpha();

					// The output bitmap (with the icon + glow)

					// The canvas to paint on the image
					int glowRadius = 8;

					// the glow color
					int glowColor = Color.YELLOW;

					Paint paint = new Paint();
					paint.setColor(glowColor);

					// outer glow
					paint.setMaskFilter(new BlurMaskFilter(glowRadius,
							Blur.SOLID));
					// canvas.drawBitmap(alpha, matrix, paint);
					canvas.drawRect(picture.getCenterRect(), mBitmapPaint);

					picture.drawSelectionBox(canvas, matrix, mBitmapPaint);
				}
				canvas.drawBitmap(picture.getBitmap(), matrix, mBitmapPaint);

				// canvas.drawBitmap( picture.getBitmap(), picture.getX() ,
				// picture.getY() , mBitmapPaint);
				matrix.reset();
				// canvas.drawRect(new RectF(picture.getX(),
				// picture.getY(),picture.getX()+picture.getWidth(),
				// picture.getY()+picture.getHeight()),mBitmapPaint);
				// canvas.save();
				// canvas.restore();

			}

		}

		private float mX, mY;
		private static final float TOUCH_TOLERANCE = 0;

		private void touch_start(float x, float y) {
			mPath.reset();
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
		}

		private void touch_move(float x, float y) {
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
				mX = x;
				mY = y;
			}
		}
		Paint mPaint, mBitmapPaint, eraserPaint;
		private void touch_up() {
			mPath.lineTo(mX, mY);
			// commit the path to our offscreen
			mCanvas.drawPath(mPath, mPaint);
			// kill this so we don't double draw
			mPath = new Path();
			paths.add(new PathPoints(mPath, color, false));

		}

		public void onClickUndo() {
			if (actionList.size() == 0)
				return;
			Action action = actionList.pop();
			if (actionList.size() == 0) {
				/*if (undoImg != null)
					undoImg.setImageResource(R.drawable.u_undo);// disabled
*/			}
			//addToUndoneActionList(action);

			if (action instanceof ResizePictureAction) {

				ResizePictureAction resizeAction = (ResizePictureAction) action;
				Picture p = resizeAction.getPicture();
				p.setScaleFactor(resizeAction.getScaleFactorStart());

			}

			if (action instanceof RotatePictureAction) {

				RotatePictureAction rotateAction = (RotatePictureAction) action;
				Picture p = rotateAction.getPicture();
				p.setRotationAngle(rotateAction.getRotateAngleStart());

			}

			else if (action instanceof MovePictureAction) {
				MovePictureAction resizeAction = (MovePictureAction) action;
				Picture p = resizeAction.getPicture();
				setPicturePosition(p, resizeAction.getStartX(),
						resizeAction.getStartY());
			} else if (action instanceof AddPictureAction) {
				AddPictureAction addAction = (AddPictureAction) action;
				Picture p = addAction.getPicture();
				global.getPictureList().remove(p);
			} else if (action instanceof RemovePictureAction) {
				RemovePictureAction undoAction = (RemovePictureAction) action;
				Picture p = undoAction.getPicture();
				global.getPictureList().add(p);
			}
			selectedPictureId = global.getPictureList().size()-1;
			invalidate();

			/*
			 * if (paths.size() > 0) { undonePaths.add(paths.remove(paths.size()
			 * - 1)); invalidate(); } else {
			 * 
			 * }
			 */
			//Log.i("Test_undo", pictures.size()+"_"+selectedPictureId);
			// toast the user
		}

		public void onClickRedo() {
			if (undoneActionList.size() == 0)
				return;
			Action action = undoneActionList.pop();
			if (undoneActionList.size() == 0) {
				/*if (redoImg != null)
					redoImg.setImageResource(R.drawable.u_redo);// disabled
*/			}

			addToActionList(action);

			if (action instanceof ResizePictureAction) {
				Log.d("Draw", "scale undo");

				ResizePictureAction resizeAction = (ResizePictureAction) action;
				Picture p = resizeAction.getPicture();
				p.setScaleFactor(resizeAction.getScaleFactorEnd());

			}
			if (action instanceof RotatePictureAction) {
				Log.d("Draw", "scale undo");

				RotatePictureAction rotatePictureAction = (RotatePictureAction) action;
				Picture p = rotatePictureAction.getPicture();
				p.setRotationAngle(rotatePictureAction.getRotateAngleEnd());

			}

			else if (action instanceof MovePictureAction) {
				MovePictureAction resizeAction = (MovePictureAction) action;
				Picture p = resizeAction.getPicture();
				setPicturePosition(p, resizeAction.getEndX(),
						resizeAction.getEndY());
			} else if (action instanceof AddPictureAction) {
				AddPictureAction addAction = (AddPictureAction) action;
				Picture p = addAction.getPicture();
				global.getPictureList().add(p);
			} else if (action instanceof RemovePictureAction) {
				RemovePictureAction redoAction = (RemovePictureAction) action;
				Picture p = redoAction.getPicture();
				global.getPictureList().remove(p);
			}
			selectedPictureId = global.getPictureList().size()-1;
			invalidate();
			//Log.i("Test_redo", pictures.size()+"_"+selectedPictureId);
		}
	}
}
