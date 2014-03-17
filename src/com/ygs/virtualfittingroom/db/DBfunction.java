package com.ygs.virtualfittingroom.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBfunction {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private long insertId;
	private int rows;

	public DBfunction(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public boolean resetTables(String table_name) {
		// TODO Auto-generated method stub
		open();
		try{
		database.delete(table_name, null, null);
		return true;
		//db.close();
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}finally{
			close();
		}
	}
	
}
