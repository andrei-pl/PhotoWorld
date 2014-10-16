package com.example.images;

import java.util.Date;
import java.util.UUID;

import org.joda.time.DateTime;

import android.location.Location;

import com.example.location.MyLocation;
import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.serialization.ServerProperty;
import com.telerik.everlive.sdk.core.serialization.ServerType;

@ServerType("ImagesInfo")
public class ImageInfo extends DataItem{
//	
//	@ServerProperty("Owner")
//	private UUID Owner;

	@ServerProperty("Address")
	public String Address;
	
	@ServerProperty("Picture")
	public Image Picture;

	@ServerProperty("Location")
	public MyLocation location;

	@ServerProperty("Public")
	public boolean Public;

	@ServerProperty("PublicationDate")
	public Date publicationDate;
	
	public String Filename;

	public String base64;
	
	public UUID pictureId;
    
//	public UUID getOwner() {
//		return Owner;
//	}
//
//	public void setOwner(UUID owner) {
//		Owner = owner;
//	}

//	public String getAddress() {
//		return Address;
//	}
//
//	public void setAddress(String address) {
//		Address = address;
//	}

//	public Image getPicture() {
//		return Picture;
//	}
//
//	public void setPicture(Image picture) {
//		Picture = picture;
//	}

	public MyLocation getLocation() {
		return location;
	}

	public void setLocation(MyLocation location) {
		this.location = location;
	}

	public boolean isPublic() {
		return Public;
	}

	public void setPublic(boolean public1) {
		Public = public1;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public UUID getPictureId() {
		return pictureId;
	}

	public void setPictureId(UUID pictureId) {
		this.pictureId = pictureId;
	}
}
