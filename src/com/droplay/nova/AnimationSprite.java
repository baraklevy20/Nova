package com.droplay.nova;

import android.graphics.Bitmap;

// The actual animation class
public class AnimationSprite extends NovaSprite {
	private AnimationFrames frames;
	private int currentFrame;
	private long currentInterval;
	
	private long newTime;
	private boolean isLoop;
	
	private int interval;
	
	// Set to true if it should play from the last frame to the first one
	private boolean isReverse;
	
	public AnimationSprite() {
		isLoop = false;
		interval = 0;
	}
	
	public AnimationSprite(AnimationFrames frames, int startingFrame) {
		this.frames = frames;
		
		// Set the isLoop and interval according to the frames
		this.isLoop = frames.IS_LOOP;
		this.interval = frames.INTERVAL;
		currentFrame = startingFrame;
	}
	
	public void setReverse(boolean isReverse) {
		this.isReverse = isReverse;
	}
	
	public AnimationSprite(AnimationFrames frames) {
		this.frames = frames;
		this.isLoop = frames.IS_LOOP;
		this.interval = frames.INTERVAL;
	}
	
	public void setFrames(AnimationFrames frames) {
		this.frames = frames;
		currentFrame = 0;
		currentInterval = 0;
		
		if (frames != null) {
			this.isLoop = frames.IS_LOOP;
			this.interval = frames.INTERVAL;
		}
	}
	
	public void setFrameIndex(int currentFrame) {
		this.currentFrame = currentFrame;
		this.currentInterval = System.currentTimeMillis();
	}
	
	public int getNumberOfFrames() {
		return frames.getNumberOfFrames();
	}
	
	public int getFrameIndex() {
		return currentFrame;
	}
	
	public long getCurrentInterval() {
		return currentInterval;
	}
	
	public void resetAnimation() {
		currentFrame = 0;
		currentInterval = 0;
	}
	public void normalUpdate() {
		newTime = System.currentTimeMillis();
		
		if (currentInterval == 0)
			currentInterval = newTime;
		
		// If enough time has passed, move to the next frame
		if (newTime - currentInterval >= interval) {
			currentInterval = newTime;
			
			// If it's a loop, repeat the frames
			if (isLoop)
				currentFrame = (currentFrame + (isReverse ? -1 : 1)) % frames.getNumberOfFrames();
			// Otherwise, check if we're not in the first/last frame (according to the reverse value)
			else if (isReverse && currentFrame > 0 || !isReverse && currentFrame < frames.getNumberOfFrames() - 1)
				currentFrame += isReverse ? -1 : 1;
		}
	}
	
	public boolean isLoop() {
		return isLoop;
	}
	
	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	
	public int getInterval() {
		return interval;
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	@Override
	public Bitmap getFrame() {
		return frames.getFrame(currentFrame);
	}

	// Returns true if the animation has done running
	public boolean isDone() {
		return isReverse && currentFrame == 0 || !isReverse && currentFrame == frames.getNumberOfFrames() - 1;
	}

	public AnimationFrames getFrames() {
		return frames;
	}

	public void setCurrentInterval(long currentInterval) {
		this.currentInterval = currentInterval;
	}

	public boolean isReverse() {
		return isReverse;
	}
}
