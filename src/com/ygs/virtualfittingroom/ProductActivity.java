package com.ygs.virtualfittingroom;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ygs.virtualfittingroom.aws.ProductAWS;
import com.ygs.virtualfittingroom.drawing.DrawingPanel;
import com.ygs.virtualfittingroom.entity.ProductCategory;
import com.ygs.virtualfittingroom.entity.ProductCategoryList;
import com.ygs.virtualfittingroom.util.ConnectionDetector;
import com.ygs.virtualfittingroom.util.ShowAlert;

public class ProductActivity extends BaseActivity{

	private Spinner categorySpinner;
	private Spinner categorySpinnerList;
	private LinearLayout layoutImage;
	private ImageView modelImage;
	private Button nextBtn;
	ShowAlert alert;
	private String img = null;
	ConnectionDetector connection;
	public ProgressDialog pd;
	private List<ProductCategory> parent;
	private List<ProductCategoryList> child;
	private DrawingPanel panel;
	Global global;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		init();
		
	}

	

	private void init() {
		// TODO Auto-generated method stub
		nextBtn = (Button) findViewById(R.id.button1);
		categorySpinner = (Spinner) findViewById(R.id.spinner1);
		categorySpinnerList = (Spinner) findViewById(R.id.spinner2);
		layoutImage = (LinearLayout) findViewById(R.id.linearLayout2);
		modelImage = (ImageView) findViewById(R.id.imageView1);
		alert = new ShowAlert(this);
		global = (Global)getApplicationContext();
		connection = new ConnectionDetector(this);
		if (connection.isConnectingToInternet())
	    {
			new GetUserInfoTask().execute("parent");
	    }
		nextBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			if(img!=null){
				DisplayMetrics displaymetrics = new DisplayMetrics();
	    		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	    		int height = displaymetrics.heightPixels;
	    		int width = displaymetrics.widthPixels;
				final ImageView imageView = (ImageView) findViewById(R.id.imageView1);
	            final BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
	            final Bitmap yourBitmap = bitmapDrawable.getBitmap();
	            global.setImage(yourBitmap, width, height);

	    		
				Intent imgIntent = new Intent(ProductActivity.this, GridActivity.class);
				imgIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				imgIntent.putExtra("img", img);
				startActivity(imgIntent);
			}
				//launchActivity(Touchmoveimage.class);
			}
		});
	}
	private class GetUserInfoTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(ProductActivity.this);
			pd.setMessage("");
			pd.setMax(100);
			pd.setCancelable(false);
			pd.setIndeterminate(false);
			pd.show();
		}
		protected String doInBackground(String... inputs) {
			ProductAWS pro = new ProductAWS(ProductActivity.this);
			try{
			if(inputs[0].equals("parent")){
				parent = pro.getProduct();
			}else{
				child = pro.getProductList(inputs[0]);
			}
			return inputs[0];
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}

		protected void onPostExecute(String result) {
			
			try {
				if (null != pd && pd.isShowing()) {
				pd.dismiss();
				pd = null;
			}
		    } catch (Exception e) {
		        // nothing
		    }

			if(result!=null){
				if(result.equals("parent")){
					List<String> country = new ArrayList<String>();
					for(ProductCategory str:parent){
						country.add(str.getType());
					}
					ArrayAdapter<String> parentAdapter = new ArrayAdapter<String>(ProductActivity.this, android.R.layout.simple_spinner_item,country);
					categorySpinner.setAdapter(parentAdapter);
					categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							new GetUserInfoTask().execute(parent.get(arg2).getId());
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							
						}
					});
				}else{
					List<String> state = new ArrayList<String>();
					for(ProductCategoryList str:child){
						state.add(str.getItem());
					}
					ArrayAdapter<String> parentAdapter = new ArrayAdapter<String>(ProductActivity.this, android.R.layout.simple_spinner_item,state);
					categorySpinnerList.setAdapter(parentAdapter);
					categorySpinnerList.setOnItemSelectedListener(new OnItemSelectedListener() {

						
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							//new GetUserInfoTask().execute(parent.get(arg2).getId());
							img = child.get(arg2).getImage();
							aq().id(R.id.imageView1).progress(R.id.progress).image(child.get(arg2).getImage(), true, true, 200, 0);
						
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							
						}
					});

				}
			}
		}
	}
}
