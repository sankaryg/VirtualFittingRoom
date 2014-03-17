package com.ygs.virtualfittingroom;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class Touchmoveimage extends Activity {

	int windowwidth;
	int windowheight;

	private LayoutParams layoutParams;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		windowwidth = getWindowManager().getDefaultDisplay().getWidth();
		windowheight = getWindowManager().getDefaultDisplay().getHeight();
		final ImageView balls = (ImageView) findViewById(R.id.ball);

		balls.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				LayoutParams layoutParams = (LayoutParams) balls
						.getLayoutParams();
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:
					int x_cord = (int) event.getRawX();
					int y_cord = (int) event.getRawY();

					if (x_cord > windowwidth) {
						x_cord = windowwidth;
					}
					if (y_cord > windowheight) {
						y_cord = windowheight;
					}

					layoutParams.leftMargin = x_cord - 25;
					layoutParams.topMargin = y_cord - 75;

					balls.setLayoutParams(layoutParams);
					break;
				default:
					break;
				}
				return true;
			}
		});
	}
}
