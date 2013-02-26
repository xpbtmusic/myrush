package com.example.mynews.utils;

import android.content.Context;

public class StringUtils {
	
	public static String getString(Context context,int resid){
		 if(0==resid){
			 return null;
		 }
		
		return context.getResources().getString(resid);
		
		
		
	}
	public static boolean isNull(String s)
	{

		if ("".equals(s) || null == s || s.length() == 0) {
			return true;
		} else {
			return false;
		}

	}
}
