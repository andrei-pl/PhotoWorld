package com.example.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.images.Image;
import com.example.images.ImageAdapter;
import com.example.images.ImageInfo;
import com.example.location.MyLocation;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
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
