package com.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.internal.is;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

public class PhotoCountService extends Service{

	private final IBinder mBinder = new MyBinder();
	private Handler serviceHandler;
	private Task myTask = new Task();
	private TaskUpdate taskUpdate = new TaskUpdate();
	private int count = 0;
	private boolean isUpdated = false;	
	private ResultReceiver resultReceiver;
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	public class MyBinder extends Binder {
		public PhotoCountService getService() {
			return PhotoCountService.this;
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("tryThattt","service");
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		resultReceiver = intent.getParcelableExtra("receiver");
		serviceHandler = new Handler();
		serviceHandler.postDelayed(taskUpdate, 10000L);
		
		Thread t = new Thread(new Task());
		t.start();
		return Service.START_NOT_STICKY;
	}
	
	class TaskUpdate implements Runnable{

		@Override
		public void run() {
			if(isUpdated){
				 Bundle bundle = new Bundle();
				 bundle.putInt("count", count);
				Log.d("count",count+"");
				isUpdated = false;
				Log.d("dad","dadad");
				resultReceiver.send(100, bundle);
			}
			serviceHandler.postDelayed(taskUpdate, 10000L);
		}
		
	}
	
	class Task implements Runnable {
		
		private final String everliveUrl = "http://api.everlive.com/v1/gIMQgGG9sI53ZQjD/ImagesInfo";
		
		public void run() {
			BufferedReader in = null;
			JSONArray result = null;
			HttpClient httpclient = new DefaultHttpClient();
			
			try {
				URI url = new URI(everliveUrl);
				while(true){
					HttpGet getResult = new HttpGet(url);

					HttpResponse response = httpclient.execute(getResult);
					in = new BufferedReader(new InputStreamReader(response.getEntity()
							.getContent()));
					// Log.e("Success", "Success");
					String line = in.readLine();
					StringBuilder sb = new StringBuilder();

					while (line != null) {
						sb.append(line);
						line = in.readLine();
					}

					JSONObject jsonObj = new JSONObject(sb.toString());
					String countFromService = jsonObj.get("Count").toString();
					
					count = Integer.parseInt(countFromService);
					isUpdated = true;
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
