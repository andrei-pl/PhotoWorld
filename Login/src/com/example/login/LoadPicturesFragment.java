package com.example.login;

import com.example.tasks.GetAsyncResult;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LoadPicturesFragment extends Fragment {
	
	private ListView photoList;
	private FragmentActivity fragment;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_images, container, false);
		this.fragment = (FragmentActivity) getActivity();
		
		photoList = (ListView)rootView.findViewById(R.id.listPhotos);
		LoadPhotos();
		
		return rootView;
	}
	
	private void LoadPhotos() {
		(new GetAsyncResult()).execute(fragment, photoList);
	}
}
