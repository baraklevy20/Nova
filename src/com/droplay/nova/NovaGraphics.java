package com.droplay.nova;

import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.droplay.nova.objects.HiddenStar;
import com.droplay.nova.objects.King;
import com.droplay.nova.objects.Meteor;
import com.droplay.nova.objects.NovaMovingObject;
import com.droplay.nova.objects.NovaObject;
import com.droplay.nova.opening.NovaOpeningGraphics;

// A general graphics class. Since all screens have pretty much the same screen,
// the doDraw method is super important here
public abstract class NovaGraphics extends SurfaceView implements SurfaceHolder.Callback {
	protected NovaContext context;
	protected NovaResources resources;
	protected int width, height;
	private boolean isInitiated;
	protected DynamicScreen dynamicScreen;
	protected StaticScreen staticScreen;
	private NovaObject[] shinyStars;
	private HiddenStar[] hiddenStars;
	private NovaObject[][] farGroupStars;
	private NovaMovingObject[] farStars;
	private NovaMovingObject[] closerStars;
	private NovaMovingObject[] nearStars;
	private Meteor meteor;
	
	public NovaGraphics(NovaContext context) {
		super(context.getActivity());
		this.context = context;
		
		getHolder().addCallback(this);
		// Must have so you can click on the screen
		setFocusable(true);
	}
	
	public void doDraw(Canvas canvas) {
		// Draw background
		canvas.drawColor(0xFF0D081B);
		
		// Draw screens
		for (NovaObject[] arr : farGroupStars)
			for (NovaObject o : arr)
				canvas.drawBitmap(o.getSprite().getFrame(), o.getX(), o.getY() , null);
		
		for (NovaMovingObject o : farStars)
			canvas.drawBitmap(o.getSprite().getFrame(), o.getX(), o.getY(), null);
		
		for (NovaMovingObject o : closerStars)
			canvas.drawBitmap(o.getSprite().getFrame(), o.getX(), o.getY(), null);
		
		for (HiddenStar o : hiddenStars)
			canvas.drawBitmap(o.getSprite().getFrame(), o.getX(), o.getY(), null);
		
		for (NovaMovingObject o : nearStars)
			canvas.drawBitmap(o.getSprite().getFrame(), o.getX(), o.getY() , null);
		
		for (NovaObject o : shinyStars)
			canvas.drawBitmap(o.getSprite().getFrame(), o.getX(), o.getY(), null);
		
		if (meteor.getState() == Meteor.State.STARTING || meteor.getState() == Meteor.State.STARTED)
			canvas.drawBitmap(meteor.getSprite().getFrame(), meteor.getX(), meteor.getY(), null);
		
		// Draw mute buttons
		if (!(this instanceof NovaOpeningGraphics)) {
			if (((NovaApplication)context.getActivity().getApplication()).isSoundOn())
				canvas.drawBitmap(resources.SOUND_ON, 20 * width / 480, 21 * height / 800, null);
			else
				canvas.drawBitmap(resources.SOUND_OFF, 20 * width / 480, 21 * height / 800, null);
			
			if (((NovaApplication)context.getActivity().getApplication()).isMusicOn())
				canvas.drawBitmap(resources.MUSIC_ON, 59 * width / 480, 21 * height / 800, null);
			else
				canvas.drawBitmap(resources.MUSIC_OFF, 59 * width / 480, 21 * height / 800, null);
		}
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.width = this.getWidth();
		this.height = this.getHeight();
		
		// Initiate only once!
		if (!isInitiated) {
			if (NovaGraphics.this instanceof NovaOpeningGraphics) {
				NovaMovingObject.SCREEN_WIDTH = width;
				NovaMovingObject.KING_WIDTH = context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth();
				NovaMovingObject.VELOCITY_FACTOR = (NovaMovingObject.SCREEN_WIDTH - NovaMovingObject.KING_WIDTH) / 888f;
			    ((NovaApplication)context.getActivity().getApplication()).randomizeDynamicScreen(width, height);
				((NovaApplication)context.getActivity().getApplication()).randomizeStaticScreen(width, height);
		    }
			context.getManager().initialize();
			initialize();
			isInitiated = true;
		}
		
		// Move stars if ads are added/removed
		if (context.getActivity().isShowAds() && !((NovaApplication)context.getActivity().getApplication()).isCurrentShowAds()) {
			((NovaApplication)context.getActivity().getApplication()).moveStars(-context.getActivity().getAdView().getHeight());
			((NovaApplication)context.getActivity().getApplication()).setAdSize(context.getActivity().getAdView().getHeight());
		}
		else if (!context.getActivity().isShowAds() && ((NovaApplication)context.getActivity().getApplication()).isCurrentShowAds())
			((NovaApplication)context.getActivity().getApplication()).moveStars(((NovaApplication)context.getActivity().getApplication()).getAdSize());
		
		((NovaApplication)context.getActivity().getApplication()).setCurrentShowAds(context.getActivity().isShowAds());
		
		// One less byte => FASTERRRR
		holder.setFormat(PixelFormat.RGB_565);
		context.getManager().getGameLoop().startGameLoop(holder);
//		extraMergin = context.getActivity().isShowAds() ? context.getActivity().getAdView().getHeight() : 0;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		context.getManager().getGameLoop().stopGameLoop();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		context.getManager().handleTouch(event);
		return true;
	}
	
	public void initialize() {
		this.resources = context.getResources();
		staticScreen = context.getManager().getStaticScreen();
		dynamicScreen = context.getManager().getDynamicScreen();
		
		shinyStars = ((NovaApplication)context.getActivity().getApplication()).getShinyStars();
		hiddenStars = ((NovaApplication)context.getActivity().getApplication()).getHiddenStars();
		farGroupStars = ((NovaApplication)context.getActivity().getApplication()).getFarGroupStars();
		farStars = ((NovaApplication)context.getActivity().getApplication()).getFarStars();
		closerStars = ((NovaApplication)context.getActivity().getApplication()).getCloserStars();
		nearStars = ((NovaApplication)context.getActivity().getApplication()).getNearStars();
		meteor = ((NovaApplication)context.getActivity().getApplication()).getMeteor();
	}
}
