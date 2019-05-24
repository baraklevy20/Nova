package com.droplay.nova;

import android.graphics.Bitmap;

// Take care of animation frames
public class AnimationFrames {
	private Bitmap[] frames;
	
	// The interval between each frame
	public final int INTERVAL;
	
	// If the animation repeats itself, this is set to true
	public final boolean IS_LOOP;
	
	public AnimationFrames(final int INTERVAL, boolean isLoop, Bitmap... frames) {
		this.frames = frames;
		this.INTERVAL = INTERVAL;
		this.IS_LOOP = isLoop;
	}
	
	public int getWidth() {
		return frames[0].getWidth();
	}
	
	public int getHeight() {
		return frames[0].getHeight();
	}
	
	public int getNumberOfFrames() {
		return frames.length;
	}
	
	public Bitmap getFrame(int index) {
		return frames[index];
	}
}