package com.ygs.virtualfittingroom.drawing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CanvasLayout extends RelativeLayout {
	Context context;
  	float relYLevel1 = .265f;
	float relYLevel2 = .465f;
	float relYLevelStartGameBtn = .55f;
	int bottombarHeight = 0;
	int bottombarTopPos = 0;
	int categorySelectBoxHeight = 0;

	public CanvasLayout(Context context, AttributeSet a) {
		super(context, a);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		int width = r - l;
		int height = b - t;
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			if (v.getTag() == null)
				continue;
			String viewTag = v.getTag().toString();
			// Toast.makeText(context,"Tag"+viewTag, Toast.LENGTH_SHORT).show();
			if (!(viewTag.startsWith("canvasbottombar")
					|| viewTag.startsWith("canvasdrawarea")
					|| viewTag.equals("categoryselhighlight") || viewTag
						.equals("categoryselbox")))
				continue;
			if (viewTag.startsWith("canvasbottombar") && (bottombarHeight == 0)) {

				if (v.getHeight() != 0) {
					bottombarHeight = v.getHeight();
				}
			}

			if (viewTag.equals("categoryselbox")
					&& (categorySelectBoxHeight == 0)) {

				if (v.getHeight() != 0) {
					categorySelectBoxHeight = v.getHeight();
				}
			}

			int viewL = 0;
			if (viewTag.equals("categoryselbox")) {
				viewL = l + ((width - v.getWidth()) / 2);
			}

			int viewR = viewL + v.getWidth(); // The right pixel is the left
												// pixel plus the measured width
												// of the child view itself

			/*
			 * if(viewTag.startsWith("username")) { viewR= viewL +birdWidth; }
			 */
			int viewT = 0;// The top pixel is 30% down the total height of the
							// parent view
			int viewB = 0;

			if (viewTag.equals("categoryselbox")) {
				// viewT= t+( (height-v.getHeight())/2);
				viewT =   height - bottombarHeight - v.getHeight();
				//Toast.makeText(context, "categoryselbox=" + viewT,
				//		Toast.LENGTH_SHORT).show();
			}

			/*
			 * if(viewTag.equals("canvasbottombar")) { bottombarTopPos=viewT;
			 * //Toast
			 * .makeText(context,"level1HeightBird="+level1HeightBird,Toast
			 * .LENGTH_SHORT).show(); }
			 */
			viewB = viewT + v.getHeight();

			// The bottom pixel is the top pixel plus the measured height of the
			// child view itself
			if (!viewTag.equals("categoryselbox"))
				continue;

			v.layout(viewL, viewT, viewR, viewB);

		}

	}

	// @Override

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// At this time we need to call setMeasuredDimensions(). Lets just
		// call the parent View's method
		// (see
		// https://github.com/android/platform_frameworks_base/blob/master/core/java/android/view/View.java)
		// that does:
		// setMeasuredDimension(getDefaultSize(
		// getSuggestedMinimumWidth(), widthMeasureSpec),
		// getDefaultSize(
		// getSuggestedMinimumHeight(), heightMeasureSpec));
		//

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int wspec = MeasureSpec.makeMeasureSpec(getMeasuredWidth()
				/ getChildCount(), MeasureSpec.EXACTLY);
		int hspec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
				MeasureSpec.EXACTLY);
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);

			if (v.getTag() == null)
				continue;
			String viewTag = v.getTag().toString();
			if (!(viewTag.startsWith("canvasbottombar") || viewTag
					.startsWith("categoryselbox")))
				continue;
			// v.measure(wspec, hspec);
		}
	}
}
