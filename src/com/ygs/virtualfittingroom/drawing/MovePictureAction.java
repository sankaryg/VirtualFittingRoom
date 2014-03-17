package com.ygs.virtualfittingroom.drawing;

import com.ygs.virtualfittingroom.entity.Picture;

public class MovePictureAction extends PictureAction {

	public MovePictureAction(Picture picture) {
		super(picture);
		// TODO Auto-generated constructor stub
	}

	float startX;
	float startY;

	float endX;
	float endY;

	/**
	 * @return the startX
	 */
	public float getStartX() {
		return startX;
	}

	/**
	 * @param startX
	 *            the startX to set
	 */
	public void setStartX(float startX) {
		this.startX = startX;
	}

	/**
	 * @return the startY
	 */
	public float getStartY() {
		return startY;
	}

	/**
	 * @param startY
	 *            the startY to set
	 */
	public void setStartY(float startY) {
		this.startY = startY;
	}

	/**
	 * @return the endX
	 */
	public float getEndX() {
		return endX;
	}

	/**
	 * @param endX
	 *            the endX to set
	 */
	public void setEndX(float endX) {
		this.endX = endX;
	}

	/**
	 * @return the endY
	 */
	public float getEndY() {
		return endY;
	}

	/**
	 * @param endY
	 *            the endY to set
	 */
	public void setEndY(float endY) {
		this.endY = endY;
	}

}
