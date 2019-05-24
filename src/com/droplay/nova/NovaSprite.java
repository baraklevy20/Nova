package com.droplay.nova;

import android.graphics.Bitmap;

// Each sprite is either a bitmap or an animation.
public abstract class NovaSprite {
	// Each sprite has a frame. get it
	public abstract Bitmap getFrame();
}
