package com.example.login;

import com.example.models.HolderCount;
import com.example.service.PhotoCountService;
import com.example.tasks.GetAsyncResult;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class LoadPicturesFragment extends Fragment {
	
	private ListView photoList;
	private FragmentActivity fragment;
	private HolderCount holderCount = new HolderCount();
	private Intent intent;
	private int countPhotosOnServer = 0;
	private Handler updateListHandler;
	private TaskUpdateList taskUpdateList;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_images, container, false);
		this.fragment = (FragmentActivity) getActivity();
		
		photoList = (ListView)rootView.findViewById(R.id.listPhotos);
		
		LoadPhotos();
			
		taskUpdateList = new TaskUpdateList();
		updateListHandler = new Handler();
		updateListHandler.postDelayed(taskUpdateList, 30000);
		
		resultReceiver = new MyResultReceiver(null);
		this.intent= new Intent(getActivity(), PhotoCountService.class);
		intent.putExtra("receiver", resultReceiver);
		getActivity().startService(intent);
		
		return rootView;
	}
	
	class TaskUpdateList implements Runnable{

		@Override
		public void run() {
			Log.d("checkServerCount", countPhotosOnServer + "");
			Log.d("holderCount.count", holderCount.count + "");
			if(holderCount.count != countPhotosOnServer){
				photoList.setAdapter(null);
				LoadPhotos();
				Toast.makeText(getActivity(), "The album was updated", Toast.LENGTH_LONG).show();
			}
			updateListHandler.postDelayed(taskUpdateList, 5000);
		}
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
		getActivity().stopService(intent);
	}
	
	private void LoadPhotos() {
		(new GetAsyncResult()).execute(fragment, photoList, holderCount);
	}
	
	MyResultReceiver resultReceiver;
	private PhotoCountService photoService;
	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, 
				IBinder binder) {
			PhotoCountService.MyBinder photoBinder = (PhotoCountService.MyBinder) binder;
			photoService = photoBinder.getService();
			Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT)
			.show();
		}

		public void onServiceDisconnected(ComponentName className) {
			photoService = null;
		}
	};

	class UpdateUI implements Runnable {
		public UpdateUI(int count) {
			countPhotosOnServer = count;
		}
		public void run() {
			Log.d("activityCount", countPhotosOnServer + "");
		}
	}

	class MyResultReceiver extends ResultReceiver {
		public MyResultReceiver(Handler handler) {
			super(handler);
		}

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			if(resultCode == 100){
				try{
					getActivity().runOnUiThread(new UpdateUI(resultData.getInt("count")));
				}
				catch(Exception e){
					
				}
			}
		}
	}

}
