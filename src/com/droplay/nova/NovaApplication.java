package com.droplay.nova;

import android.app.Application;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.droplay.nova.objects.HiddenStar;
import com.droplay.nova.objects.Meteor;
import com.droplay.nova.objects.NovaMovingObject;
import com.droplay.nova.objects.NovaObject;

// The class contains all the stars, since they're in every single screen
// Also extra stuff like music and sound
public class NovaApplication extends Application implements NovaConstants {
	private NovaResources resources;
	
	private boolean isCurrentShowAds;
	private int adSize;
	
	// Static screen
	private HiddenStar[] hiddenStars;
	private NovaObject[] shinyStars;
	private NovaObject[][] farGroupStars;
	
	// Dynamic screen
	private NovaMovingObject[] farStars;
	private NovaMovingObject[] closerStars;
	private NovaMovingObject[] nearStars;
	private Meteor meteor;
	
	private MediaPlayer musicPlayer, soundPlayer;
	private boolean isMusicOn, isSoundOn;
	
	
	// Used to convert the x and y to relative values for each screen size
	private static final int RATIO_1 = 1080;
	private static final int RATIO_2 = 1920;
	
	public boolean isMusicOn() {
		return isMusicOn;
	}

	public void setMusicOn(boolean isMusicOn) {
		this.isMusicOn = isMusicOn;
		
		if (musicPlayer != null) {
			if (isMusicOn)
				musicPlayer.start();
			else
				musicPlayer.pause();
		}
	}

	public boolean isSoundOn() {
		return isSoundOn;
	}

	public void setSoundOn(boolean isSoundOn) {
		this.isSoundOn = isSoundOn;
	}

	public HiddenStar[] getHiddenStars() {
		return hiddenStars;
	}

	public NovaObject[] getShinyStars() {
		return shinyStars;
	}

	public NovaObject[][] getFarGroupStars() {
		return farGroupStars;
	}
	
	public Meteor getMeteor() {
		return meteor;
	}
	
	public NovaMovingObject[] getFarStars() {
		return farStars;
	}
	
	public NovaMovingObject[] getCloserStars() {
		return closerStars;
	}

	public NovaMovingObject[] getNearStars() {
		return nearStars;
	}
	
	public void setAdSize(int adSize) {
		this.adSize = adSize;
	}
	
	public int getAdSize() {
		return adSize;
	}
	
	public void setCurrentShowAds(boolean isCurrentShowAds) {
		this.isCurrentShowAds = isCurrentShowAds;
	}
	
	public boolean isCurrentShowAds() {
		return isCurrentShowAds;
	}
	
	// Move all of the stars in the game when the ads screen shows,
	// so it would still look clean
	public void moveStars(int dy) {
		// Dynamic
		for (NovaObject o : farStars)
			o.setY(o.getY() + dy);
		
		for (NovaObject o : closerStars)
			o.setY(o.getY() + dy);
		
		for (NovaObject o : nearStars)
			o.setY(o.getY() + dy);
		
		meteor.setY(meteor.getY() + dy);
		
		// Static
		for (NovaObject o : shinyStars)
			o.setY(o.getY() + dy);
		
		for (HiddenStar h : hiddenStars)
			h.setY(h.getY() + dy);
		
		for (NovaObject[] arr : farGroupStars)
			for (NovaObject o : arr)
				o.setY(o.getY() + dy);
	}
	
	public void randomizeDynamicScreen(final int SCREEN_WIDTH, final int SCREEN_HEIGHT) {
		// MAGIC. DO NOT TOUCH
		farStars = new NovaMovingObject[SCREEN_WIDTH * SCREEN_HEIGHT * NUMBER_FAR_STARS / RATIO_1 / RATIO_2];
		closerStars = new NovaMovingObject[SCREEN_WIDTH * SCREEN_HEIGHT * NUMBER_CLOSER_STARS / RATIO_1 / RATIO_2];
		nearStars = new NovaMovingObject[SCREEN_WIDTH * SCREEN_HEIGHT * NUMBER_NEAR_STARS / RATIO_1 / RATIO_2];
		int currentSpace = 0;
		
		if (farStars.length > 0)
			currentSpace = SCREEN_WIDTH / farStars.length;
		
		// Generate far stars locations
		for (int i = 0; i < farStars.length; i++)
			// Stars in a certain frame, a random x and random y, and left movement speed
			farStars[i] = new NovaMovingObject(new BitmapSprite(resources.FAR_STAR[(int)(Math.random() * resources.FAR_STAR.length)]),
					i * currentSpace + (int)(Math.random() * currentSpace),
					(int)(Math.random() * SCREEN_HEIGHT), -FAR_STARS_SPEED);
	
		if (closerStars.length > 0)
			currentSpace = SCREEN_WIDTH / closerStars.length;
		
		for (int i = 0; i < closerStars.length; i++)
			closerStars[i] = new NovaMovingObject(new BitmapSprite(resources.CLOSER_STAR[(int)(Math.random() * resources.CLOSER_STAR.length)]),
					i * currentSpace + (int)(Math.random() * currentSpace),
					(int)(Math.random() * SCREEN_HEIGHT), -CLOSER_STARS_SPEED);
		
		if (nearStars.length > 0)
			currentSpace = SCREEN_WIDTH / nearStars.length;
		
		for (int i = 0; i < nearStars.length; i++)
			nearStars[i] = new NovaMovingObject(new BitmapSprite(resources.NEAR_STAR[(int)(Math.random() * resources.NEAR_STAR.length)]),
					i * currentSpace + (int)(Math.random() * currentSpace),
					(int)(Math.random() * SCREEN_HEIGHT), -NEAR_STARS_SPEED);
		
		meteor = new Meteor(resources, SCREEN_WIDTH, (float)(Math.random() * SCREEN_WIDTH), (float)(Math.random() * SCREEN_HEIGHT), METEOR_SPEED);
	}
	
