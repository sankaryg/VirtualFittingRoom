package com.ygs.virtualfittingroom.drawing;

import com.ygs.virtualfittingroom.entity.Picture;

public class RotatePictureAction extends PictureAction {

	public RotatePictureAction(Picture picture) {
		super(picture);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the rotateAngle
	 */
	public float getRotateAngleStart() {
		return rotateAngleStart;
	}

	/**
	 * @param rotateAngle
	 *            the rotateAngle to set
	 */
	public void setRotateAngleStart(float rotateAngle) {
		this.rotateAngleStart = rotateAngle;
	}

 
 

	float rotateAngleStart;
	float rotateAngleEnd;
	/**
	 * @return the rotateAngleEnd
	 */
	public float getRotateAngleEnd() {
		return rotateAngleEnd;
	}

	/**
	 * @param rotateAngleEnd the rotateAngleEnd to set
	 */
	public void setRotateAngleEnd(float rotateAngleEnd) {
		this.rotateAngleEnd = rotateAngleEnd;
	}
}
