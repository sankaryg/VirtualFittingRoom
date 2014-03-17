package com.ygs.virtualfittingroom.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String Category_Table = "Category";
	public static final String category_ID = "_id";
	public static final String category_db_Id = "id";
	public static final String category_Type = "category";
	public static final String category_Channel_Name = "channel_name";
	public static final String category_Channel_Link = "channel_link";

	private static final String DATABASE_NAME = "Cricket.db";
	private static final int DATABASE_VERSION = 4;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + Category_Table
			+ "(" + category_ID + " integer primary key autoincrement, " + category_db_Id
			+ " text not null, " + category_Type + " text not null," + category_Channel_Name
			+ " text not null, " +category_Channel_Link+ " text not null );";

	/* table for list of brands */

	public static final String Update_TABLE = "updatetable";
	public static final String Update_ID = "_id";
	public static final String Update_USER_ID = "id";
	public static final String Update_Status = "status";
	public static final String Update_Imei = "imei";

	private static final String BRAND_TABLE_QUERY = "create table "
			+ Update_TABLE + "(" + Update_ID
			+ " integer primary key autoincrement, " + Update_USER_ID
			+ " text not null, " + Update_Status + " text not null, "+ Update_Imei + " text not null);";
		public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(DATABASE_CREATE);
		database.execSQL(BRAND_TABLE_QUERY);
		}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + Category_Table);
		db.execSQL("Drop table if exists " + Update_TABLE);
		onCreate(db);
	}
}
