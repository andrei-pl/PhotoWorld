package com.example.login;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import com.example.images.Image;
import com.example.images.ImageInfo;
import com.example.location.MyLocation;
import com.telerik.everlive.sdk.core.EverliveApp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TakePictureFragment extends Fragment{
	
	private static final int CAMERA_REQUEST = 0; 
	private ImageView currentImage;
	private Button cancelBtn;
	private Button confirmBtn;
	private Uri fileUri;
	private Intent data;

    private EverliveApp everlive;
	private String currentFileName;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.everlive = new EverliveApp("gIMQgGG9sI53ZQjD");
        //this.everlive.workWith().authentication().login("asd", "asd").executeSync();
		
		View view = inflater.inflate(R.layout.fragment_take_picture,  container, false);
		
		currentImage = (ImageView) view.findViewById(R.id.currentImage);
		
		cancelBtn = (Button)view.findViewById(R.id.pictureCancel);
		confirmBtn = (Button)view.findViewById(R.id.pictureOk);
		
		cancelBtn.setOnClickListener(new CancelButton());
		confirmBtn.setOnClickListener(new ConfirmButton());
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = getOutputPhotoFile();
        fileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	    startActivityForResult(intent, CAMERA_REQUEST);
		return view;
	}
	
	private File getOutputPhotoFile() {
		File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getActivity().getPackageName());
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				return null;
			}
		}
		
		String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());
		currentFileName = "IMG_" + timeStamp + ".jpg";
		
		return new File(directory.getPath() + File.separator + currentFileName);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
		    if (resultCode == Activity.RESULT_OK) {
		        Uri photoUri = null;
		        if (data == null) {
		        	photoUri = fileUri;
		        } else {
		        	photoUri = data.getData();
		        	fileUri = photoUri;
		        	this.data = data;
		        }
		        
		        showPhoto(photoUri);
		    } 
	    }
	}
	
	private void showPhoto(Uri photoUri) {
	  File imageFile = new File(photoUri.getPath());
	  if (imageFile.exists()){
	     Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
	     BitmapDrawable drawable = new BitmapDrawable(this.getResources(), bitmap);
	     currentImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
	     currentImage.setImageDrawable(drawable);
	  }       
	}
	
	private class CancelButton implements OnClickListener{

		@Override
		public void onClick(View v) {
			File file = new File(fileUri.getPath());
			boolean deleted = file.delete();
			if(!deleted){
				Toast.makeText(getActivity(), "FAILL", Toast.LENGTH_LONG).show();
			}
			
			FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.replace(R.id.frame_container, new HomeFragment() );
            fragTransaction.addToBackStack(null);
            fragTransaction.commit();
		}
	}
	
	private class ConfirmButton implements OnClickListener{

		@Override
		public void onClick(View v) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");   

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();  		

            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Image image = new Image();
			image.Filename = currentFileName;
			image.ContentType = "image/jpeg";
			image.base64 = encodedImage;
            
            MyLocation location = new MyLocation(43, 23);
            
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.Picture  = image;
            imageInfo.setLocation(location);
            imageInfo.Address = "Aleksandar Malinov 30";
            imageInfo.setPublic(true);
            imageInfo.setOwner(new UUID(1, 1));
            imageInfo.setPublicationDate(new Date());
            
//            Gson gson = new Gson();
//            String json = gson.toJson(imageInfo);
            everlive.workWith().data(ImageInfo.class).create(imageInfo).executeAsync();
            
            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.replace(R.id.frame_container, new HomeFragment() );
            fragTransaction.addToBackStack(null);
            fragTransaction.commit();
		}
	}
}
