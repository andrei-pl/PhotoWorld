package com.example.login;

import android.app.Activity;
import android.os.Bundle;

public class ImagesPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.images_layout);
	}

	// TODO: Load and save images by username in everlive
	// Save by UserName: Picture (File), Location (Geopoint), Address (string), Public (Yes/No)
}
