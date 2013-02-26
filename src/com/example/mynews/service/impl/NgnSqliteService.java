package com.example.mynews.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.mynews.service.INgnSqliteService;
import com.example.mynews.utils.ILog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;




public class NgnSqliteService implements INgnSqliteService
{
	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "base";
	/**
	 * 数据库路径
	 */
	public static final String DB_PATH = "data/data/com.example.mynews/databases/base.db";
	public static final String DB_PATH_TEMP = "data/data/com.example.mynews/databases/base.db";
	/**
	 * 雇员表
	 */
	public static final String TB_NAME_CONTACTS = "tablename";
	/**
	 * 部门--员工表
	 */
	public static final String TB_NAME_RELATION = "T_RELATION";
	/**
	 * 部门表
	 */
	public static final String TB_NAME_DEPT = "T_DEPT";
	/**
	 * 内容
	 */
	public static final String POST_CONTENT = "post_content";
	/**
	 * 标题
	 */
	public static final String POST_TITLE = "post_title";
	
	/**
	 * LOG标签
	 */
	private static final String TAG = NgnSqliteService.class.getCanonicalName();

	/**
	 * 
	 * 方法说明：得到数据库实例
	 * 
	 * @return SQLiteDatabase
	 */
	private SQLiteDatabase getDataBase()
	{
		File dbFile = new File(DB_PATH_TEMP);
		SQLiteDatabase db = null;
		if (!dbFile.exists()) {

			try {

				db = SQLiteDatabase.openOrCreateDatabase(DB_PATH, null);

			} catch (SQLiteException e) {

				
				e.printStackTrace();

			}
		} else {
			db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
		}
		return db;

	}

	/**
	 * 
	 * 方法说明：插入多条数据
	 * 
	 * @param lists
	 *            数据集合
	 * @param tb_name
	 *            表名 void
	 */
	public void insert2(List<ContentValues> lists, String tb_name)
	{
		if (lists == null || lists.size() == 0) {
			return;
		}
		SQLiteDatabase db = null;
		try {
			db = getDataBase();
			db.beginTransaction();

			for (int j = 0; j < lists.size(); j++) {
				db.insert(tb_name, "", lists.get(j));
			}

		} catch (Exception e) {
			db.endTransaction();
			db.close();
			

		} finally {
			db.setTransactionSuccessful();
			db.endTransaction();
			if (db != null) {
				db.close();
				
			}
		}
	}

	/**
	 * 方法说明:查询集合
	 * 
	 * @param tb_name
	 *            表名
	 * @return List<ContentValues>
	 */
	public List<ContentValues> queryAll(String tb_name)
	{
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = getDataBase();
			String sql = "select " + POST_TITLE + "," + POST_CONTENT 
					+ " from " + tb_name;
			cursor = db.rawQuery(sql, null);
			List<ContentValues> list = new ArrayList<ContentValues>();
			ILog.d(TAG + "条目", cursor.getCount() + "===");
			if (cursor.getCount() < 1) {
				return null;
			} else {
				while (cursor.moveToNext()) {
					ContentValues item = new ContentValues();
					item.put(POST_TITLE, cursor.getString(cursor.getColumnIndex(POST_TITLE)));
					item.put(POST_CONTENT, cursor.getString(cursor.getColumnIndex(POST_CONTENT)));
				
					list.add(item);

				}
				return list;
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
				

			}

		}
	}



	/**
	 * 方法说明：
	 * 
	 * void
	 */
	private void deleteTempDB()
	{
		File file = new File(DB_PATH_TEMP);
		if (file.exists()) {
			if (file.delete()) {
				ILog.d(TAG, "删除临时数据库");
			}
		}

	}




	/**
	 * 
	 * 方法说明：删除数据库
	 * 
	 * @param context
	 *            上下文
	 * @param db_name
	 *            数据库名
	 * @return boolean
	 */
	public boolean deleteDatabase(Context context, String db_name)
	{
		File dbFile = new File(DB_PATH);
		if (dbFile.exists()) {
			return dbFile.delete();
		}
		return false;
	}

	/**
	 * 
	 * 方法说明：判断数据库是否存在
	 * 
	 * @return boolean
	 */
	public boolean dataBaseIsExists()
	{
		File dbFile = new File(DB_PATH);

		return dbFile.exists();
	}

	/**
	 * 方法说明:开启服务
	 * 
	 * @return boolean
	 */
	@Override
	public boolean start()
	{

		return false;
	}

	/**
	 * 方法说明:关闭服务
	 * 
	 * @return boolean
	 */
	@Override
	public boolean stop()
	{
		// TODO Auto-generated method stub
		return false;
	}



}
