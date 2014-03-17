package com.ygs.virtualfittingroom;

import java.lang.reflect.InvocationTargetException;

import com.androidquery.AQuery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends Activity {

	private Typeface typeface;
	private Typeface typefaceLight;
	private BaseActivity _activity;
	public MediaPlayer mp;
	private AQuery aq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		_activity = this;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	public AQuery aq() {
		if (aq == null) {
			aq = new AQuery(_activity);
		}
		return aq;
	}
	public void pauseWeb(WebView webView) {
		try {
			if (webView != null) {
				Class.forName("android.webkit.WebView")
						.getMethod("onPause", (Class[]) null)
						.invoke(webView, (Object[]) null);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("InlinedApi")
	public void launchActivity(Class<?> clazz) {
		Intent intent = new Intent(_activity, clazz);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		_activity.startActivity(intent);
		// _activity.finish();
	}

	public Typeface getTypefaceLight(Context context) {
		if (typefaceLight == null) {
			typefaceLight = Typeface.createFromAsset(context.getAssets(),
					"HelveticaNeueLight.ttf");

		}
		return typefaceLight;
	}

	public Typeface getHelvetica(Context context) {

		if (typeface == null) {
			typeface = Typeface.createFromAsset(context.getAssets(),
					"HelveticaNeue_Medium_Cond.ttf");

		}
		return typeface;
	}

	public void setFont(ViewGroup group, Typeface font) {
		int count = group.getChildCount();
		View v;
		for (int i = 0; i < count; i++) {
			v = group.getChildAt(i);
			if (v instanceof TextView || v instanceof Button /* etc. */)
				((TextView) v).setTypeface(font);
			else if (v instanceof ViewGroup)
				setFont((ViewGroup) v, font);
		}
	}

	
	
}
