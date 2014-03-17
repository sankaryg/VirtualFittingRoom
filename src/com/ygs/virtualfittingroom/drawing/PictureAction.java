package com.ygs.virtualfittingroom.drawing;

import com.ygs.virtualfittingroom.entity.Picture;

public class PictureAction extends Action {
	Picture picture;

	/**
	 * @return the picture
	 */
	public Picture getPicture() {
		return picture;
	}

	/**
	 * @param picture
	 *            the picture to set
	 */
	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	/**
	 * @param picture
	 */
	public PictureAction(Picture picture) {
		super();
		this.picture = picture;
	}

}
