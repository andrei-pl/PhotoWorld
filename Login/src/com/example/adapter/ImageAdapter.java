package com.example.adapter;

import com.example.login.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ImageAdapter extends ArrayAdapter<Bitmap> {

	Context context;
	private int resource;

	public ImageAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
		this.resource = resource;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ImageHolderItem holder;

		if (view == null) {
			holder = new ImageHolderItem();

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(this.resource, parent, false);
			holder.image = (ImageView) view.findViewById(R.id.photo);

			view.setTag(holder);
		} else {
			holder = (ImageHolderItem) view.getTag();
		}

		holder.image.setImageBitmap(getItem(position));

		return view;
	}
}
