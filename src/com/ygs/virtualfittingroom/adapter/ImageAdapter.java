package com.ygs.virtualfittingroom.adapter;

import com.ygs.virtualfittingroom.Global;
import com.ygs.virtualfittingroom.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
 
    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher
    };

	private String type;
 
	Global global;
    // Constructor
    public ImageAdapter(Context c, String type){
        mContext = c;
        this.type = type;
        global = (Global)c.getApplicationContext();
    }
 
    @Override
    public int getCount() {
    	if(type.equals("img"))
        return mThumbIds.length;
    	else
    		return global.getTextList().size();
    				
    }
 
    @Override
    public Object getItem(int position) {
       	if(type.equals("img"))
        return mThumbIds[position];
       	else
       		return global.getTextList().get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    static class ViewHolder{
    	ImageView image;
    	TextView text;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	 ViewHolder holder = new ViewHolder();
         if(convertView==null){
             LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView = vi.inflate(R.layout.image_item, null);

             holder.image=(ImageView) convertView.findViewById(R.id.image);
             holder.text = (TextView) convertView.findViewById(R.id.textView1);
             convertView.setTag(holder);
         }
         holder = (ViewHolder) convertView.getTag();
        /*
        ImageView imageView = new ImageView(mContext);
        */
         if(type.equals("img")){
         holder.image.setImageResource(mThumbIds[position]);
        
        holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
         holder.text.setVisibility(View.GONE);
         }else{
        	 holder.image.setVisibility(View.GONE);
        	 holder.text.setText(global.getTextList().get(position));
         }
        //holder.image.setLayoutParams(new GridView.LayoutParams(70, 70));
        return convertView;
    }
 
}