package com.example.mynews.utils;

import android.util.Log;

public class ILog
{
	/**
	 * 日志输出开关.
	 */
	public static final boolean FLAG = true;

	/**
	 * 
	 * 方法说明：日志输出.
	 * 
	 * @param tag
	 *            标签
	 * @param message
	 *            信息 void
	 */
	public static void d(final String tag, final String message)
	{
		if (FLAG) {
			if (null == message || message.equals("NULL")) {
				return;
			} else {
				Log.d(tag, message);
			}
		} else {
			return;
		}
	}

	/**
	 * 
	 * 方法说明：日志输出方法
	 * 
	 * @param tag
	 *            TAG或者某个类
	 * @param message
	 *            输出信息 void
	 */
	@SuppressWarnings("rawtypes")
	public static void d(final Object tag, final String message)
	{
		if (FLAG) {
			if (null == message || message.equals("NULL")) {
				return;
			} else {
				Log.d(((Class) tag).getCanonicalName(), message);
			}
		} else {
			return;
		}
	}
}
