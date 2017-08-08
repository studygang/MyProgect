package com.gangzi.onedaybest.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityList.addActiviy(this);
		
		super.onCreate(savedInstanceState);
	}

	public abstract void processMessage(Message message);

	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			ActivityList.getLastActivity().processMessage(msg);
		}
	};

	public static void sendMsg(Message msg) {
		handler.sendMessage(msg);
	}

	@Override
	protected void onDestroy() {
		// ActivityList.removeActivity(this);
		super.onDestroy();
	}
}