	public void randomizeStaticScreen(final int SCREEN_WIDTH, final int SCREEN_HEIGHT) {
		shinyStars = new NovaObject[SCREEN_WIDTH * SCREEN_HEIGHT * NUMBER_SHINY_STARS / RATIO_1 / RATIO_2];
		hiddenStars = new HiddenStar[SCREEN_WIDTH * SCREEN_HEIGHT * NUMBER_HIDDEN_STARS / RATIO_1 / RATIO_2];
		farGroupStars = new NovaObject[NUMBER_FAR_STARS_GROUP][];
		int currentSpace = 0;
		
		if (shinyStars.length > 0)
			currentSpace = SCREEN_WIDTH / shinyStars.length;
		
		for (int i = 0; i < shinyStars.length; i++)
			shinyStars[i] = new NovaObject(new AnimationSprite(resources.SHINY_STAR, (int)(Math.random() * resources.SHINY_STAR.getNumberOfFrames())), i * currentSpace + (int)(Math.random() * currentSpace),
					(int)(Math.random() * SCREEN_HEIGHT));
	
		if (hiddenStars.length > 0)
			currentSpace = SCREEN_WIDTH / hiddenStars.length;
		
		for (int i = 0; i < hiddenStars.length; i++)
			hiddenStars[i] = new HiddenStar(resources, i * currentSpace + (int)(Math.random() * currentSpace),
					(int)(Math.random() * SCREEN_HEIGHT), (int)(Math.random() * HIDDEN_STAR_WAIT));
		
		for (int i = 0; i < farGroupStars.length; i++)
			generateStarsGroup(i, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	
	public void generateStarsGroup(int index, final int SCREEN_WIDTH, final int SCREEN_HEIGHT) {
		int xSpace = SCREEN_WIDTH / NUMBER_FAR_STARS_GROUP;
		int ySpace = SCREEN_HEIGHT / NUMBER_FAR_STARS_GROUP;
		farGroupStars[index] = new NovaObject[SCREEN_WIDTH * SCREEN_HEIGHT * (MIN_STARS_PER_GROUP + (int)(Math.random() * (MAX_STARS_PER_GROUP - MIN_STARS_PER_GROUP + 1))) / RATIO_1 / RATIO_2];
		
		// Generate the starting location
		int startX = xSpace * index + (int)(Math.random() * xSpace / STARS_GROUP_DENSITY);
		int startY = ySpace * index + (int)(Math.random() * xSpace / STARS_GROUP_DENSITY);
		
		// Move through the group and generate stars somewhere in the group space
		for (int i = 0; i < farGroupStars[index].length; i++) {
			farGroupStars[index][i] = new NovaObject(
					new BitmapSprite(resources.FAR_STARS_GROUP[(int)(Math.random() * resources.FAR_STARS_GROUP.length)]),
					startX + (int)(Math.random() * xSpace / STARS_GROUP_DENSITY),
					startY + (int)(Math.random() * ySpace / STARS_GROUP_DENSITY));
		}
	}
	
	public void playMusic(final int resid) {
		if (musicPlayer != null) {
			musicPlayer.release();
			musicPlayer = null;
		}
		
		musicPlayer = MediaPlayer.create(NovaApplication.this, resid);
		
		if (musicPlayer != null) {
			musicPlayer.setLooping(true);
			
			if (isMusicOn)
				musicPlayer.start();
		}
	}
	
	public void playSound(final int resid) {
		if (soundPlayer != null) {
			soundPlayer.release();
			soundPlayer = null;
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				soundPlayer = MediaPlayer.create(NovaApplication.this, resid);
				
				if (soundPlayer != null)
					soundPlayer.start();
				
				if (soundPlayer != null)
					soundPlayer.setOnCompletionListener(new OnCompletionListener() {
					    public void onCompletion(MediaPlayer mp) {
					    	if (mp != null)
					    		// Efficiency
					    		mp.release();
					    };
					});
			}
		}).start();
	}
	
	public void releaseMediaPlayers() {
		if (musicPlayer != null) {
			musicPlayer.release();
			musicPlayer = null;
		}
		
		if (soundPlayer != null) {
			soundPlayer.release();
			soundPlayer = null;
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		resources = new NovaResources(getResources());
		
	}
	
	public NovaResources getNovaResources() {
		return resources;
	}
}
