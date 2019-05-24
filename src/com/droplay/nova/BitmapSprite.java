package com.droplay.nova;

import android.graphics.Bitmap;

public class BitmapSprite extends NovaSprite {
	private Bitmap bitmap;
	
	public BitmapSprite(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	@Override
	public Bitmap getFrame() {
		return bitmap;
	}
}
