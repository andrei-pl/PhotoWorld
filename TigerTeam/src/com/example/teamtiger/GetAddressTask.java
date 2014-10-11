package com.example.teamtiger;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GetAddressTask extends AsyncTask<Location, Void, String> {

    Context mContext;
    public GetAddressTask(Context context) {
        super();
        mContext = context;
    }
    
	@Override
	protected String doInBackground(Location... params) {
		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		String addressText = "";
        
        // Get the current location from the input parameter list
        Location loc = params[0];
        // Create a list to contain the result address
        List<Address> addresses = null;
        try {
            /*
             * Return 1 address.
             */
        	List<Address> address = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 100);
        	addresses = address;
        } catch (IOException e1) {
	        Log.e("LocationSampleActivity", "IO Exception in getFromLocation()");
	        e1.printStackTrace();
	        return ("IO Exception trying to get address");
        } catch (IllegalArgumentException e2) {
	        // Error message to post in the log
	        String errorString = "Illegal arguments " + Double.toString(loc.getLatitude()) + " , " +
	                			  Double.toString(loc.getLongitude()) + " passed to address service";
	        Log.e("LocationSampleActivity", errorString);
	        e2.printStackTrace();
	        return errorString;
        }
        // If the reverse geocode returned an address
        if (addresses != null && addresses.size() > 0) {
            // Get the first address
            Address address = addresses.get(0);
            /*
             * Format the first line of address (if available),
             * city, and country name.
             */

            if(address.getMaxAddressLineIndex() > 0){
            	addressText = address.getAddressLine(0);
            	Log.e("Success", "Success");
            } else {
            	addressText = String.format("%s, %s, %s", "", address.getLocality(), address.getCountryName());
            }
            
            // Return the text
            return addressText;
        }
        Log.e("Success", "No Success");
        return "No address found";
	}
}
