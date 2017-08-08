package com.gangzi.onedaybest.base;

import android.util.Log;

import java.util.LinkedList;

public class ActivityList {
	private static LinkedList<BaseActivity> list = new LinkedList<BaseActivity>();

	public static void addActiviy(BaseActivity a) {
		if (!list.contains(a)) {
			list.add(a);
		}
	}

	public static BaseActivity getLastActivity() {
		return list.getLast();
	}

	public static void removeActivity(BaseActivity a) {
		if (!list.isEmpty()) {
			list.remove(a);
		}
	}

	/**
	 * 退出，结束程序的所有界面
	 */
	public static void tuichu() {
		// for (BaseActivity str : list) {
		// str.finish();
		// list.remove(str);
		// }
		for (int i = 0; i < list.size(); i++) {
			try {
				list.get(i).finish();
				list.remove(i);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		int l = list.size();
		Log.i("wjj", l + "");
	}

	/**
	 * 退出登录，留下一个登录界面
	 */
	public static void existLogin() {
		for (int i = 0; i < list.size(); i++) {
			if (i < list.size() - 1) {
				list.get(i).finish();
			}
		}
	}
}
