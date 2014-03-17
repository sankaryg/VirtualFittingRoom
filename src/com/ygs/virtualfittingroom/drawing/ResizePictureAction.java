package com.ygs.virtualfittingroom.drawing;

import com.ygs.virtualfittingroom.entity.Picture;

public class ResizePictureAction extends PictureAction {

	/**
	 * @return the scaleFactorStart
	 */
	public float getScaleFactorStart() {
		return scaleFactorStart;
	}

	/**
	 * @param scaleFactorStart
	 *            the scaleFactorStart to set
	 */
	public void setScaleFactorStart(float scaleFactorStart) {
		this.scaleFactorStart = scaleFactorStart;
	}

	/**
	 * @return the scaleFactorEnd
	 */
	public float getScaleFactorEnd() {
		return scaleFactorEnd;
	}

	/**
	 * @param scaleFactorEnd
	 *            the scaleFactorEnd to set
	 */
	public void setScaleFactorEnd(float scaleFactorEnd) {
		this.scaleFactorEnd = scaleFactorEnd;
	}

	public ResizePictureAction(Picture picture) {
		super(picture);
		// TODO Auto-generated constructor stub
	}

	float scaleFactorStart;
	float scaleFactorEnd;
}
