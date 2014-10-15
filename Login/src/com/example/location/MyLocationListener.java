package com.example.location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.login.ImagesPageActivity;
import com.example.login.MapFragment;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public final class MyLocationListener extends LocationClient implements
		LocationListener {

	private Context context;
	private ConnectionCallbacks connectionCallbacks;
	private OnConnectionFailedListener connectionFailedListener;
	private static String currentAddress;
	public static MyLocationListener myLocationListener;
	private static Location myLocation;

	private MyLocationListener(Context context,
			ConnectionCallbacks connectionCallbacks,
			OnConnectionFailedListener connectionFailedListener) {
		super(context, connectionCallbacks, connectionFailedListener);

		this.context = context;
		this.connectionCallbacks = connectionCallbacks;
		this.connectionFailedListener = connectionFailedListener;
		this.setCurrentAddress("");
	}

	public static MyLocationListener Instance (Context context,
			ConnectionCallbacks connectionCallbacks,
			OnConnectionFailedListener connectionFailedListener){
		
		if(myLocationListener == null){
			myLocationListener = new MyLocationListener(context, connectionCallbacks, connectionFailedListener);
		}
		
		return myLocationListener;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		setMyLocation(location);
		(new GetAddressTask()).execute(location);
		
		MapFragment.changeCurrentLocation(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		this.connect();
	}

	@Override
	public void onProviderDisabled(String provider) {
		this.disconnect();

	}

	public static String getCurrentAddress() {
		return currentAddress;
	}

	private void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public static Location getMyLocation() {
		return myLocation;
	}

	private void setMyLocation(Location myLocation) {
		this.myLocation = myLocation;
	}

	private class GetAddressTask extends AsyncTask<Location, Void, String> {
		Location loc;

		@Override
		protected String doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(context, Locale.getDefault());
			String addressText = "";

			// Get the current location from the input parameter list
			loc = params[0];
			// Create a list to contain the result address
			List<Address> addresses = null;
			try {
				/*
				 * Return 1 address.
				 */
				List<Address> address = geocoder.getFromLocation(
						loc.getLatitude(), loc.getLongitude(), 1);
				addresses = address;
			} catch (IOException e1) {
				Log.e("LocationSampleActivity",
						"IO Exception in getFromLocation()");
				e1.printStackTrace();
				return ("IO Exception trying to get address");
			} catch (IllegalArgumentException e2) {
				// Error message to post in the log
				String errorString = "Illegal arguments "
						+ Double.toString(loc.getLatitude()) + " , "
						+ Double.toString(loc.getLongitude())
						+ " passed to address service";
				Log.e("LocationSampleActivity", errorString);
				e2.printStackTrace();
				return errorString;
			}
			// If the reverse geocode returned an address
			if (addresses != null && addresses.size() > 0) {
				// Get the first address
				Address address = addresses.get(0);
				/*
				 * Format the first line of address (if available), city, and
				 * country name.
				 */

				if (address.getMaxAddressLineIndex() > 0) {
					addressText = address.getAddressLine(0);
					Log.e("Success", "Success");
				} else {
					addressText = String.format("%s, %s, %s", "",
							address.getLocality(), address.getCountryName());
				}

				// Return the text
				return addressText;
			}
			Log.e("Success", "No Success");
			return "No address found";
		}

		@Override
		protected void onPostExecute(String result) {
			setCurrentAddress(result);

			super.onPostExecute(result);
		}
	}
}