package com.ygs.virtualfittingroom.drawing;

import android.graphics.Picture;

public class RotateListener extends
RotateGestureDetector.SimpleOnRotateGestureListener {
@Override
public boolean onRotate(RotateGestureDetector detector) {
Picture picture = null;
try {

	//picture = pictures.get(selectedPictureId);
} catch (Exception e) {
	e.printStackTrace();
}

if (picture != null) {
	//picture.setRotationAngle(picture.getRotationAngle()
		//	- detector.getRotationDegreesDelta());

}
return true;
}

public boolean onRotateBegin(RotateGestureDetector detector) {
/*if (selectedPictureId >= 0) {

	Picture picture = pictures.get(selectedPictureId);

	float mAngle = picture.getRotationAngle();

	RotatePictureAction action = new RotatePictureAction(picture);
	action.setRotateAngleStart(mAngle);
	currentRotateAction = action;
	addToActionList(currentRotateAction);
}
drawView.invalidate();*/
return true;
}

public void onRotateEnd(RotateGestureDetector detector) {
// Do nothing, overridden implementation may be used
/*if (selectedPictureId >= 0) {

	Picture picture = pictures.get(selectedPictureId);
	
	 * if(currentMoveAction!=null && currentMoveAction.getEndX()==0
	 * &&currentMoveAction.getEndY()==0 ) {
	 * currentMoveAction.setEndX(picture.getCenterX()) ;
	 * currentMoveAction.setEndY(picture.getCenterY()) ; }
	 
	float angle = picture.getRotationAngle();

	currentRotateAction.setRotateAngleEnd(angle);

}

drawView.invalidate();
*/
}
}