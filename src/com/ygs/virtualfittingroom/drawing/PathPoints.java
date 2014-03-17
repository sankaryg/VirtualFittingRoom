package com.ygs.virtualfittingroom.drawing;

import android.graphics.Path;

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
