package com.ygs.virtualfittingroom.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.ygs.virtualfittingroom.BaseActivity;
import com.ygs.virtualfittingroom.MainActivity;

public class ShowAlert {

	private Context context;
	private AlertDialog alertDialog;
	
	public boolean appInstalledOrNot(String paramString,Context context)
	  {
	    PackageManager localPackageManager = context.getPackageManager();
	    try
	    {
	      localPackageManager.getPackageInfo(paramString, 1);
	      return true;
	    }
	    catch (PackageManager.NameNotFoundException localNameNotFoundException)
	    {
	    }
	    return false;
	  }

	public ShowAlert(Context context){
		this.context = context;
	}
	public String getDeviceID(Context context){
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String id = telephonyManager.getDeviceId();
		return id;
	}
	public void showAlertDialog(final Context context, String title, String message, Boolean status, final String string) {
       alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
 
        // Setting alert dialog icon
        //alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
 
        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if(string != null){
					if(string.equals("back")){
						((BaseActivity)context).launchActivity(MainActivity.class);
					}else if(string.equals("finish"))
						((Activity)context).finish();
				}
				/*String str = null; 
				if(str == null && string != null ){
					str = string.split("_")[0];
					if(str == null && string.equals("finish"))
					((Activity)context).finish();
					else if(str != null){
						Intent mainActivity = new Intent(context, MainActivity.class);
						((Activity)context).startActivity(mainActivity);
						((Activity)context).finish();
					}
				}*/
				
			}
		});
       
        // Showing Alert Message
        alertDialog.show();
    }
	
	public void cancelAlert(){
		if(alertDialog!=null){
		if(alertDialog.isShowing()){
			alertDialog.cancel();
			alertDialog = null;
		}
		}
	}
	
	public AlertDialog getAlertDialog() {
		return alertDialog;
	}
	
	
}
