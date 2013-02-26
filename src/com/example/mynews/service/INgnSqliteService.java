package com.example.mynews.service;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;

public interface INgnSqliteService extends INgnBaseService
{

	/**
	 * 
	 * 方法说明：插入多条数据
	 * 
	 * @param lists
	 *            数据集合
	 * @param tb_name
	 *            表名 void
	 */
	void insert2(List<ContentValues> lists, String tb_name);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param tb_name
	 *            表名
	 * @return List<ContentValues>
	 */
	List<ContentValues> queryAll(String tb_name);

	

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
	boolean deleteDatabase(Context context, String db_name);

	/**
	 * 
	 * 方法说明：判断数据库是否存在
	 * 
	 * @return boolean
	 */
	boolean dataBaseIsExists();

}
